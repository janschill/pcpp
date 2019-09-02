# Exercise 1.1

## 1

When increment is not synchronized the result is non deterministic. I.e. we get a different result most times.

## 2

When we reduce the count to 200, we often see the correct result. 
This is most likely because counting to 100 is such a rapid operation that the threads do not have time to change.

## 3

These notations are all shorthand for the same operation, only difference is the returned value. 
This means that every operation includes the same 3 steps, making the operations non-atomic.

## 4

If the methods are not synchronized, the final value would still be non deterministic. 
This is because even though the methods are different, the threads both access the same field.

## 5

### 1 No synchronization

Non deterministic result:

* Count is 207129 and should be 20000000
* Count is -242716 and should be 20000000
* Count is 183193 and should be 20000000

### 2 Decrement synchronized

Non deterministic result:

* Count is 160476 and should be 20000000
* Count is 152435 and should be 20000000
* Count is 146789 and should be 20000000

### 3 Increment synchronized

Non deterministic result:

* Count is -225733 and should be 20000000
* Count is -174695 and should be 20000000
* Count is -189368 and should be 20000000

### 4 Increment and decrement synchronized

Deterministic result:

* 0
* 0
* 0

# Exercise 1.2

## 1

The weaving happens when the first thread is sleeping and the second thread starts running after sleeping,
Because they are running independently, nothing prevents the second thread from running from the beginning of the method again.

## 2 

Making print synchronized allows only 1 thread to have access to the method at any one time.
This means that each thread will only allow the other thread to access the method once it has printed both symbols.

# Exercise 1.3

## 1

YES

## 2

YES

## 3

The synchronized keyword ensures variable visibility across threads. 
If the get method is not synchronized, there is nothing ensuring that the set update to the variable becomes visible on the second thread.

## 4

The volatile keyword ensures that any updates to the variable becomes visible across all threads.

# Exercise 1.4

## 1

Sequential execution:
59,67s


## 2

Parallel execution with 10 threads:
13,892s

## 3

It still produces the correct result.

## 4

Yes, it matters. If the get method was not synchronized, the main thread's call to get might not return the correct result.

# Exercise 1.5

## 1

Nope:

* Sum is 1906744,000000 and should be 2000000,000000
* Sum is 1941798,000000 and should be 2000000,000000
* Sum is 1854380,000000 and should be 2000000,000000
* Sum is 1903987,000000 and should be 2000000,000000

## 2

A synchronized static method uses a lock on the class object. 
A normal synchronized method uses a lock on the instance object.
Therefore, different threads can enter the two different methods at the same time, creating race conditions.

## 3

To make the Mystery class thread safe, we can add a synchronized block on the Mystery.class object to the body of addInstance method.

# Exercise 1.6

## 1

The simplest natural way of making the class DoubleArrayList thread safe is to simply add the synchronized keyword to every method.

## 2

This implementation is not very efficient as a single thread locks out all other threads while executing a method.

## 3

Introducting a lock for each method does allow a thread in each method, but it will not be thread safe as multiple threads will be able to access the same fields at the same time.

# Exercise 1.7

## 1

Adding a synchronized block on the class object around the 'totalSize++' statement and making totalSize synchronized 
will ensure that the field is updated correctly from different threads working on different objects.

## 2

Adding a synchronized block in the constructor and making the allLists method synchronized works in the same manner as the above.

# Exercise 1.8

## 1

The increment method of the MysteryA class uses the MysteryA.class object lock.
The increment4 method of the MysteryB class uses the MysteryB.class object lock.

## 2

In the increment4 method, add a synchronized(MysteryA.class) block around the code.


 



