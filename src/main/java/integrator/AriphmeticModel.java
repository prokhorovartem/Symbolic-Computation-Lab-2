package integrator;

import input.operand.BinaryOperator;
import input.operand.Number;
import input.operand.Operand;

import java.util.List;
import java.util.Objects;

public class AriphmeticModel {
    public static List<Operand> reduceModel(List<Operand> operands) {
        for (int i = 0; i < operands.size(); i++) {
            if (!(operands.get(i) instanceof Number)) {
                if (operands.get(i - 2) instanceof Number && operands.get(i - 1) instanceof Number) {
                    double acc = 0;
                    if (Objects.equals(operands.get(i), BinaryOperator.PLUS)) {
                        acc = ((Number) operands.get(i - 2)).getNumber() + ((Number) operands.get(i - 1)).getNumber();
                    }
                    if (Objects.equals(operands.get(i), BinaryOperator.MINUS)) {
                        acc = ((Number) operands.get(i - 2)).getNumber() - ((Number) operands.get(i - 1)).getNumber();
                    }
                    if (Objects.equals(operands.get(i), BinaryOperator.MULTIPLY)) {
                        acc = ((Number) operands.get(i - 2)).getNumber() * ((Number) operands.get(i - 1)).getNumber();
                    }
                    if (Objects.equals(operands.get(i), BinaryOperator.DIVIDE)) {
                        acc = ((Number) operands.get(i - 2)).getNumber() / ((Number) operands.get(i - 1)).getNumber();
                    }
                    if (Objects.equals(operands.get(i), BinaryOperator.POWER)) {
                        acc = Math.pow(((Number) operands.get(i - 2)).getNumber(), ((Number) operands.get(i - 1)).getNumber());
                    }
                    operands.add(i - 2, new Number(acc));
                    operands.remove(i - 1);
                    operands.remove(i - 1);
                    operands.remove(i - 1);
                    i--;
                    reduceModel(operands);
                }
            }
        }
        return operands;
    }
}
