import model.InputModel;
import object.Resource;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;
import symbolic.visitor.Visitor;
import symbolic.visitor.impl.VisitorImpl;

import java.math.BigDecimal;

public class Application {
    public static void main(String[] args) {
        Resource inputResource = new Resource("input.tex");

        InputModel inputModel;

        Operation operation = OperationImpl.builder()
                .firstArgument(new Variable(new BigDecimal(1.0)))
                .operationType(OperationType.ADDITION)
                .secondArgument(new Variable(new BigDecimal(2.0)))
                .build();
        Visitor visitor = new VisitorImpl();
        operation.accept(visitor);
        System.out.println(operation.getOperationType());

        try {
            inputModel = new InputModel(inputResource);
            inputModel.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
