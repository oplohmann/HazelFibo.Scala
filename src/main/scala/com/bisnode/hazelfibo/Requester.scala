package com.bisnode.hazelfibo

import com.hazelcast.core.IQueue
import com.bisnode.hazelfibo.jobs.Job

trait Requester extends HazelInstance
{

  protected val requestQueueId: String
  
  protected lazy val requestQueue: IQueue[Job] = hazelcastClient.getQueue(requestQueueId)
  
}