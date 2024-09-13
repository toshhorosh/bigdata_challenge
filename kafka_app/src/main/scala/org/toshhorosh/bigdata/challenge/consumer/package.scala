package org.toshhorosh.bigdata.challenge

import io.circe.Decoder
import nequi.circe.kafka
import io.circe.generic.auto._
import org.apache.kafka.common.serialization.Deserializer
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Encoder, Encoders, SparkSession}
import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

import scala.reflect.runtime.universe._

package object consumer {

  private val viewLogDecoder: Decoder[ViewLogRecord] = implicitly[Decoder[ViewLogRecord]]
  val viewLogDeserializer: Deserializer[ViewLogRecord] = kafka.decoder2deserializer(viewLogDecoder)
  val viewLogToSparkDsEncoder: Encoder[ViewLogRecord] = Encoders.product[ViewLogRecord]


  def getSpark: SparkSession = {
    SparkSession
      .builder
      .master("local")
      .config("spark.jars.packages", "org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.2")
      .getOrCreate()
  }

  def extractSparkSchema[T <: Product : TypeTag]: StructType =
    Encoders.product[T].schema
}
