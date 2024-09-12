package org.toshhorosh.bigdata.challenge.producer

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

class DataGeneratorSpec extends AnyFlatSpec {

  "DataGenerator" should "return correct number of ViewLogRecords" in {
    val result = DataGenerator(5)

    assert(result.nonEmpty)
    assert(result.size == 5)
    assert(result.head.isInstanceOf[ViewLogRecord])
  }
}
