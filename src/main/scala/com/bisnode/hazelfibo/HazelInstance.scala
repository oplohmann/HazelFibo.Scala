package com.bisnode.hazelfibo

import com.hazelcast.core.Hazelcast

trait HazelInstance {

  protected lazy val hazelcastInstance = Hazelcast.newHazelcastInstance
  
}