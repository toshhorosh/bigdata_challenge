package org.toshhorosh.bigdata.challenge.consumer

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.StructType

object BatchDataLoader {

  def loadFromCsvDataFrame(path: String, schema: StructType): DataFrame = {
    getSpark
      .read
      .schema(schema)
      .option("delimiter", ",")
      .option("header", value = true)
      .csv(path)
  }


}
