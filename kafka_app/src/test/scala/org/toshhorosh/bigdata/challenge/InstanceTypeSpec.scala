package org.toshhorosh.bigdata.challenge

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.InstanceType.{Consumer, Producer, Transformer}

class InstanceTypeSpec extends AnyFlatSpec {

  "InstanceType" should "return proper types" in {
    assert(InstanceType("consumer") == Consumer )
    assert(InstanceType("transformer") == Transformer )
    assert(InstanceType("producer") == Producer )
  }

  it should "throw an exception due to wrong argument" in {
    assertThrows[IllegalArgumentException](InstanceType("test"))
  }

}
