package com.bisnode.hazelfibo

import java.util.concurrent.TimeUnit

import org.junit.{After, Before, Test}
import org.scalatest.junit.AssertionsForJUnit

object RequesterTest 
{
  val RequestQueueId = "FiboRequestQueue" 
  val ResponseQueueId = "FiboResponseQueue"
}

@Test
class RequesterTest extends AssertionsForJUnit  
{
  
  val consumers = initialConsumers
  val producer = new Producer(RequesterTest.RequestQueueId, RequesterTest.ResponseQueueId)
  
  private lazy val initialConsumers: List[Consumer] = {
    List(
      new Consumer(RequesterTest.RequestQueueId),
      new Consumer(RequesterTest.RequestQueueId),
      new Consumer(RequesterTest.RequestQueueId))
  }
  
  @Before def startup 
  {
  }

  @After def shudown
  {
  }

  @Test def startConsumers = 
  {   
    Consumer.StartAll(consumers)
  }
  
  @Test def startProducer =
  {
    Producer.Start(producer, consumers.size, List(5, 7, 9))
  }

}