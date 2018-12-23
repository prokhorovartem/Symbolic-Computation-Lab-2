package input;

import input.operand.BinaryOperator;
import input.operand.Number;
import input.operand.Operand;
import input.operand.Variable;

import java.util.ArrayList;
import java.util.List;

public class InputModel {
    private static List<Operand> operands = new ArrayList<>();

    public static List<Operand> createModel(String expression) {
        char[] arrayOfOperands = expression.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arrayOfOperands.length; i++) {
            char arrayOfOperand = arrayOfOperands[i];
            if (Character.isDigit(arrayOfOperand)) {
                builder.append(arrayOfOperand);
                if (i != arrayOfOperands.length - 1) {
                    if (!Character.isDigit(arrayOfOperands[i + 1])) {
                        operands.add(new Number(Integer.parseInt(builder.toString())));
                        builder = new StringBuilder();
                    }
                } else {
                    operands.add(new Number(Integer.parseInt(builder.toString())));
                    builder = new StringBuilder();
                }
                continue;
            }
            switch (arrayOfOperand) {
                case '+':
                    operands.add(BinaryOperator.PLUS);
                    break;
                case '-':
                    operands.add(BinaryOperator.MINUS);
                    break;
                case '*':
                    operands.add(BinaryOperator.MULTIPLY);
                    break;
                case '/':
                    operands.add(BinaryOperator.DIVIDE);
                    break;
                case 'x':
                    operands.add(Variable.X);
            }
        }
        return operands;
    }
}
