package org.toshhorosh.bigdata.challenge.producer

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.InstanceType.Producer
import org.toshhorosh.bigdata.challenge.configResolver

class ProducerAppInstanceSpec extends AnyFlatSpec {

  "ProducerAppInstance" should "prepare config correctly" in {
    val config = configResolver(Producer)
    val producerConf = new ProducerAppConfig(config)
    val kafkaProducerSettings = producerConf.getKafkaSettings

    assert(!kafkaProducerSettings.isEmpty)

    assert(kafkaProducerSettings.containsKey("acks")
      && kafkaProducerSettings.containsKey("client.id")
      && kafkaProducerSettings.containsKey("bootstrap.servers"))

    assert(config.hasPath(producerConf.RECORD_LIMIT_CONF_KEY)
      && config.hasPath(producerConf.TOPIC_NAME_CONF_KEY))
  }
}
