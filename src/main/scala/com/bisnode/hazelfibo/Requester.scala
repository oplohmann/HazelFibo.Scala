package com.bisnode.hazelfibo

import com.bisnode.hazelfibo.utils.Config
import com.hazelcast.core.{IMap, IQueue}
import com.bisnode.hazelfibo.jobs.Job

trait Requester extends HazelInstance
{

  protected val config: Config
  
  protected lazy val requestQueue: IQueue[Job] = hazelcastClient.getQueue(config.requestQueueId)

  protected lazy val responseQueue: IQueue[(Long, Long)] = hazelcastClient.getQueue(config.responseQueueId)

  protected lazy val resultMap: IMap[Long, Long] = hazelcastClient.getMap(config.resultMapId)
  
}