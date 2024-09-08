package org.toshhorosh.bigdata.challenge

import com.typesafe.config.Config

trait AppInstance {

  def run(config: Config): Unit
}
