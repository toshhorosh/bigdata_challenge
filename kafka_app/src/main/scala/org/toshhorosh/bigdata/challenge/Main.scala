package org.toshhorosh.bigdata.challenge

import com.typesafe.scalalogging.LazyLogging

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
}