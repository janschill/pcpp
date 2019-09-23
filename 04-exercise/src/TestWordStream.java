// Week 3
// sestoft@itu.dk * 2015-09-09

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestWordStream {
  public static void main(String[] args) {
    String filename = "usr\\share\\dict\\words";
    Stream<String> words = readWords(filename);
    //System.out.println(words.count());
    //words.limit(100).forEach(System.out::println);

    //words.filter((e) -> e.length() >= 22).forEach(System.out::println);

    Optional<String> s = words.filter((e) -> e.length() >= 22).findAny();

    //double start = System.currentTimeMillis();
    //words.filter((s) -> s.equals(new StringBuilder(s).reverse().toString())).forEach(System.out::println);
    //words.parallel().filter((s) -> s.equals(new StringBuilder(s).reverse().toString())).forEach(System.out::println);
    //double stop = System.currentTimeMillis();
    //System.out.println((stop - start) / 1000);

    //System.out.println(words.mapToInt(String::length).summaryStatistics());

    //System.out.println(words.collect(Collectors.groupingBy(String::length)).get(4));

    //System.out.println(letters("Persistent"));

    //words.map(TestWordStream::letters).limit(100).forEach(System.out::println);

    //System.out.println(words.map(TestWordStream::letters).map(m -> m.get('e') == null ? 0 : m.get('e')).reduce(0,(integer, integer2) -> integer + integer2).toString());

    double start = System.currentTimeMillis();
    //System.out.println(words.collect(Collectors.groupingBy(TestWordStream::letters)));
    System.out.println(words.parallel().collect(Collectors.groupingBy(TestWordStream::letters)));
    double stop = System.currentTimeMillis();
    System.out.println((stop - start) / 1000);
    //System.out.println(words.filter(w -> w.length() < 5).collect(Collectors.groupingBy(TestWordStream::letters)));








  }



  public static Stream<String> readWords(String filename) {
    try {
      Stream<String> words = Files.lines(Paths.get(filename));
      return words;
    } catch (IOException exn) {
      exn.printStackTrace();
      return Stream.<String>empty();
    }
  }




  public static boolean isPalindrome(String s) {
    // TO DO: Implement properly
    return false; 
  }

  public static Map<Character,Integer> letters(String s) {
    Map<Character,Integer> res = new TreeMap<>();
    s = s.toLowerCase();
    for(int i = 0; i < s.length(); i++) {
       res.compute(s.charAt(i), (k, v) -> v == null ? 1 : v + 1);
    }
    return res;
  }
}
