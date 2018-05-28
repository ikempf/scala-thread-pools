package com.ikempf

import java.util.concurrent.Executors

import com.ikempf.common.time

import scala.concurrent.{ExecutionContext, Future}

object FixedThreadPool extends App {

  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4000))
  time(
    Future.traverse((0 until 3800).toList)(_ =>
      Future.successful {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        Thread.sleep(1000)
      }
    )
  )

}
