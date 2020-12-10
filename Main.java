import java.io.File;
import javax.xml.parsers.*;
import java.util.*;

public class Main{
  public static void main(String[] a) throws Exception {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter file name: ");
    String fileName = scanner.nextLine();
    File inputFile = new File(fileName);
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    MyHandler handler = new MyHandler();
    parser.parse(inputFile, handler);
  }
}
