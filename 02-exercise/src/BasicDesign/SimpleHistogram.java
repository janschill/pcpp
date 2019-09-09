package BasicDesign;// For week 2
// sestoft@itu.dk * 2014-09-04
// thdy@itu.dk * 2019

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

abstract class Histogram {
    abstract void increment(int bin);

    abstract int getCount(int bin);

    abstract int getSpan();

    abstract int[] getBins();

    public void print() {
        for(int i = 0; i < getSpan(); i++) {
            System.out.println(i + ":\t\t\t" + getCount(i));
        }
    }
}

class SimpleHistogram {
    public static void main(String[] args) {
        final Histogram histogram = new Histogram1(30);
        histogram.increment(7);
        histogram.increment(13);
        histogram.increment(7);
        dump(histogram);
    }

    public static void dump(Histogram histogram) {
        int totalCount = 0;
        for (int bin = 0; bin < histogram.getSpan(); bin++) {
            System.out.printf("%4d: %9d%n", bin, histogram.getCount(bin));
            totalCount += histogram.getCount(bin);
        }
        System.out.printf("      %9d%n", totalCount);
    }
}

class Histogram1 extends Histogram {
    private int[] counts;

    public Histogram1(int span) {
        this.counts = new int[span];
    }

    public void increment(int bin) {
        counts[bin] = counts[bin] + 1;
    }

    public int getCount(int bin) {
        return counts[bin];
    }

    public int getSpan() {
        return counts.length;
    }

    @Override
    int[] getBins() {
        return counts.clone();
    }

}

class Histogram2 extends Histogram {

    private final int[] counts;

    public Histogram2(int span) {
        this.counts = new int[span];
    }

    public synchronized void increment(int bin) {
        counts[bin] = counts[bin] + 1;
    }

    public synchronized int getCount(int bin) {
        return counts[bin];
    }

    public int getSpan() {
        return counts.length;
    }

    @Override
    public synchronized int[] getBins() {
        return counts.clone();
    }
}

class Histogram3 extends Histogram {

    private final AtomicInteger[] counts;

    public Histogram3(int span) {
        this.counts = new AtomicInteger[span];
        for(int i = 0; i < span; i++) {
            counts[i] = new AtomicInteger(0);
        }
    }

    @Override
    void increment(int bin) {
        counts[bin].incrementAndGet();
    }

    @Override
    int getCount(int bin) {
        return counts[bin].get();
    }

    @Override
    int getSpan() {
        return counts.length;
    }

    @Override
    int[] getBins() {
        int[] bins = new int[getSpan()];
        for(int i = 0; i < getSpan(); i++) {
            bins[i] = getCount(i);
        }
        return bins;
    }
}

class Histogram4 extends Histogram {

    private final AtomicIntegerArray counts;

    public Histogram4(int span) {
        counts = new AtomicIntegerArray(span);
    }

    @Override
    void increment(int bin) {
        counts.incrementAndGet(bin);
    }

    @Override
    int getCount(int bin) {
        return counts.get(bin);
    }

    @Override
    int getSpan() {
        return counts.length();
    }

    @Override
    int[] getBins() {
        int[] bins = new int[getSpan()];
        for(int i = 0; i < getSpan(); i++) {
            bins[i] = getCount(i);
        }
        return bins;
    }
}

class Histogram5 extends Histogram {

    private final LongAdder[] counts;

    public Histogram5(int span) {
        counts = new LongAdder[span];
        for(int i = 0; i < counts.length; i++) {
            counts[i] = new LongAdder();
        }
    }

    @Override
    void increment(int bin) {
        counts[bin].increment();
    }

    @Override
    int getCount(int bin) {
        return counts[bin].intValue();
    }

    @Override
    int getSpan() {
        return counts.length;
    }

    @Override
    int[] getBins() {
        int[] bins = new int[getSpan()];
        for(int i = 0; i < getSpan(); i++) {
            bins[i] = getCount(i);
        }
        return bins;
    }
}

