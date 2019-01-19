import input.Converter;
import input.model.InputExpression;
import input.InputModel;
import input.Resource;
import symbolic.model.Expression;
import symbolic.visitor.impl.Resolver;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Resource inputResource = new Resource("input.tex");
        InputModel inputModel;

        try {
            inputModel = new InputModel(inputResource);
            List<InputExpression> inputData = inputModel.parse();
            Converter converter = new Converter();
            Expression expression = converter.convert(inputData);
            Expression resolvedExpression = Resolver.resolveExpression(expression);
            System.out.println(resolvedExpression);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
