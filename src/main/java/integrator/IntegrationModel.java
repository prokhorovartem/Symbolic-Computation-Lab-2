package integrator;

import input.operand.BinaryOperator;
import input.operand.Number;
import input.operand.Operand;
import input.operand.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IntegrationModel {
    public static List<Operand> integrateExpression(List<Operand> operands) {
        List<Operand> integratedOperands = new ArrayList<>();
        for (Operand operand : operands) {
            if (operand instanceof Number) {
                integratedOperands.add(operand);
                integratedOperands.add(BinaryOperator.MULTIPLY);
                integratedOperands.add(Variable.X);
            }
            if (operand instanceof BinaryOperator){
                if (Objects.equals(operand, BinaryOperator.PLUS))
                    integratedOperands.add(BinaryOperator.PLUS);
                if (Objects.equals(operand, BinaryOperator.MINUS))
                    integratedOperands.add(BinaryOperator.MINUS);
                if (Objects.equals(operand, BinaryOperator.MULTIPLY))
                    integratedOperands.add(BinaryOperator.MULTIPLY);
                if (Objects.equals(operand, BinaryOperator.DIVIDE))
                    integratedOperands.add(BinaryOperator.DIVIDE);
            }
        }
        return integratedOperands;
    }
}
