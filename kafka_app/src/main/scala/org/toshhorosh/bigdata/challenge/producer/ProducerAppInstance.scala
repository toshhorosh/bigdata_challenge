package org.toshhorosh.bigdata.challenge.producer

import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer
import org.toshhorosh.bigdata.challenge.AppInstance
import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

import java.util.concurrent.Future
import scala.util.Try


object ProducerAppInstance extends AppInstance with LazyLogging {

  override def run(config: Config): Unit = {

    val producerAppConfig = new ProducerAppConfig(config)

    logger.debug("Creating the kafka producer")
    val producer = new KafkaProducer[String, ViewLogRecord](producerAppConfig.getKafkaSettings,
      new StringSerializer,
      viewLogToJsonSerializer)

    logger.debug(s"Starting data load with limit of records: ${producerAppConfig.limit_records}")
    DataGenerator(producerAppConfig.limit_records).foreach {
      record =>
        Thread.sleep(getRandomDurationMillis)
        produce(producer, producerAppConfig.topic_name, record.viewId, record)
    }

    Try {
      producer.flush()
      producer.close()
      logger.info("Successfully close the producer application.")
    }.recover {
      case e: Exception => logger.error("Closing the producer was failed with: ", e)
    }

  }

  private[producer] def produce[K, V](producer: KafkaProducer[K, V],
                                      topic: String,
                                      key: K,
                                      value: V): Future[RecordMetadata] = {
    val kafkaRecord: ProducerRecord[K, V] = new ProducerRecord(topic, key, value)

    producer.send(kafkaRecord, new Callback {
      override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = Option(exception)
        .map(ex => logger.error(s"Fail to produce record due to: ${ex.getMessage}"))
        .getOrElse(logger.info(s"Successfully produced - ${recordMetadataToString(metadata)}"))
    })
  }
}

















