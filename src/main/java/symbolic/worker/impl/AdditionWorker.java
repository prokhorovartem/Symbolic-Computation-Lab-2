package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

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
            throw new UnsupportedOperationException("Sum of functions");
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().add(secondArg.getValue()));
            } else if (Objects.equals(firstArg.getVariable(), secondArg.getVariable())) {
                return new MultiplicationWorker(
                        new Variable(2),
                        new Variable(firstArg.getVariable())
                ).work();
            }
        } else {
            return new OperationImpl(OperationType.ADDITION, firstArgument, secondArgument);
        }
        throw new RuntimeException("Something went terribly wrong");
    }
}
