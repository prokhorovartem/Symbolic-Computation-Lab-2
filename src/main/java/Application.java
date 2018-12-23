import input.InputModel;
import input.ReaderFromFile;
import integrator.AriphmeticModel;

public class Application {
    public static void main(String[] args) {
        System.out.println(AriphmeticModel.reduceModel(InputModel.createModel(ReaderFromFile.readExpression())));
    }
}
