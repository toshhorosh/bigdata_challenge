package org.toshhorosh.bigdata

import com.typesafe.config.{Config, ConfigFactory}
import org.toshhorosh.bigdata.challenge.InstanceType.{Consumer, Producer}
import org.toshhorosh.bigdata.challenge.consumer.ConsumerAppInstance
import org.toshhorosh.bigdata.challenge.producer.ProducerAppInstance

package object challenge {
  def appResolver(appType: InstanceType): AppInstance = {

    appType match {
      case Producer =>
        ProducerAppInstance
      case Consumer =>
        ConsumerAppInstance
    }
  }

  def configResolver(instanceType: InstanceType): Config = {
    ConfigFactory
      .parseResources(s"${instanceType.name}.conf")
      .resolve()
  }
}
