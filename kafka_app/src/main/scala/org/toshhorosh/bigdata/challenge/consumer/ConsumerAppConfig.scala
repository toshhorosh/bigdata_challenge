package org.toshhorosh.bigdata.challenge.consumer

import com.typesafe.config.Config

class ConsumerAppConfig(config: Config) {

  private[consumer] val BOOTSTRAP_SERVERS_CONF_KEY = "kafka.bootstrap.servers"
  private[consumer] val TOPIC_NAME_CONF_KEY = "kafka.topic.name"
  private[consumer] val CHECKPOINT_LOCATION_CONF_KEY = "checkpoint.location"
  private[consumer] val CAMPAIGN_DICT_PATH_CONF_KEY = "data.campaign.dict.path"
  private[consumer] val VIEWS_REPORT_PATH_CONF_KEY = "data.views.report.path"

  val bootstrapServers: String = config.getString(BOOTSTRAP_SERVERS_CONF_KEY)

  val topic:String = config.getString(TOPIC_NAME_CONF_KEY)

  val checkpointLocation: String = config.getString(CHECKPOINT_LOCATION_CONF_KEY)

  val campaignsDictPath: String = config.getString(CAMPAIGN_DICT_PATH_CONF_KEY)

  val viewReportPath: String = config.getString(VIEWS_REPORT_PATH_CONF_KEY)

}
