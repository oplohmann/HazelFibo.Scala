package com.bisnode.hazelfibo

import com.bisnode.hazelfibo.jobs.FiboJob

import com.bisnode.hazelfibo.utils.TimesUtil._
import com.bisnode.hazelfibo.jobs.ShutdownJob
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

class Producer(override protected val requestQueueId: String, override protected val responseQueueId: String) extends Requester with Responder
{

    def calculate(fibonacciNumbers: List[Int]): Producer = 
    {
      fibonacciNumbers foreach { n => requestQueue.add(new FiboJob(responseQueueId, n)) }  
      this
    }
    
    def processResponses(numberOfConsumbers: Int, fiboJobs: Int): Unit = 
    {
      fiboJobs times {
        val result: (Long, Long) = responseQueue.take() 
        Producer.LOG.info("Fibonacci number of " + result._1 + " is: " + result._2)
      }

      Producer.LOG.info("sending shutdown jobs")
      numberOfConsumbers times { requestQueue.add(new ShutdownJob) }
      Producer.LOG.info("done sending shutdown jobs")
    }
  
}