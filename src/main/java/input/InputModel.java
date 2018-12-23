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
        String expressionInPolandNotation = InputModel.reversePolandNotation(expression);
        char[] arrayOfOperands = expressionInPolandNotation.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arrayOfOperands.length; i++) {
            char arrayOfOperand = arrayOfOperands[i];
            if (Character.isDigit(arrayOfOperand)) {
                builder.append(arrayOfOperand);
                if (i != arrayOfOperands.length - 1) {
                    if (!Character.isDigit(arrayOfOperands[i + 1])) {
                        operands.add(new Number(Double.parseDouble(builder.toString())));
                        builder = new StringBuilder();
                    }
                } else {
                    operands.add(new Number(Double.parseDouble(builder.toString())));
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
                case '^':
                    operands.add(BinaryOperator.POWER);
                    break;
                case 'x':
                    operands.add(Variable.X);
                    break;
            }
        }
        System.out.println(operands);
        return operands;
    }

    public static String reversePolandNotation(String expression) {
        StringBuilder sbStack = new StringBuilder(""), sbOut = new StringBuilder("");
        char cIn, cTmp;

        for (int i = 0; i < expression.length(); i++) {
            cIn = expression.charAt(i);
            if (isOperation(cIn)) {
                while (sbStack.length() > 0) {
                    cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
                    if (isOperation(cTmp) && (operationPriority(cIn) <= operationPriority(cTmp))) {
                        sbOut.append(" ").append(cTmp).append(" ");
                        sbStack.setLength(sbStack.length() - 1);
                    } else {
                        sbOut.append(" ");
                        break;
                    }
                }
                sbOut.append(" ");
                sbStack.append(cIn);
            } else if ('(' == cIn) {
                sbStack.append(cIn);
            } else if (')' == cIn) {
                cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
                while ('(' != cTmp) {
                    if (sbStack.length() < 1) {
                        try {
                            throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    sbOut.append(" ").append(cTmp);
                    sbStack.setLength(sbStack.length() - 1);
                    cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
                }
                sbStack.setLength(sbStack.length() - 1);
            } else {
                // Если символ не оператор - добавляем в выходную последовательность
                sbOut.append(cIn);
            }
        }

        // Если в стеке остались операторы, добавляем их в входную строку
        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length() - 1));
            sbStack.setLength(sbStack.length() - 1);
        }

        return sbOut.toString();
    }

    private static byte operationPriority(char operation) {
        switch (operation) {
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
        }
        return 1; // Тут остается + и -
    }

    private static boolean isOperation(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }
}
