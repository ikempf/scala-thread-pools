package com.ikempf

import com.ikempf.common.time

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}

object ForkJoinPool extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  time(
    Future.traverse((0 until 100).toList)(_ =>
      Future {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        Thread.sleep(1000)
      }
    )
  )

}
