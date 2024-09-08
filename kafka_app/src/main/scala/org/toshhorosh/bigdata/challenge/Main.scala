package org.toshhorosh.bigdata.challenge

import com.typesafe.config.{Config, ConfigFactory}
import org.toshhorosh.bigdata.challenge.InstanceType.{Consumer, Producer, Transformer}
import org.toshhorosh.bigdata.challenge.consumer.ConsumerAppInstance
import org.toshhorosh.bigdata.challenge.producer.ProducerAppInstance
import org.toshhorosh.bigdata.challenge.transformer.TransformerAppInstance

import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}


object Main extends App with LazyLogging {

  require(args.length == 1, "Java argument should be provided: producer, transformer, consumer")

  Try {
    val instanceType = InstanceType(args.head)
    val config = configResolver(instanceType)

    appResolver(instanceType).run(config)

  } match {
    case Failure(e) => logger.error("Application failed, we catch the exception: ", e)
    case Success(_) =>
  }

  private[challenge] def appResolver(appType: InstanceType): AppInstance = {

    appType match {
      case Producer =>
        ProducerAppInstance
      case Transformer =>
        TransformerAppInstance
      case Consumer =>
        ConsumerAppInstance
    }
  }

  private[challenge] def configResolver(instanceType: InstanceType): Config = {
    ConfigFactory
      .parseResources(s"${instanceType.name}.conf")
      .resolve()
  }


}
