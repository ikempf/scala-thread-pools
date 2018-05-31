package com.ikempf

import akka.actor.ActorSystem

import scala.concurrent.{ExecutionContextExecutor, Future, Promise}
import scala.concurrent.duration._
import com.ikempf.common.time

object NonBlockingAkkaBased extends App {

  implicit val system: ActorSystem          = ActorSystem()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  time(
    Future.traverse((0 until 10000000).toList)(_ => {
      val p = Promise[Unit]()

      system.scheduler.scheduleOnce(1.seconds, new Runnable {
        override def run(): Unit = {
          println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
          p.success(())
        }
      })

      p.future
    })
  )

}
