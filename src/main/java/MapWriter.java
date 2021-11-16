import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MapWriter {

  private String map;

  public MapWriter(String map) {
    this.map = map;
  }

  public void writeMapToFile(String filePath) {
    System.out.println("Start writing file...");

    File file = new File(filePath);
    try (PrintWriter writer = new PrintWriter(file)) {
      writer.write(map);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println("Completed!");
  }
}
