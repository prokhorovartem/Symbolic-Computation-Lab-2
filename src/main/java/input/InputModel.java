package input;

import input.operand.*;
import input.operand.Number;

import java.util.ArrayList;
import java.util.List;

public class InputModel {
    private static List<Operand> operands = new ArrayList<>();

    public static List<Operand> createModel(String expression) {
        char[] arrayOfOperands = expression.toCharArray();
        for (char arrayOfOperand : arrayOfOperands) {
            if (Character.isDigit(arrayOfOperand)) {
                operands.add(new Number((int) arrayOfOperand));
                continue;
            }
            switch (arrayOfOperand) {
                case '(':
                    operands.add(Bracket.OPEN);
                    break;
                case ')':
                    operands.add(Bracket.CLOSE);
                    break;
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
