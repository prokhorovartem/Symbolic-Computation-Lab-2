import io.Converter;
import io.InputModel;
import io.OutputModel;
import io.Resource;
import io.model.InputExpression;
import symbolic.model.Expression;
import symbolic.visitor.impl.IntegrationParamHolder;
import symbolic.visitor.impl.Resolver;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Resource inputResource = new Resource(args[0]);
        Resource outputResource = new Resource(args[1]);

        try {
            InputModel inputModel = new InputModel(inputResource);
            List<InputExpression> inputData = inputModel.parse();

            Converter converter = new Converter();
            Expression expression = converter.convert(inputData);

            Resolver resolver = new Resolver();
            IntegrationParamHolder.getInstance().setName("x");
            Expression resolvedExpression = resolver.resolveExpression(expression);

            OutputModel outputModel = new OutputModel(outputResource);
            outputModel.printResult(resolvedExpression);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
