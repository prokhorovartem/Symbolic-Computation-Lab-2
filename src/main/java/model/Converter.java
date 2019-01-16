package model;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    public Expression convert(List<Expression> inputData) {
        List<Expression> expressions = reverseNotation(inputData);
        return null;
    }

    private List<Expression> reverseNotation(List<Expression> inputData) {
        Expression currentExpression, tempExpression;
        List<Expression> expressionStack = new ArrayList<>(), expressionsOut = new ArrayList<>();
        for (Expression data : inputData) {
            currentExpression = data;
            if (isOperation(currentExpression)) {
                while (expressionStack.size() > 0) {
                    tempExpression = expressionStack.get(expressionStack.size() - 1);
                    if (isOperation(tempExpression) && (operationPriority(currentExpression) <= operationPriority(tempExpression))) {
                        expressionsOut.add(tempExpression);
                        expressionStack.remove(tempExpression);
                    } else break;
                }
                expressionStack.add(currentExpression);
            } else if (currentExpression == OperationType.OPENING_BRACKET) {
                expressionStack.add(currentExpression);
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

    private byte operationPriority(Expression currentExpression) {
        if (currentExpression == OperationType.POW)
            return 3;
        if (currentExpression == OperationType.MULTIPLICATION || currentExpression == OperationType.DIVISION)
            return 2;
        return 1;
    }

    private boolean isOperation(Expression currentExpression) {
        return currentExpression instanceof OperationType && currentExpression != OperationType.INT;
    }
}
