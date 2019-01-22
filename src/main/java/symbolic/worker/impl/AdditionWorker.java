package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;
import symbolic.visitor.impl.IntegrationParamHolder;

import java.util.Objects;

public class AdditionWorker extends AbstractWorker {
    public AdditionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public AdditionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation() && secondArgument.isOperation()) {
            return new OperationImpl(OperationType.ADDITION, firstArgument, secondArgument);
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().add(secondArg.getValue()));
            } else if (Objects.equals(firstArg.getName(), secondArg.getName())) {
                return new MultiplicationWorker(
                        new Variable(2),
                        new Variable(firstArg.getName())
                ).work();
            } else {
                String name = IntegrationParamHolder.getInstance().getName();
                firstArgument =  new VariableWorker(firstArg).work();
                secondArgument = new VariableWorker(secondArg).work();
                return new OperationImpl(OperationType.ADDITION,  firstArgument, secondArgument);
            }
        } else {
            return new OperationImpl(OperationType.ADDITION, firstArgument, secondArgument);
        }
    }
}
