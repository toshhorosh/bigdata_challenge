package org.toshhorosh.bigdata.challenge

import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import org.toshhorosh.bigdata.challenge.InstanceType.{Consumer, Producer}
import org.toshhorosh.bigdata.challenge.consumer.ConsumerAppInstance
import org.toshhorosh.bigdata.challenge.producer.ProducerAppInstance

import scala.util.{Failure, Success, Try}


object Main extends App with LazyLogging {

  require(args.length == 1, "Java argument should be provided. " +
    "Possible values: producer, consumer")

  Try {
    val instanceType = InstanceType(args.head)
    val config = configResolver(instanceType)

    logger.info(s"Starting an instance of $instanceType")
    appResolver(instanceType).run(config)

  } match {
    case Failure(e) => logger.error("Application failed, we catch the exception: ", e)
    case Success(_) =>
  }

  private[challenge] def appResolver(appType: InstanceType): AppInstance = {

    appType match {
      case Producer =>
        ProducerAppInstance
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
