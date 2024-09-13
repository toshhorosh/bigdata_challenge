package org.toshhorosh.bigdata.challenge.consumer

import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.functions._
import org.toshhorosh.bigdata.challenge.AppInstance
import org.toshhorosh.bigdata.challenge.data.CampaignDict

object ConsumerAppInstance extends AppInstance with LazyLogging {

  override def run(config: Config): Unit = {

    val consumerAppConfig = new ConsumerAppConfig(config)

    logger.info(s"Loading the static dictionary of campaigns from: " +
      s"${consumerAppConfig.campaignsDictPath}")
    val dictDf = BatchDataLoader.loadFromCsvDataFrame(consumerAppConfig.campaignsDictPath,
      extractSparkSchema[CampaignDict])

    val eventsDf = getSpark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", consumerAppConfig.bootstrapServers)
      .option("subscribe", consumerAppConfig.topic)
      .load()
      .map {
        r => viewLogDeserializer.deserialize("hhh", r.getAs[Array[Byte]]("value"))
      }(viewLogToSparkDsEncoder)


    val joined = eventsDf
      .join(dictDf, col("campaignId") === col("campaign_id"), "left")
      .withColumn("view_duration_sec", unix_timestamp(col("endTimestamp")) - unix_timestamp(col("startTimestamp")))
      .withColumn("minute_timestamp", date_trunc("minute", col("endTimestamp")))
      .select(
        col("campaignId").as("campaign_id"),
        col("network_id"),
        col("minute_timestamp"),
        col("view_duration_sec")
      )
      .withWatermark("minute_timestamp", "1 minutes")
      .groupBy(
        window(col("minute_timestamp"), "1 minutes").as("minute_timestamp"),
        col("campaign_id"),
        col("network_id")
      ).agg(avg("view_duration_sec").as("avg_duration"),
        count("campaign_id").as("total_count"))

    val writeParquetQuery = joined
      .writeStream
      .format("parquet")
      .option("path", consumerAppConfig.viewReportPath)
      .outputMode("append")
      .partitionBy("network_id", "minute_timestamp")
      .option("checkpointLocation", consumerAppConfig.checkpointLocation)
      .start()

    writeParquetQuery.awaitTermination()

  }
}
