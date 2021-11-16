import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {

  public static void main(String[] args) {

    System.out.println("Введите адрес сайта или вставьте ссылку на ресурс. "
        + "Формат ввода: https://example.ru");
    Scanner scanner = new Scanner(System.in);
    String url = scanner.nextLine();
    String filePath = "siteMap.txt";
      System.out.println("Scanning...");

    SiteMapBuilder siteMapBuilder = new SiteMapBuilder(url, url);
    String siteMap = new ForkJoinPool().invoke(siteMapBuilder);
    new MapWriter(siteMap).writeMapToFile(filePath);

  }
}