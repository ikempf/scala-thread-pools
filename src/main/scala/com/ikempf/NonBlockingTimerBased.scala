package com.ikempf

import java.util.{Timer, TimerTask}

import com.ikempf.common.time

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future, Promise}

object NonBlockingTimerBased extends App {

  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  private val timer = new Timer()
  time(
    Future.traverse((0 until 100000).toList)(_ => {
      val p = Promise[Unit]()

      timer.schedule(new TimerTask {
        override def run(): Unit =
          ec.execute(() => {
            println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
            p.success(())
          })
      }, 1000)

      p.future
    })
  )

}
