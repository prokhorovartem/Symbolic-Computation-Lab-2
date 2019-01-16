import input.Converter;
import input.InputModel;
import input.Resource;
import symbolic.model.Expression;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Resource inputResource = new Resource("input.tex");
        InputModel inputModel;

        try {
            inputModel = new InputModel(inputResource);
            List<Expression> inputData = inputModel.parse();
            Converter converter = new Converter();
            Expression expression = converter.convert(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
