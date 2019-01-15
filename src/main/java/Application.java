import model.Converter;
import model.InputModel;
import model.Resource;
import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;
import symbolic.visitor.Visitor;
import symbolic.visitor.impl.VisitorImpl;

import java.math.BigDecimal;
import java.util.List;

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
            List inputData = inputModel.parse();
            Converter converter = new Converter();
            Expression expression = converter.convert(inputData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
