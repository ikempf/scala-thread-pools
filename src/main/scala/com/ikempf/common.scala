package com.ikempf

import java.time.Duration
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

object common {

  def time[A](thunk: => Future[A]): A = {
    val start = System.nanoTime()
    val a = Await.result(thunk, 5.minutes)
    val end = System.nanoTime()

    println(s"Took ${Duration.ofNanos(end - start).toMillis} ms" )
    a
  }

}
