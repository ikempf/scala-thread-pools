package com.ikempf

import com.ikempf.common.time

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future, blocking}

object ForkJoinPoolBlocking extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global
  time(
    Future.traverse((0 until 10).toList)(_ =>
      Future {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        blocking(Thread.sleep(1000))
      }
    )
  )

}
