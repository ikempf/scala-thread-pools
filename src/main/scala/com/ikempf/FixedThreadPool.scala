package com.ikempf

import java.util.concurrent.Executors

import com.ikempf.common.time

import scala.concurrent.{ExecutionContext, Future, blocking}

object FixedThreadPool extends App {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4000))
  time(
    Future.traverse((0 until 10000).toList)(_ =>
      Future {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        blocking(Thread.sleep(1000))
      }
    )
  )


}
