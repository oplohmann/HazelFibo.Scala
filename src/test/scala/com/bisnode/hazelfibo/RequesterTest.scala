package com.bisnode.hazelfibo

import com.bisnode.hazelfibo.utils.Config
import com.hazelcast.core.Hazelcast
import org.junit.{After, Before, Test}
import org.scalatest.junit.AssertionsForJUnit

object RequesterTest 
{
  val RequestQueueId = "FiboRequestQueue" 
  val ResponseQueueId = "FiboResponseQueue"
  val ResultMapId = "FiboResultMap"
}

@Test
class RequesterTest extends AssertionsForJUnit  
{
  
  val consumers = initialConsumers
  val producer = new Producer(config)

  private lazy val config: Config = {
    new Config(
      RequesterTest.RequestQueueId,
      RequesterTest.ResponseQueueId,
      RequesterTest.ResultMapId)
  }

  private lazy val initialConsumers: List[Consumer] = {
    List(new Consumer(config), new Consumer(config), new Consumer(config))
  }
  
  @Before def startup: Unit =
  {
    Hazelcast.newHazelcastInstance()
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
    Producer.Start(producer, consumers.size, List(5, 7, 9, 5, 12, 4, 14, 6, 8, 10, 11, 4, 3, 15, 18, 21, 22))
  }

}