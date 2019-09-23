# Exercises week 4

## Exercise 4.1 1. remove

```java
public FunList<T> remove(T x) {
  return new FunList<T>(remove(x, this.first));
}

protected static <T> Node<T> remove(T x, Node<T> xs) {
  if (xs == null) {
    return null;
  }
  return x.equals(xs.item) ? remove(x, xs.next) : new Node<T>(xs.item, remove(x, xs.next));
}
```

## Exercise 4.1 2. count

```java
public int count(Predicate<T> p) {
  return count(p, this.first, 0);
}

protected static <T> int count(Predicate<T> p, Node<T> xs, int count) {
  if (xs == null) {
    return count;
  }

  return count(p, xs.next, p.test(xs.item) ? ++count : count);
}
```

## Exercise 4.1 3. filter

```java
public FunList<T> filter(Predicate<T> p) {
  return new FunList<T>(filter(p, this.first));
}

protected static <T> Node<T> filter(Predicate<T> p, Node<T> xs) {
  if (xs == null) {
    return xs;
  }
  return p.test(xs.item) ? new Node<T>(xs.item, filter(p, xs.next)) : filter(p, xs.next);
}
```

## Exercise 4.1 4. removeFun

```java
public FunList<T> removeFun(T x) {
  return filter((p -> p != x));
}
```

## Exercise 4.1 5. flatten

```java
public static <T> FunList<T> flatten(FunList<FunList<T>> xss) {
  FunList.Node<FunList<T>> current = xss.first;
  FunList<T> funList = new FunList<T>(current.item.first);
  while (current.next != null) {
    funList = new FunList<T>(append(funList.first, current.next.item.first));
    current = current.next;
  }

  return funList;
}
```

## Exercise 4.1 6. flattenFun

```java
public static <T> FunList<T> flattenFun(FunList<FunList<T>> xss) {
  return xss.reduce(new FunList<T>(), FunList::append);
}
```

## Exercise 4.1 7. flatMap

### Iterative

```java
public <U> FunList<U> flatMap(Function<T, FunList<U>> f) {
  FunList.Node<T> current = this.first;
  FunList<U> funList = new FunList<U>();
  while (current != null) {
    funList = funList.append(f.apply(current.item));
    current = current.next;
  }
  return funList;
}
```

### Using map and flatten

```java
public <U> FunList<U> flatMapFun(Function<T, FunList<U>> f) {
  return flatten(map(f));
}
```

## Exercise 4.1 8. scan

```java
FunList<T> scan(BinaryOperator<T> f) {
  return null;
}
```

## Exercise 4.2

Not with current implementation. But if the second argument in the contructor (next) is changed from the type Node<U> to a function, which generates a node and can be evaluated lazily, then it is possible.

## Exercise 4.3

## Exercise 4.5

### 4.5 1. Sum

Runtime: 12.321s
Result: -0,0000000010000001

```java
static double computeSum(float n) {
  return IntStream.range(1, (int) n).mapToDouble((i) -> 1.0 / i / i).sum();
}
```

### 4.5 2. SumParallel

Runtime: 2.71s
Result: -0,0000000010000001

```java
static double computeSumParallel(float n) {
  return IntStream.range(1, (int) n).mapToDouble((i) -> 1.0 / i / i).parallel().sum();
}
```

### 4.5 3. SumSequential For-loop

Runtime: 8.465s
Result: -0,0000000090136514

```java
static double computeSumSequential(float n) {
  double sum = 0;

  for (int i = 1; i <= (int) n; i++) {
    double x = 1.0 / i / i;
    sum += x;
  }

  return sum;
}
```

### 4.5 4. SumStreamSequential Generator Limit

Runtime: 13.481s
Result: -0,0000000010000001

```java
static double computeSumStreamSequential(float n) {
  DoubleSupplier generator = new DoubleSupplier(){
    int current = 1;

    @Override
    public double getAsDouble() {
      double result = 1.0 / current / current;
      current++;
      return result;
    }
  };
  return DoubleStream.generate(generator).limit((long) n).sum();
}
```

### 4.5 6. SumStreamParallel Generator Limit

Runtime: 4.274s
Result: 0,0248307532200600

```java
static double computeSumStreamParallel(float n) {
  DoubleSupplier generator = new DoubleSupplier(){
    int current = 1;

    @Override
    public double getAsDouble() {
      double result = 1.0 / current / current;
      current++;
      return result;
    }
  };
  return DoubleStream.generate(generator).limit((long) n).parallel().sum();
}
```
