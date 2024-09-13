package org.toshhorosh.bigdata.challenge

import io.circe.Encoder
import io.circe.generic.auto._
import nequi.circe.kafka
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.Serializer
import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

package object producer {

  private val enc: Encoder[ViewLogRecord] = implicitly[Encoder[ViewLogRecord]]
  val viewLogToJsonSerializer: Serializer[ViewLogRecord] = kafka.encoder2serializer(enc)

  def recordMetadataToString(metadata: RecordMetadata): String =
    s"""topic: ${metadata.topic()},
       | partition: ${metadata.partition()},
       | offset: ${metadata.offset()}
       | (ts: ${metadata.timestamp()})""".stripMargin.replace("\n", "")

  def getRandomDurationMillis: Long = {
    val start = 0
    val end = 5
    val rnd = new scala.util.Random
    (start + rnd.nextInt((end - start) + 1)) * 1000
  }
}

