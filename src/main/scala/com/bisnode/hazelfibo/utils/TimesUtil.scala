package com.bisnode.hazelfibo.utils

object TimesUtil {

  implicit def intTimes(i: Int) = new {
    def times(fn: => Unit) = (1 to i) foreach (x => fn)
  }
  
}