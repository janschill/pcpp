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

