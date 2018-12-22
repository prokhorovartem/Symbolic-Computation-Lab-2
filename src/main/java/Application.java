import input.InputModel;
import input.ReaderFromFile;

public class Application {
    public static void main(String[] args) {
        System.out.println(InputModel.createModel(ReaderFromFile.readExpression()));
    }
}
