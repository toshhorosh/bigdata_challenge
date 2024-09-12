package org.toshhorosh.bigdata.challenge


sealed abstract class InstanceType(val name: String)

object InstanceType {
  case object Producer extends InstanceType("producer")
  case object Consumer extends InstanceType("consumer")

  def apply(str: String): InstanceType = {
    str match {
      case Producer.name => Producer
      case Consumer.name => Consumer
      case _ => throw new IllegalArgumentException(s"Possible values: ${Producer.name}, ${Consumer.name}")
    }
  }
}
