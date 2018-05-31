# Config
Intel(R) Core(TM) i7-4770HQ CPU @ 2.20GHz
4 physical cpus, 8 logical cpus


## ForkJoinPool (ExecutionContext.global)
```
ForkJoinPool spawns 1 thread per logical cpu (scala.concurrent.context.maxThreads)
Ideal for compute heavy stuff (or async), bad for blocking
This thread pool can quickly deadlock, blocking code is very risky in concurrent situations.

10 tasks : time ≈ 2109 ms
10 / 8 = 1.25, 2 cycles
 
100 tasks : time ≈ 13151 ms
100 / 8 = 12.5, 13 cycles
```

## ForkJoinPool (ExecutionContext.global) using scala.concurrent.blocking
```
blocking() allows ForkJoinPool to identify blocking code and to go over the logical-cpu thread count limit
The maximum number of threads is 256 + 8 (scala.concurrent.context.maxExtraThreads + scala.concurrent.context.maxThreads)
This thread pool can still deadlock.
Created extra threads are removed after usage.

10 tasks : time ≈ 1115 ms
10 < 266, 1 cycle
 
100 tasks : time ≈ 1132 ms
100 < 266, 1 cycle

1000 tasks : time ≈ 4223 ms
1000 / 264 = 3.7, 4 cycles ≈ 4000ms 

10000 tasks : time 38781 ms
10000 / 264 = 37.9, 38 cycles ≈ 38000ms 
```

## FixedThreadPool
```
Very efficient when the maximum number of needed threads is known. 
This thread pool can still deadlock even when the number of threads is big.

10 tasks, 10 threads : time ≈ 1113 ms

1000 tasks, 100 threads : time ≈ 10169 ms

1000 tasks, 1000 threads : time ≈ 1273 ms

10000 tasks, 4000 threads : time ≈ 3791 ms

10000 tasks, 5000 threads
OOM T_T, 

10000 tasks, 10000 threads
OOM T_T, 
```

## CachedUnboundedThreadPool
```
Creates thread as needed and let's them live for up to 1 min (by default) before garbage collecting them.
Since there is no upper limit there is no risk of deadlock. 
Looking at ThreadPoolExecutor.addWorker(), there should to be some kind of throttling of new thread allocations when 
there are no native threads left on the os BUT this mechanism does not seem to work every time ?
The JVM will OOMs when there are too many parallel tasks, in this case oom is not caused
by a memory issue but by the os not allocating any more native threads to the JVM. Might crash your OS (no native threads left).

10 tasks, 10 threads : time ≈ 1119 ms

1000 tasks, 1000 threads : time ≈ 1118 ms

4000 tasks, spawns 3560 threads : time ≈ 2272 ms

10000 tasks, spawns ≈ 4026 threads : time ≈ 2272 ms
```

## Monix
```
Scheduler.global is a ForkJoinPool (it uses ExecutionContext.global)

Scheduler.io() is an UnboundedCachedThreadPool made for io.
```

## NonBlockingTimerBased
```
Scales a lot more. 
Uses a lot of CPU. Could probably use less if 
 - Timer's internal queue was correctly size
 - event's were not ordered in the internal queue but simply treated FIFO-wise 
1 timer thread (event-loop) and a ForkJoinPool (8 threads)


1000 tasks, 1 thread + 8 threads : time ≈ 1138 ms

100000 tasks, 1 thread + 8 threads : time ≈ 1594 ms

1000000 tasks, 1 thread + 8 threads : time ≈ 6029 ms

```

