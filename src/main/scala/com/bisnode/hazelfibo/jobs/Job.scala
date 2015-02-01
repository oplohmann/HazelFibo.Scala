package com.bisnode.hazelfibo.jobs

abstract class Job extends Serializable
{
  def isShutdown: Boolean
  def run: Unit
}