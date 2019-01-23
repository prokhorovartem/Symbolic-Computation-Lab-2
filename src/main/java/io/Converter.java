package io;

import io.model.*;
import io.model.impl.InputVariable;
import io.visitor.impl.ArgumentConvertVisitorImpl;
import symbolic.model.Expression;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    /**
     * Конвертирует лист типов в одно выражение типа Expression
     *
     * @param inputData
     * @return
     */
    public Expression convert(List<InputExpression> inputData) {
        List<InputExpression> reversedExpression = reverseNotation(inputData);
        for (int i = 0; i < reversedExpression.size(); i++) {
            InputExpression expression = reversedExpression.get(i);
            ArgumentConvertVisitorImpl visitor = new ArgumentConvertVisitorImpl(reversedExpression, i);
            if (reversedExpression.size() != 1 && !(expression instanceof InputVariable)) {
                if (expression instanceof BinaryOperation)
                    i = i - 2;
                else
                    i = i - 1;
            }
            expression.accept(visitor);
        }
        return ArgumentConvertVisitorImpl.symbolicExpression.get(0);
    }

    /**
     * Приводит лист с типами в обратной польской нотации
     *
     * @param inputData лист с типами
     * @return
     */
    private List<InputExpression> reverseNotation(List<InputExpression> inputData) {
        InputExpression currentExpression, tempExpression, unaryExpression = null;
        List<InputExpression> expressionStack = new ArrayList<>(), expressionsOut = new ArrayList<>();
        for (InputExpression data : inputData) {
            currentExpression = data;
            if (currentExpression == UnaryOperation.INT)
                continue;
            if (currentExpression instanceof UnaryOperation) {
                unaryExpression = currentExpression;
            } else if (isBinaryOperation(currentExpression)) {
                while (expressionStack.size() > 0) {
                    tempExpression = expressionStack.get(expressionStack.size() - 1);
                    if (isBinaryOperation(tempExpression)
                            && (binaryOperationPriority(currentExpression) <= binaryOperationPriority(tempExpression))) {
                        expressionsOut.add(tempExpression);
                        expressionStack.remove(tempExpression);
                    } else break;
                }
                expressionStack.add(currentExpression);
            } else if (currentExpression == Bracket.OPENING_BRACKET) {
                expressionStack.add(currentExpression);
                expressionsOut.add(currentExpression);
            } else if (currentExpression == Bracket.CLOSING_BRACKET) {
                tempExpression = expressionStack.get(expressionStack.size() - 1);
                while (tempExpression != Bracket.OPENING_BRACKET) {
                    if (expressionStack.size() < 1) {
                        try {
                            throw new Exception("Ошибка разбора скобок");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    expressionsOut.add(tempExpression);
                    expressionStack.remove(tempExpression);
                    tempExpression = expressionStack.get(expressionStack.size() - 1);
                }
                expressionStack.remove(tempExpression);
                expressionsOut.add(currentExpression);
                if (unaryExpression != null) expressionsOut.add(unaryExpression);
                unaryExpression = null;
            } else {
                expressionsOut.add(currentExpression);
            }
        }
        while (expressionStack.size() > 0) {
            expressionsOut.add(expressionStack.get(expressionStack.size() - 1));
            expressionStack.remove(expressionStack.size() - 1);
        }
        return expressionsOut;
    }

    private byte binaryOperationPriority(InputExpression currentExpression) {
        if (currentExpression == BinaryOperation.POW)
            return 3;
        if (currentExpression == BinaryOperation.MULTIPLICATION
                || currentExpression == BinaryOperation.DIVISION)
            return 2;
        return 1;
    }

    private boolean isBinaryOperation(InputExpression currentExpression) {
        return currentExpression instanceof BinaryOperation
                && currentExpression != UnaryOperation.INT;
    }
}
