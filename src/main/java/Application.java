import input.InputModel;
import input.ReaderFromFile;
import integrator.IntegrationModel;

public class Application {
    public static void main(String[] args) {
        System.out.println(IntegrationModel.integrateExpression(InputModel.createModel(ReaderFromFile.readExpression())));
    }
}
