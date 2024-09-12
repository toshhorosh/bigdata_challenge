package org.toshhorosh.bigdata.challenge.producer

import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import io.circe.Encoder
import io.circe.generic.auto._
import nequi.circe.kafka
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.{Serializer, StringSerializer}
import org.toshhorosh.bigdata.challenge.AppInstance
import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

import java.util.concurrent.Future
import scala.jdk.CollectionConverters.{CollectionHasAsScala, MapHasAsJava}
import scala.language.postfixOps
import scala.util.Try


object ProducerAppInstance extends AppInstance with LazyLogging {

  private[producer] val RECORD_LIMIT_CONF_KEY = "records-limit"
  private[producer] val TOPIC_NAME_CONF_KEY = "topic-name"

  override def run(config: Config): Unit = {

    val limit_records = config.getInt(RECORD_LIMIT_CONF_KEY)
    val topic_name = config.getString(TOPIC_NAME_CONF_KEY)

    logger.debug("Creating the serializer and configuration")
    val enc: Encoder[ViewLogRecord] = implicitly[Encoder[ViewLogRecord]]
    val viewLogToJsonSerializer: Serializer[ViewLogRecord] = kafka.encoder2serializer(enc)
    val producerConf = prepareProducerConfig(config)

    logger.debug("Creating the kafka producer")
    val producer = new KafkaProducer[String, ViewLogRecord](producerConf, new StringSerializer, viewLogToJsonSerializer)

    def produce[K, V](producer: KafkaProducer[K, V],
                                        topic: String,
                                        key: K,
                                        value: V): Future[RecordMetadata] = {
      val kafkaRecord: ProducerRecord[K, V] = new ProducerRecord(topic, key, value)

      producer.send(kafkaRecord, new Callback {
        override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = Option(exception)
          .map(ex => logger.error(s"Fail to produce record due to: ${ex.getMessage}"))
          .getOrElse(logger.info(s"Successfully produced - ${printMetaData(metadata)}"))
      })
    }

    logger.debug(s"Starting data load with limit of records: $limit_records")
    DataGenerator(limit_records).foreach{
      record =>
        produce(producer, topic_name, record.viewId, record)
    }

    Try {
      producer.flush()
      producer.close()
      logger.info("Successfully close the producer application.")
    }.recover {
      case e: Exception => logger.error("Closing the producer was failed with: ", e)
    }

  }

  private[producer] def prepareProducerConfig(config: Config): java.util.Map[String, AnyRef] = {
    val filteredConfig = config.getConfig("producer")
    filteredConfig
      .entrySet()
      .asScala
      .map(pair => (pair.getKey, filteredConfig.getAnyRef(pair.getKey)))
      .toMap
      .asJava
  }

  private[producer] def printMetaData(metadata: RecordMetadata): String =
    s"""topic: ${metadata.topic()},
       | partition: ${metadata.partition()},
       | offset: ${metadata.offset()}
       | (ts: ${metadata.timestamp()})""".stripMargin.replace("\n", "")
}

















