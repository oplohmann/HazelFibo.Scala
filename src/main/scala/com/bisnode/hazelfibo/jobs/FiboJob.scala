package com.bisnode.hazelfibo.jobs

import com.hazelcast.core.IQueue
import com.bisnode.hazelfibo.HazelInstance

class FiboJob(val responseQueueId: String, val n: Long) extends Job with HazelInstance
{
  def isShutdown = false
  
  protected lazy val responseQueue: IQueue[(Long, Long)] = hazelcastInstance.getQueue(responseQueueId)
  
  def run: Unit = {
    responseQueue.add((n, fibonacci(n)))
  }
  
  def fibonacci(n: Long): Long = {
    if(n <= 1) {
      1
    } else {
      fibonacci(n - 1) + fibonacci(n - 2)
    } 
  }
  
}