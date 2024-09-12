package org.toshhorosh.bigdata.challenge.producer

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.InstanceType.Producer
import org.toshhorosh.bigdata.challenge.Main.configResolver
import org.toshhorosh.bigdata.challenge.producer.ProducerAppInstance.{RECORD_LIMIT_CONF_KEY, TOPIC_NAME_CONF_KEY}

class ProducerAppInstanceSpec extends AnyFlatSpec {

  "ProducerAppInstance" should "prepare config correctly" in {
    val conf = configResolver(Producer)
    val producerConf = ProducerAppInstance.prepareProducerConfig(conf)

    assert(!producerConf.isEmpty)

    assert(producerConf.containsKey("acks")
      && producerConf.containsKey("client.id")
      && producerConf.containsKey("bootstrap.servers"))

    assert(conf.hasPath(RECORD_LIMIT_CONF_KEY)
      && conf.hasPath(TOPIC_NAME_CONF_KEY))
  }
}
