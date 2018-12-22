import input.ReaderFromFile;

public class Application {
    public static void main(String[] args) {
        ReaderFromFile readerFromFile = new ReaderFromFile();
        System.out.println(readerFromFile.readExpression());
    }
}
