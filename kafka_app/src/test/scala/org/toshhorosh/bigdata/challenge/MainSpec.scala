package org.toshhorosh.bigdata.challenge

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.InstanceType.{Consumer, Producer}
import org.toshhorosh.bigdata.challenge.consumer.ConsumerAppInstance
import org.toshhorosh.bigdata.challenge.producer.ProducerAppInstance

class MainSpec extends AnyFlatSpec {
  "configResolver" should "resolve .conf file with ENV variable" in {
    assert(configResolver(Producer).getInt("test") == 99)
  }

  it should "resolve .conf file 2" in {
    assert(configResolver(Consumer).getInt("test") == 2)
  }

  "appResolver" should "return proper object type" in {
    assert(appResolver(Producer) == ProducerAppInstance)
    assert(appResolver(Consumer) == ConsumerAppInstance)
  }

}
