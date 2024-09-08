package org.toshhorosh.bigdata.challenge

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.InstanceType.{Consumer, Producer, Transformer}
import org.toshhorosh.bigdata.challenge.Main.{appResolver, configResolver}
import org.toshhorosh.bigdata.challenge.consumer.ConsumerAppInstance
import org.toshhorosh.bigdata.challenge.producer.ProducerAppInstance
import org.toshhorosh.bigdata.challenge.transformer.TransformerAppInstance

class MainSpec extends AnyFlatSpec {
  "configResolver" should "resolve .conf file" in {
    assert(configResolver(Producer).getInt("test") == 1)
  }

  it should "resolve .conf file 2" in {
    assert(configResolver(Consumer).getInt("test") == 2)
  }

  it should "resolve .conf file with ENV variable" in {
    assert(configResolver(Transformer).getInt("test") == 99)
  }

  "appResolver" should "return proper object type" in {
    assert(appResolver(Producer) == ProducerAppInstance)
    assert(appResolver(Transformer) == TransformerAppInstance)
    assert(appResolver(Consumer) == ConsumerAppInstance)
  }

}
