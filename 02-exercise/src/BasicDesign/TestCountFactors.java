package BasicDesign;// For week 2
// sestoft@itu.dk * 2014-08-29

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class TestCountFactors {
    public static void main(String[] args) {
        final int range = 5_000_000;
        int count = 0;
        double start = System.currentTimeMillis();
        //for (int p = 0; p < range; p++)
        //    count += countPrimeFactors(p);

        Histogram h = countPrimeFactorsParallelHistogram(5_000_000, 10);
        double end = System.currentTimeMillis();
        System.out.println("Done in time " + ((end - start) / 1000) + "s");
        //System.out.printf("Total number of factors is %9d%n", count);

        h.print();

    }



    public static int countPrimeFactors(int p) {
        if (p < 2)
            return 0;
        int factorCount = 1, k = 2;
        while (p >= k * k) {
            if (p % k == 0) {
                factorCount++;
                p /= k;
            } else {
                k++;
            }
        }
        return factorCount;
    }


    public static Histogram countPrimeFactorsParallelHistogram(final int p, int nThreads) {
        //final Histogram2 histogram = new Histogram2(30);
        //final Histogram3 histogram = new Histogram3(30);
        //final Histogram4 histogram = new Histogram4(30);
        final Histogram5 histogram = new Histogram5(30);
        List<Thread> threads = new ArrayList<>();

        int rangeFactor = p / nThreads;
        Thread thread;
        for(int i = 0; i < nThreads; i++) {
            final int lower = i * rangeFactor;
            final int upper = (i + 1) * rangeFactor;

            thread = new Thread(() -> {
                for(int j = lower; j < upper; j++) {
                    histogram.increment(countPrimeFactors(j));
                }
            });
            threads.add(thread);

            thread.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return histogram;




    }


    public static int countPrimeFactorsParallel(final int p, int nThreads) {
        final AtomicInteger counter = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        int rangeFactor = p / nThreads;
        Thread thread;
        for(int i = 0; i < nThreads; i++) {
            final int lower = i * rangeFactor;
            final int upper = (i + 1) * rangeFactor;

            thread = new Thread(() -> {
                for(int j = lower; j < upper; j++) {
                    counter.addAndGet(countPrimeFactors(j));
                }
            });
            threads.add(thread);

            thread.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return counter.get();


    }

    // Just some code I wrote because I misunderstood the exercises.
    // This counts the factors of a single number using multiple threads.
    // You won't need this for anything.
    public static int countFactorsParallel(final int p, int n) {
        final int sqrt = (int) Math.sqrt(p);
        final AtomicInteger count = new AtomicInteger();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            final int ti = i;
            threads.add(new Thread(() -> {
                for (int v = ti*sqrt/n+1; v <= (ti+1)*sqrt/n; v++) {
                    if (p % v == 0) {
                        if (v*v == p) count.addAndGet(1);
                        else count.addAndGet(2);
                    }
                }
            }));
            threads.get(threads.size()-1).start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return count.get();
    }
}
