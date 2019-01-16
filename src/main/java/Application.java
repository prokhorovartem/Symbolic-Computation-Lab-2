import input.Converter;
import input.InputExpression;
import input.InputModel;
import input.Resource;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Resource inputResource = new Resource("input.tex");
        InputModel inputModel;

        try {
            inputModel = new InputModel(inputResource);
            List<InputExpression> inputData = inputModel.parse();
            Converter converter = new Converter();
            InputExpression expression = converter.convert(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
