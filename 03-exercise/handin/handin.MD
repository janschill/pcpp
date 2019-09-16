# 3.1

## 1

- CPU: Intel(R) Core(TM) i5-8350U CPU @ 1.70GHz 1.90 GHz
- 8 GB RAM

### Comments

- Mark1: Result is as expected and as in the notes. The function call to the expensive operation may have been removed as they are not used anywhere.
- Mark2: The result from Mark2 more precisely correspond to the workload of the function, as the result is not discarded. This is as in the notes.
- Mark3: The multiple measurements give almost exactly the same result as in Mark2, which is what is expected.
- Mark4: The standard deviation output also corresponds to the former functions 
- Mark5: As expected, we see the running time converging to match that of previous runs. We do not see any dramatic deviations, but a minor one of 28.1 ns where the standard deviation is 15.15.
- Mark6: In Mark6 we also get the expected results. As the number of invocations increase, we see a flattening of the running time, and it converges towards the expected running time that we've seen in the other benchmarks.

## 2

### Comments

- Mark7: The results are similar to the ones in the notes in the sense that most functions have similar running times. Except 'asin' and 'acos' which have significantly longer running times (on both tested PCs).
		 Something to note is that on a faster CPU the simpler functions are faster, but the slower ones are much slower than on the slower PC.
		 This might be due to optimizations on the faster CPU, that work for simpler computations but not advanced computations.
		 
# 3.2

## 1
We don't see anything out of the ordinary except for a hashCode run that takes an insane amount of time. The runtime converges towards 2.5ns but after the initial run of 575ns (which we expect to be the slowest), we suddenly get a run that takes 9713ns when count = 32.
This could be a scheduling hiccup.

## 2
The observation that we make is that the PC from the slides do smaller operations slower but heavier operations faster than our test PC.
Our test PC has 8 cores and the PC from the slides has 4 cores (the Intel one). This corresponds to the result we saw earlier when comparing the result between our 2 test PCs, as the secondary one is almost similar to the one on the slides.
We are not sure why this is the case. 

# 3.3

## 3
We observe that the running time of the thread operations decreases as we near 8 threads, which is also the number of cores on our test PC.
It hits a minumum in the running time at 9 threads. 
Also, as the number of threads increase beyond this point, the running time steadily increases to take longer with 32 threads than with 4.
This is probably because the thread handling logic starts to interfere and increase the running time of the computation.

## 4
The performance of AtomicLong is in general better than that of the LongCounter. Not by a lot, but in general one should always use tried and tested built-in classes.

## 5
Yes, it is faster.

# 3.4

1. Memoizer1 result: 26 125 445.6 ns 		89381.66 	16
2. Memoizer2 result: 5 444 888.3 ns 		39026.89 	64 
3. Memoizer3 result: 5 026 329.5 ns			43791.58	64
4. Memoizer4 result: 6 770 779.8 ns			35585.89	64
5. Memoizer5 result: 11 544 456.3 ns		106659.79	32
6. Memoizer0 result: 9 395 501.6 ns			213444.30	32

## 7
We see in the results that there is a balance between locking and cache misses.
Complicated locking schemes in order to avoid relatively (at times) inexpensive cache misses, may end up slowing down the execution.
The point of the caches was to decrease execution time, but too much complication may not be beneficial, as we see.

## 8
To determine if a cache scheme is effective, we need to check both big and small computations with many threads, and then make an average. 
Using a complicated cache scheme to not compute perform the same small computation twice, may be less efficient.
Similarly, using a complicated cache scheme to avoid making an expensive computation will ususally be worthwhile.

		 