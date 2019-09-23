import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Ex4_5 {

    public static void main(String[] args) {
        float n = 1_000_000_001;

        double start = System.currentTimeMillis();

        double stop = System.currentTimeMillis();
        System.out.println(stop - start);

        Double sum = IntStream.range(1, (int)n).mapToDouble((i) -> 1.0 / i / i).sum();
        System.out.printf("Sum = %20.16f%n", sum - Math.PI*Math.PI/6);
        System.out.printf("Sum = %20.16f%n", - (1 / n));

    }

}
