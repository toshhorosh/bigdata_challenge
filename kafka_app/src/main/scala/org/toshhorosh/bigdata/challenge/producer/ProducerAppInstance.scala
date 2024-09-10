package org.toshhorosh.bigdata.challenge.producer

import com.typesafe.config.Config
import org.toshhorosh.bigdata.challenge.AppInstance

import scala.language.postfixOps

object ProducerAppInstance extends AppInstance {

  override def run(config: Config): Unit = {
    val limit_records = config.getInt("records_limit")

    DataGenerator(limit_records) andThen KafkaProducer(config)

  }
}
