package model;

import symbolic.model.Expression;
import symbolic.model.OperationType;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    public Expression convert(List<Expression> inputData) {
        List<Expression> expressions = reverseNotation(inputData);
        return null;
    }

    private List<Expression> reverseNotation(List<Expression> inputData) {
        Expression currentExpression, tempExpression, unaryExpression = null;
        List<Expression> expressionStack = new ArrayList<>(), expressionsOut = new ArrayList<>();
        for (Expression data : inputData) {
            currentExpression = data;
            if (isUnaryOperation(currentExpression)) {
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
            } else if (currentExpression == OperationType.OPENING_BRACKET) {
                expressionStack.add(currentExpression);
                expressionsOut.add(currentExpression);
            } else if (currentExpression == OperationType.CLOSING_BRACKET) {
                tempExpression = expressionStack.get(expressionStack.size() - 1);
                while (tempExpression != OperationType.OPENING_BRACKET) {
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

    private boolean isUnaryOperation(Expression currentExpression) {
        return currentExpression == OperationType.SIN
                || currentExpression == OperationType.COS
                || currentExpression == OperationType.TAN
                || currentExpression == OperationType.CTG;
    }

    private byte binaryOperationPriority(Expression currentExpression) {
        if (currentExpression == OperationType.POW)
            return 3;
        if (currentExpression == OperationType.MULTIPLICATION
                || currentExpression == OperationType.DIVISION)
            return 2;
        return 1;
    }

    private boolean isBinaryOperation(Expression currentExpression) {
        return currentExpression instanceof OperationType
                && currentExpression != OperationType.INT
                && currentExpression != OperationType.OPENING_BRACKET
                && currentExpression != OperationType.CLOSING_BRACKET
                && !isUnaryOperation(currentExpression);
    }
}
