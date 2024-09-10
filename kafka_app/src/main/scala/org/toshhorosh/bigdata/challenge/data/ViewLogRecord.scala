package org.toshhorosh.bigdata.challenge.data

import org.scalacheck.Gen

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


case class ViewLogRecord(viewId: String = ViewLogRecord.getViewId(),
                         startTimestamp: LocalDateTime = ViewLogRecord.getStartTimestamp(),
                         endTimestamp: LocalDateTime = ViewLogRecord.getEndTimestamp(),
                         bannerId: Long = ViewLogRecord.getBannerId(),
                         campaignId: Int = ViewLogRecord.getCampaignId())

object ViewLogRecord {
  def getViewId(): String = Gen.uuid.sample.get.toString

  def getStartTimestamp(): LocalDateTime = LocalDateTime.now()
    .minusSeconds(Gen.oneOf[Long](1L to 100L).sample.get)
    .truncatedTo(ChronoUnit.MILLIS)

  def getEndTimestamp(): LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS)

  def getBannerId(): Long = Gen.oneOf[Long](1L to 1000L).sample.get

  def getCampaignId(): Int = Gen.frequency[Int](
    (1, 10),
    (1, 20),
    (1, 30),
    (2, 40),
    (2, 50),
    (3, 60),
    (3, 70),
    (3, 80),
    (2, 90),
    (2, 100),
    (1, 110),
    (1, 120),
    (1, 130),
    (1, 140)
  ).sample.get
}

