package integrator;

import input.InputModel;
import input.operand.BinaryOperator;
import input.operand.Number;
import input.operand.Operand;
import input.operand.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntegrationModel {
    private static List<Operand> integratedOperands = new ArrayList<>();

    public static List<Operand> integrateExpression() {
        List<Operand> operands = AriphmeticModel.reduceModel(InputModel.createModel());
        for (int i = 2; i < operands.size(); i++) {
            if (Objects.equals(operands.get(i), BinaryOperator.PLUS) || Objects.equals(operands.get(i), BinaryOperator.MINUS)) {
                if (operands.get(i - 2) instanceof Number) {
                    integrateNumber(operands.get(i - 2));
                }
                if (operands.get(i - 2) instanceof Variable) {
                    integrateVariable(operands.get(i - 2), 1);
                }
                if (Objects.equals(operands.get(i), BinaryOperator.PLUS)){
                    integratedOperands.add(BinaryOperator.PLUS);
                } else integratedOperands.add(BinaryOperator.MINUS);
                if (operands.get(i - 1) instanceof Number) {
                    integrateNumber(operands.get(i - 1));
                }
                if (operands.get(i - 1) instanceof Variable) {
                    integrateNumber(operands.get(i - 1));
                }
            }
        }
        return integratedOperands;
    }

    private static void integrateVariable(Operand operand, double power) {
        for (int i = 0; i < power; i++){
            integratedOperands.add(operand);
            integratedOperands.add(BinaryOperator.MULTIPLY);
        }
        integratedOperands.add(operand);
        integratedOperands.add(BinaryOperator.DIVIDE);
        integratedOperands.add(new Number(power + 1));
    }

    private static void integrateNumber(Operand operand) {
        integratedOperands.add(operand);
        integratedOperands.add(BinaryOperator.MULTIPLY);
        integratedOperands.add(Variable.X);
    }
}
