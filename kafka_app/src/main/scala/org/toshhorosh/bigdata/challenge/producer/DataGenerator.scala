package org.toshhorosh.bigdata.challenge.producer

import org.toshhorosh.bigdata.challenge.data.Record

class DataGenerator extends Function1[Int, Vector[Record]] {

  override def apply(limit: Int): Vector[Record] = ???
}
