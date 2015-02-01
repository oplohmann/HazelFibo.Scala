package com.bisnode.hazelfibo

import com.hazelcast.core.IQueue
import com.bisnode.hazelfibo.jobs.Job

trait Responder extends HazelInstance 
{
  protected val responseQueueId: String
  
  protected lazy val responseQueue: IQueue[(Long, Long)] = hazelcastInstance.getQueue(responseQueueId)
}