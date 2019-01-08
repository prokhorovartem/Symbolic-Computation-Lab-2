import model.InputModel;
import object.Resource;

public class Application {
    public static void main(String[] args) {
        Resource inputResource = new Resource("input.tex");

        InputModel inputModel;
        try {
            inputModel = new InputModel(inputResource);
            inputModel.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
