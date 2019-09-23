import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Ex4_5 {
  public static void main(String[] args) {
    float n = 1_000_000_000;
    System.out.printf("-(1/1_000_000_000) = %20.16f%n", -(1 / n));
    double sum = computeSum(n);
    System.out.printf("Sum                 = %20.16f%n", sum - Math.PI * Math.PI / 6);
    double sumParallel = computeSumParallel(n);
    System.out.printf("SumParallel         = %20.16f%n", sumParallel - Math.PI * Math.PI / 6);
    double sumSequential = computeSumSequential(n);
    System.out.printf("SumSequential       = %20.16f%n", sumSequential - Math.PI * Math.PI / 6);
    double sumStreamSequential = computeSumStreamSequential(n);
    System.out.printf("SumStreamSequential = %20.16f%n", sumStreamSequential - Math.PI * Math.PI / 6);
    double sumStreamParallel = computeSumStreamParallel(n);
    System.out.printf("SumStreamParallel   = %20.16f%n", sumStreamParallel - Math.PI * Math.PI / 6);
  }

  static double computeSum(float n) {
    double start = System.currentTimeMillis();
    double sum = IntStream.range(1, (int) n).mapToDouble((i) -> 1.0 / i / i).sum();
    double stop = System.currentTimeMillis();
    System.out.println("computeSum: " + (stop - start) / 1000 + "s");

    return sum;
  }

  static double computeSumParallel(float n) {
    double start = System.currentTimeMillis();
    double sum = IntStream.range(1, (int) n).mapToDouble((i) -> 1.0 / i / i).parallel().sum();
    double stop = System.currentTimeMillis();
    System.out.println("computeSumParallel: " + (stop - start) / 1000 + "s");

    return sum;
  }

  static double computeSumSequential(float n) {
    double start = System.currentTimeMillis();
    double sum = 0;

    for (int i = 1; i <= (int) n; i++) {
      double x = 1.0 / i / i;
      sum += x;
    }
    double stop = System.currentTimeMillis();
    System.out.println("computeSumSequential: " + (stop - start) / 1000 + "s");

    return sum;
  }

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
    double start = System.currentTimeMillis();
    double sum = DoubleStream.generate(generator).limit((long) n).sum();
    double stop = System.currentTimeMillis();
    System.out.println("computeSumStreamSequential: " + (stop - start) / 1000 + "s");

    return sum;
  }

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
    double start = System.currentTimeMillis();
    double sum = DoubleStream.generate(generator).limit((long) n).parallel().sum();
    double stop = System.currentTimeMillis();
    System.out.println("computeSumStreamParallel: " + (stop - start) / 1000 + "s");

    return sum;
  }
}
