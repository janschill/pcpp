// For week 5
// sestoft@itu.dk * 2014-09-19

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class TestDownload {

  private static final ExecutorService executor = Executors.newCachedThreadPool();
  private static final String[] urls = { "https://www.itu.dk", "https://www.di.ku.dk", "https://www.miele.de",
      "https://www.microsoft.com", "https://www.dr.dk", "https://www.vg.no", "https://www.tv2.dk",
      "https://www.google.com", "https://www.ing.dk", "https://www.dtu.dk", "https://www.eb.dk",
      "https://www.nytimes.com", "https://www.theguardian.com", "https://www.lemonde.fr", "https://www.welt.de",
      "https://www.dn.se", "https://www.heise.de", "https://www.wsj.com", "https://www.bbc.co.uk", "https://www.dsb.dk",
      "https://www.bmw.com", "https://www.cia.gov" };

  public static void main(String[] args) throws IOException {
    // String url = "https://www.wikipedia.org/";
    // String page = getPage(url, 10);
    // System.out.printf("%-30s%n%s%n", url, page);
    // Map<String, String> content = getPages(urls, 200);

    Map<String, String> contentParallel = getPagesParallel(200);
    for (Map.Entry<String, String> entry : contentParallel.entrySet()) {
    System.out.println("URL: " + entry.getKey() + ", Content-Length: " +
    entry.getValue().length());
    }



  }

  public static String getPage(String url, int maxLines) throws IOException {
    // This will close the streams after use (JLS 8 para 14.20.3):
    try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < maxLines; i++) {
        String inputLine = in.readLine();
        if (inputLine == null)
          break;
        else
          sb.append(inputLine).append("\n");
      }
      return sb.toString();
    }
  }

  public static Map<String, String> getPages(String[] urls, int maxLines) throws IOException {
    final Map<String, String> content = new HashMap<>();
    double runningTime = 0.0;
    Timer t = new Timer();
    for (String url : urls) {
      content.put(url, getPage(url, maxLines));
    }
    runningTime = t.check();
    System.out.println(runningTime);
    return content;
  }

  public static Map<String, String> getPagesParallel(int maxLines) throws IOException {
    List<Future<?>> futures = new ArrayList<Future<?>>();
    final int maxLinesToFetch = maxLines;
    final Map<String, String> content = new HashMap<>();
    for (int i = 0; i < urls.length; i++) {
      final int currentUrlIndex = i;
      futures.add(executor.submit(() -> {
        try {
          content.put(urls[currentUrlIndex], getPage(urls[currentUrlIndex], maxLinesToFetch));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }));
    }
    return content;
  }
}
