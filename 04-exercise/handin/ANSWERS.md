# Exercises week 4

## Exercise 4.1

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


