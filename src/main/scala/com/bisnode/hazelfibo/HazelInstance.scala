package com.bisnode.hazelfibo

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig

trait HazelInstance {

  def hazelcastClient = HazelcastClient.newHazelcastClient(new ClientConfig())
  
}