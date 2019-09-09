public class MyAtomicInteger {

    private volatile int value;

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    public int addAndGet(int i) {
        synchronized (MyAtomicInteger.class) {
            value += i;
        }
        return value;
    }

    public int get() {
        return value;
    }


}