import io.Converter;
import io.InputModel;
import io.OutputModel;
import io.Resource;
import io.model.InputExpression;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import symbolic.model.Expression;
import symbolic.model.impl.Variable;
import symbolic.visitor.Resolver;
import symbolic.visitor.calculation.CalculationParamHolder;
import symbolic.visitor.calculation.CalculationResolver;
import symbolic.visitor.integration.IntegrationParamHolder;
import symbolic.visitor.integration.IntegrationResolver;

import java.math.BigDecimal;
import java.util.List;

public class Application extends javafx.application.Application {

    private static Expression resolvedExpression;

    public static void main(String[] args) {
        Resource inputResource = new Resource(args[0]);
        Resource outputResource = new Resource(args[1]);

        InputModel inputModel = new InputModel(inputResource);
        List<InputExpression> inputData = inputModel.parse();

        Converter converter = new Converter();
        Expression expression = converter.convert(inputData);

        Resolver resolver = new IntegrationResolver();
        resolvedExpression = resolver.resolveExpression(expression);

        OutputModel outputModel = new OutputModel(outputResource);
        outputModel.printResult(resolvedExpression);

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {

        TextField leftBorderTextField = new TextField("-1");
        TextField rightBorderTextField = new TextField("1");
        Button btn = new Button("Change borders");

        final NumberAxis xAxis1 = new NumberAxis();
        final NumberAxis yAxis1 = new NumberAxis();
        final LineChart<Number, Number> lineChart1 = new LineChart<>(xAxis1, yAxis1);
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("F(x)");
        lineChart1.getData().add(series1);
        btn.setOnAction(event -> {
            try {
                double leftBorder = Double.parseDouble(leftBorderTextField.getText());

                double rightBorder = Double.parseDouble(rightBorderTextField.getText());

                if (rightBorder < leftBorder)
                    throw new NumberFormatException("left border should be less than right border");
                initData(series1, leftBorder, rightBorder);
            } catch (NumberFormatException ex) {
                System.out.println(ex.toString());
            }
        });

        VBox vBox1 = new VBox();
        vBox1.getChildren().addAll(lineChart1);

        HBox chartBox = new HBox();
        chartBox.getChildren().addAll(vBox1);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(leftBorderTextField, rightBorderTextField, btn, chartBox);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 505, 550);

        primaryStage.setTitle("Symbolic Computation, Lab4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initData(XYChart.Series series1, double leftBorder, double rightBorder) {
        series1.getData().remove(0, series1.getData().size());

        CalculationParamHolder.getInstance().setName(IntegrationParamHolder.getInstance().getName());
        Resolver resolver = new CalculationResolver();
        for (double i = leftBorder; i < rightBorder; i += 0.05) {
            try {
                CalculationParamHolder.getInstance().setValue(BigDecimal.valueOf(i));
                Variable variable = (Variable) resolver.resolveExpression(resolvedExpression);
                series1.getData().add(new XYChart.Data(i, variable.getValue().doubleValue()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
