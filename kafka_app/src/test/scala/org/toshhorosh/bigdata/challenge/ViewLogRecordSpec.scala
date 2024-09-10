package org.toshhorosh.bigdata.challenge

import org.scalatest.flatspec.AnyFlatSpec
import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

import java.util.UUID

class ViewLogRecordSpec extends AnyFlatSpec {

  "ViewLogRecord" should "contain proper fields" in {
    val record = ViewLogRecord()
    val campIds = Array(10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140)

    assert(campIds.contains(record.campaignId))
    assert(record.bannerId > 0)
    assert(record.endTimestamp.compareTo(record.startTimestamp) == 1)
  }
}
