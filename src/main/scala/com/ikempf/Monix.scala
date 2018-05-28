package com.ikempf

import java.util.concurrent.Executors

import com.ikempf.common.time
import monix.eval.Task
import monix.execution.Scheduler
import monix.execution.schedulers.SchedulerService

object Monix extends App {

  implicit val sc: SchedulerService = Scheduler.io()
  time(
    Task.gather((0 until 1000).toList.map(_ =>
      Task.apply {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        Thread.sleep(1000)
      })
    ).runAsync
  )

}

object MonixForkJoin extends App {

  implicit val sc: Scheduler = Scheduler.global
  time(
    Task.gather((0 until 1000).toList.map(_ =>
      Task.apply {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        Thread.sleep(1000)
      })
    ).runAsync
  )

}

object MonixTraverse extends App {

  implicit val sc: SchedulerService = Scheduler.io()
  time(
    Task.traverse((0 until 10).toList)(_ =>
      Task.apply {
        println(s"Threads ${Thread.activeCount()}, ${Thread.currentThread().getName}")
        Thread.sleep(1000)
      }
    ).runAsync
  )

}
