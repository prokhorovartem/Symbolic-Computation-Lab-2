package model;

import object.*;
import object.Number;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputModel {
    private Resource resource;

    public InputModel(Resource resource) {
        this.resource = resource;
    }

    public void parse() {
        String integral = null;
        int startExpression = 11;
        String endExpression = "\\,dx\\]";
        try (Scanner sc = new Scanner(resource.getFile())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("\\[\\int")) {
                    integral = line.substring(startExpression, line.indexOf(endExpression)).replaceAll(" ", "");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файла не существует!");
        }
        assert integral != null;
        Expression expression = getExpressionFromString(reversePolandNotation(integral));
    }

    private static String reversePolandNotation(String expression) {
        StringBuilder sbStack = new StringBuilder(), sbOut = new StringBuilder();
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

        return sbOut.toString().replaceAll(" ", "");
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
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }

    private Expression getExpressionFromString(String reversedNotation) {
        StringBuilder sb = new StringBuilder(reversedNotation);
        boolean firstTime = true;
        Expression expression = null;
        for (int i = 0; i < sb.length(); i++) {
            if (!Character.isDigit(sb.charAt(i))) {
                if (firstTime)
                    expression = new Expression(getOperandType(sb.charAt(i - 2)), null, null);
                Operation operation = null;
                switch (sb.charAt(i)) {
                    case '+':
                        operation = Operation.ADD;
                        break;
                    case '-':
                        operation = Operation.SUB;
                        break;
                    case '*':
                        operation = Operation.MUL;
                        break;
                    case '/':
                        operation = Operation.DIV;
                        break;
                    case '^':
                        operation = Operation.POW;
                        break;
                }
                expression = new Expression(expression,
                        getOperandType(sb.charAt(i - 1)), operation);
                if (firstTime)
                    sb.delete(i - 2, i + 1);
                else sb.delete(i - 1, i + 1);
                firstTime = false;
                i = 0;
            }
        }
        return expression;
    }

    private Operand getOperandType(char charOperand) {
        Operand operand = null;
        if (Character.isDigit(charOperand)) {
            operand = new Number(Double.parseDouble(String.valueOf(charOperand)));
        } else if (charOperand == 'x') operand = Variable.X;
        return operand;
    }
}
