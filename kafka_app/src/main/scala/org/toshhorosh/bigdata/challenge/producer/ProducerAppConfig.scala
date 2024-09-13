package org.toshhorosh.bigdata.challenge.producer

import com.typesafe.config.Config

import scala.jdk.CollectionConverters.{CollectionHasAsScala, MapHasAsJava}

class ProducerAppConfig(config: Config) {

  private[producer] val KAFKA_CONF_KEY = "kafka"
  private[producer] val RECORD_LIMIT_CONF_KEY = "records-limit"
  private[producer] val TOPIC_NAME_CONF_KEY = "topic.name"

  val limit_records: Int = config.getInt(RECORD_LIMIT_CONF_KEY)

  val topic_name: String = config.getString(TOPIC_NAME_CONF_KEY)

  def getKafkaSettings: java.util.Map[String, AnyRef] = {
    val filteredConfig = config.getConfig(KAFKA_CONF_KEY)
    filteredConfig
      .entrySet()
      .asScala
      .map(pair => (pair.getKey, filteredConfig.getAnyRef(pair.getKey)))
      .toMap
      .asJava
  }

}
