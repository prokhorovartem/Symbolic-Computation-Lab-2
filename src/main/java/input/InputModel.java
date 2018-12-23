package input;

import input.operand.*;
import input.operand.Number;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        return prioritizeOperands(operands);
    }

    private static List<Operand> prioritizedOperands = new ArrayList<>();
    private static int counterForNewList = 0;

    private static List<Operand> prioritizeOperands(List<Operand> operands) {
        for (int i = 0; i < operands.size(); i++) {
            if (operands.get(i) instanceof Bracket) {
                if (Objects.equals(operands.get(i), Bracket.OPEN)) {
                    for (int j = i; j < operands.size(); j++) {
                        if (Objects.equals(operands.get(j), Bracket.CLOSE)) {
                            List<Operand> sublist = operands.subList(i, j + 1);
                            prioritizedOperands.addAll(sublist);
                            operands.subList(i, j + 1).clear();
                            if (prioritizedOperands.size() > counterForNewList){
                                counterForNewList = prioritizedOperands.size();
                                prioritizeOperands(prioritizedOperands);
                            }
                        }
                    }
                }
            }
        }
        return prioritizedOperands;
    }
}
