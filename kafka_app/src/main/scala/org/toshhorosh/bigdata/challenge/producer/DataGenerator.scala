package org.toshhorosh.bigdata.challenge.producer

import org.toshhorosh.bigdata.challenge.data.ViewLogRecord

object DataGenerator extends ((Int) => LazyList[ViewLogRecord]) {

  override def apply(limit: Int): LazyList[ViewLogRecord] = {

    LazyList.fill[ViewLogRecord](limit)(ViewLogRecord())
  }
}
