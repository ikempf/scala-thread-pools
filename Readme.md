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
