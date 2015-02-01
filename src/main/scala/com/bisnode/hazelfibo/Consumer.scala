package com.bisnode.hazelfibo

import com.bisnode.hazelfibo.utils.Config
import com.hazelcast.core.Hazelcast
import org.slf4j.{LoggerFactory, Logger}

object Consumer {

  private val LOG: Logger = LoggerFactory.getLogger(classOf[Consumer]);

  def StartAll(consumers: List[Consumer]) = consumers.foreach { _.start }
  
}

class Consumer(override val config: Config) extends Requester
{
  def start = new Thread { spawn }.start

  private def spawn: Unit =
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