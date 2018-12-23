import input.operand.Operand;
import integrator.IntegrationModel;
import output.WriterInFile;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        List<Operand> operands = IntegrationModel.integrateExpression();
        WriterInFile.writeResult(operands);
    }
}
