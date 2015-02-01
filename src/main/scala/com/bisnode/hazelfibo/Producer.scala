package com.bisnode.hazelfibo

import com.bisnode.hazelfibo.jobs.{Job, FiboJob, ShutdownJob}
import com.bisnode.hazelfibo.utils.Config

import com.bisnode.hazelfibo.utils.TimesUtil._
import com.hazelcast.core.IQueue
import org.slf4j.{LoggerFactory, Logger}

object Producer {

  private val LOG: Logger = LoggerFactory.getLogger(classOf[Producer]);

  def Start(producer: Producer, numberOfConsumbers: Int, fibonacciNumbers: List[Int]) = {
   producer.
     calculate(fibonacciNumbers).
     processResponses(numberOfConsumbers, fibonacciNumbers.size)

    Producer.LOG.info(this + " shutting down ...")
  }
  
}

class Producer(override val config: Config) extends Requester
{

    def calculate(fibonacciNumbers: List[Int]): Producer =
    {
      fibonacciNumbers foreach { n => requestQueue.add(new FiboJob(config, n)) }
      this
    }
    
    def processResponses(numberOfConsumbers: Int, fiboJobs: Int): Unit = 
    {
      fiboJobs times {
        val result: (Long, Long) = responseQueue.take() 
        Producer.LOG.info("Fibonacci number of " + result._1 + " is: " + result._2)
      }

      Producer.LOG.info("Sending shutdown jobs to consumers ...")
      numberOfConsumbers times { requestQueue.add(new ShutdownJob) }
      Producer.LOG.info("Done sending shutdown jobs.")
    }
  
}