package com.bisnode.hazelfibo

import com.hazelcast.core.Hazelcast
import org.slf4j.{LoggerFactory, Logger}

object Consumer {

  private val LOG: Logger = LoggerFactory.getLogger(classOf[Consumer]);

  def StartAll(consumers: List[Consumer]) = consumers.foreach { _.start }
  
}

class Consumer(override protected val requestQueueId: String) extends Requester with HazelInstance 
{
  def start = new Thread { spawn }.start  
  
  def spawn: Unit = 
  {
    while(true) 
    {
      val job = requestQueue.take()    
      if(job.isShutdown) {
        Consumer.LOG.info(this + " shutting down ...")
        return
      }
      
      job.run
    }  
  }
  
}