package com.ikempf

import java.util.concurrent.Executors

import com.ikempf.common.time

import scala.concurrent.{ExecutionContext, Future}

object CachedUnboundedThreadPool extends App {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())
  time(
    Future.traverse((0 until 10000).toList)(_ =>
      Future {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        Thread.sleep(1000)
      }
    )
  )

}
