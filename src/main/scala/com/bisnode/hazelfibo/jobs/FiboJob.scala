package com.bisnode.hazelfibo.jobs

import java.util

import com.bisnode.hazelfibo.utils.Config
import com.hazelcast.core.IQueue
import com.bisnode.hazelfibo.{Requester, HazelInstance}

class FiboJob(val config: Config, val n: Long) extends Job with Requester
{
  def isShutdown = false
  
  def run: Unit =
  {
    val existingResult = Option(resultMap.get(n))
    val result = existingResult.getOrElse(calculate)
    responseQueue.add((n, result))
  }

  def calculate: Long =
  {
    val results = new util.TreeSet[Long](resultMap.keySet())
    val nearestLeftMostResult = Option(results.floor(n - 1))
    val nearestRightMostResult = getNearestRightMostResult(n, results, nearestLeftMostResult)

    val result = if (nearestLeftMostResult.isDefined || nearestRightMostResult.isDefined) {
      fibonacciCached(n, nearestLeftMostResult, nearestRightMostResult)
    }
    else {
      fibonacci(n)
    }

    resultMap.putIfAbsent(n, result)
    result
  }

  private def getNearestRightMostResult(n: Long, results: util.TreeSet[Long], nearestLeftMostResult: Option[Long]): Option[Long] =
  {
    if(!nearestLeftMostResult.isDefined) {
      return None
    }

    if(results.size() == 1) {
      return None
    }

    val resultsList = new util.ArrayList(results)
    val index: Int = resultsList.indexOf(nearestLeftMostResult.get)

    if(index == 0 || index == -1) {
      None
    } else {
      Option(resultsList.get(index - 1))
    }
  }

  private def fibonacciCached(n: Long, nearestLeftMostResult: Option[Long], nearestRightMostResult: Option[Long]): Long =
  {
    if(nearestLeftMostResult.isDefined && nearestLeftMostResult.get == n) {
      return resultMap.get(nearestLeftMostResult.get)
    }
    if(nearestRightMostResult.isDefined && nearestRightMostResult.get == n) {
      return resultMap.get(nearestRightMostResult.get)
    }

    if(n <= 1) {
      1
    } else {
      fibonacciCached(n - 1, nearestLeftMostResult, nearestRightMostResult) + fibonacciCached(n - 2, nearestLeftMostResult, nearestRightMostResult)
    }
  }

  private def fibonacci(n: Long): Long = {
    if(n <= 1) {
      1
    } else {
      fibonacci(n - 1) + fibonacci(n - 2)
    } 
  }
  
}