package com.bisnode.hazelfibo.jobs

class ShutdownJob extends Job 
{
  def isShutdown = true
  
  def run: Unit = new RuntimeException("invalid message for this kind of object")
}