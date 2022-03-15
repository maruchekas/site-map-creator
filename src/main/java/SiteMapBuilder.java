import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SiteMapBuilder extends RecursiveTask<String> {

  private String url;
  private static String mainUrl;
  private static Map<String, Integer> allLinks = new ConcurrentHashMap<>();
  private static final String FILE_LINK = "http[s]?:\\/(?:\\/[^\\/]+){1,}(?:\\/[А-Яа-яёЁ\\w- ]+\\.[a-z]{3,5}" +
          "(?![\\/]|[\\wА-Яа-яёЁ]))";
  private int level;

  public SiteMapBuilder(String url) {
    this.url = url;
  }

  public SiteMapBuilder(String url, String mainUrl) {
    this.url = url;
    this.mainUrl = mainUrl;
  }

  @Override
  protected String compute() {
    StringBuffer sb = new StringBuffer(url + "\n");
    Set<SiteMapBuilder> tasks = new HashSet<>();

    getChildren(tasks);

    for (SiteMapBuilder link : tasks) {
      sb.append(slashMatcher(link.toString())).append(link.join());
    }
    level = 0;
    return sb.toString();
  }

  private void getChildren(Set<SiteMapBuilder> subTask) {
    Document document;
    Elements elements;
    try {
      Thread.sleep(66);
      document = Jsoup.connect(url).get();
      elements = document.select("a");
      for (Element el : elements) {
        String link = el.attr("abs:href");
        if (checkLink(link)) {
          SiteMapBuilder siteMapBuilder = new SiteMapBuilder(link);
          siteMapBuilder.fork();
          level++;
          subTask.add(siteMapBuilder);
          allLinks.put(link, level);
        }
      }
    } catch (InterruptedException | IOException ignored) {
    }
  }

  private boolean checkLink(String link){
    return !link.isEmpty() && link.startsWith(mainUrl) && !allLinks.containsKey(link)
            && !link.contains("#") && !Pattern.matches(FILE_LINK, link);
  }

  private StringBuilder slashMatcher(String attr){
    Pattern pattern = Pattern.compile("\\b\\/.");
    Matcher matcher = pattern.matcher(attr);
    StringBuilder tabs = new StringBuilder();
    while (matcher.find()) {
      tabs.append("\t");
    }
    return tabs;
  }

  @Override
  public String toString() {
    return url;
  }
}