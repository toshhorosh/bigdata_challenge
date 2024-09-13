package org.toshhorosh.bigdata.challenge.producer

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

import java.time.ZoneOffset


class DataGeneratorSpec extends AnyFlatSpec {

  "DataGenerator" should "return correct number of ViewLogRecords" in {
    val result = DataGenerator(5)

    assert(result.nonEmpty)
    assert(result.size == 5)
    assert(result.head.isInstanceOf[ViewLogRecord])
  }

  "DataGenerator" should "evaluate records lazily only when they are called" in {
    val result = DataGenerator(3)

    val seconds = result.map[Long] {
      r =>
        Thread.sleep(5000)
        r.endTimestamp.toEpochSecond(ZoneOffset.UTC)
    }.toList

    val normalized = seconds.map {
      s => s - seconds.head
    }

    assert(normalized === List(0, 5, 10))
  }
}
