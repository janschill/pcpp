// For week 5
// sestoft@itu.dk * 2014-09-19

import com.sun.xml.internal.bind.v2.model.runtime.RuntimeTypeInfo;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class TestDownload {
  private static final ExecutorService executor
          = Executors.newWorkStealingPool();

  private static final String[] urls =
          {"http://www.itu.dk", "http://www.di.ku.dk", "http://www.miele.de",
                  "http://www.microsoft.com", "http://www.amazon.com", "http://www.dr.dk",
                  "http://www.vg.no", "http://www.tv2.dk", "http://www.google.com",
                  "http://www.ing.dk", "http://www.dtu.dk", "http://www.eb.dk",
                  "http://www.nytimes.com", "http://www.guardian.co.uk", "http://www.lemonde.fr",
                  "http://www.welt.de", "http://www.dn.se", "http://www.heise.de", "http://www.wsj.com",
                  "http://www.bbc.co.uk", "http://www.dsb.dk", "http://www.bmw.com", "https://www.cia.gov"
          };


  public static void main(String[] args) throws IOException {
//    String url = "https://www.wikipedia.org/";
//    String page = getPage(url, 10);
//    System.out.printf("%-30s%n%s%n", url, page);
    System.out.printf(getPagesParallel(urls, 10).toString());
  }

  public static String getPage(String url, int maxLines) throws IOException {
    // This will close the streams after use (JLS 8 para 14.20.3):
    Timer t = new Timer();
    try (BufferedReader in
                 = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < maxLines; i++) {
        String inputLine = in.readLine();
        if (inputLine == null)
          break;
        else
          sb.append(inputLine).append("\n");
      }
      System.out.println(t.check());
      return Integer.toString(sb.length());
    }
  }

  public static Map<String, String> getPages(String[] urls, int maxLines) throws IOException {
    // This will close the streams after use (JLS 8 para 14.20.3):
    int numOfLinks = urls.length;
    double[] times = new double[numOfLinks];
    Map<String, String> sites = new HashMap<String, String>();
    for (int i = 0; i < urls.length; i++) {
      Timer t = new Timer();
      try (BufferedReader in
                   = new BufferedReader(new InputStreamReader(new URL(urls[i]).openStream()))) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < maxLines; j++) {
          String inputLine = in.readLine();
          if (inputLine == null)
            break;
          else
            sb.append(inputLine).append("\n");
        }
        times[i] = t.check();
        String numOfCharsWebsite = Integer.toString(sb.length());
        sites.put(urls[i], numOfCharsWebsite);
      }
    }
    for (double d : times) {
      System.out.println(d);
    }
    return sites;
  }

  public static Map<String, String> getPagesParallel(String[] urls, int maxLines) throws IOException {
    Map<String, Future<?>> m = new HashMap<>();
    for (String url : urls) {
      m.put(url, executor.submit(() -> {
        try {
          return getPage(url, maxLines);
        } catch (IOException e) {
          return null;
        }
      }));
    }
    Map<String, String> retm = new HashMap<>();
    try{
      for(String key:m.keySet())
        retm.put(key, (String)m.get(key).get());
    }catch(InterruptedException exn){
      System.out.println("Interrupted: "+exn);
    }
    catch(ExecutionException exn){
      throw new RuntimeException(exn.getCause());
    }
    return retm;
  }
}

class Timer {
  private long start, spent = 0;
  public Timer() { play(); }
  public double check() { return (System.nanoTime()-start+spent)/1e9; }
  public void pause() { spent += System.nanoTime()-start; }
  public void play() { start = System.nanoTime(); }
}

