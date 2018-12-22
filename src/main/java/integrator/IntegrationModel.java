package integrator;

import input.operand.BinaryOperator;
import input.operand.Number;
import input.operand.Operand;
import input.operand.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntegrationModel {
    private static List<Operand> integratedOperands = new ArrayList<>();

    public static List<Operand> integrateExpression(List<Operand> operands) {
        for (Operand operand : operands) {
            if (operand instanceof Number) {
                integrateConstants(operand);
            }
            if (operand instanceof BinaryOperator) {
                integrateBinaryOperator(operand);
            }
            if (operand instanceof Variable) {
                integrateVariable(operand);
            }
        }
        return integratedOperands;
    }

    private static void integrateConstants(Operand operand) {
        integratedOperands.add(operand);
        integratedOperands.add(BinaryOperator.MULTIPLY);
        integratedOperands.add(Variable.X);
    }

    private static void integrateBinaryOperator(Operand operand) {
        if (Objects.equals(operand, BinaryOperator.PLUS))
            integratedOperands.add(BinaryOperator.PLUS);
        if (Objects.equals(operand, BinaryOperator.MINUS))
            integratedOperands.add(BinaryOperator.MINUS);
        if (Objects.equals(operand, BinaryOperator.MULTIPLY))
            integratedOperands.add(BinaryOperator.MULTIPLY);
        if (Objects.equals(operand, BinaryOperator.DIVIDE))
            integratedOperands.add(BinaryOperator.DIVIDE);
    }

    private static void integrateVariable(Operand operand) {
        integratedOperands.add(Variable.X);
        integratedOperands.add(BinaryOperator.MULTIPLY);
        integratedOperands.add(Variable.X);
        integratedOperands.add(BinaryOperator.DIVIDE);
        integratedOperands.add(new Number(2));
    }
}
