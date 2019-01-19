package input.visitor.impl;

import input.model.*;
import input.model.impl.InputOperation;
import input.model.impl.InputVariable;
import input.visitor.ArgumentConvertVisitor;
import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.util.ArrayList;
import java.util.List;

public class ArgumentConvertVisitorImpl implements ArgumentConvertVisitor {

    public static List<Expression> symbolicExpression = new ArrayList<>();
    private List<InputExpression> reversedExpression;
    private int i;

    public ArgumentConvertVisitorImpl(List<InputExpression> reversedExpression, int i) {
        this.reversedExpression = reversedExpression;
        this.i = i;
    }

    @Override
    public void visit(BinaryOperation binaryOperation) {
        OperationType operationType = OperationType.valueOf(binaryOperation.toString());
        Operation operation = OperationImpl.builder()
                .firstArgument(symbolicExpression.get(i - 2))
                .operationType(operationType)
                .secondArgument(symbolicExpression.get(i - 1))
                .build();
        InputOperation inputOperation = InputOperation.builder()
                .firstArgument(reversedExpression.get(i - 2))
                .operationType(reversedExpression.get(i))
                .secondArgument(reversedExpression.get(i - 1))
                .build();
        //вставляем новую операцию вместо трёх старых
        reversedExpression.remove(i - 2);
        reversedExpression.remove(i - 2);
        reversedExpression.set(i - 2, inputOperation);
        symbolicExpression.remove(i - 2);
        symbolicExpression.remove(i - 2);
        symbolicExpression.add(operation);
    }

    @Override
    public void visit(UnaryOperation unaryOperation) {
        OperationType operationType = OperationType.valueOf(unaryOperation.toString());
        Operation operation = OperationImpl.builder()
                .firstArgument(symbolicExpression.get(i - 1))
                .operationType(operationType)
                .build();
        InputOperation inputOperation = InputOperation.builder()
                .firstArgument(reversedExpression.get(i - 1))
                .operationType(reversedExpression.get(i))
                .build();
        //вставляем новую операцию вместо двух старых
        reversedExpression.remove(i - 1);
        reversedExpression.set(i - 1, inputOperation);
        symbolicExpression.remove(i - 1);
        symbolicExpression.add(operation);
    }

    @Override
    public void visit(Bracket bracket) {
        reversedExpression.remove(i);
    }

    @Override
    public void visit(InputVariable inputVariable) {
        Variable variable;
        if (inputVariable.getValue() != null)
            variable = new Variable(inputVariable.getValue());
        else variable = new Variable(inputVariable.getVariable());
        symbolicExpression.add(variable);
    }
}
