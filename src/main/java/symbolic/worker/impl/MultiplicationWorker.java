package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.util.Objects;

public class MultiplicationWorker extends AbstractWorker {
    public MultiplicationWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public MultiplicationWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation() && secondArgument.isOperation()) {
            throw new UnsupportedOperationException("Mul of functions");
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().multiply(secondArg.getValue()));
            } else if (Objects.equals(firstArg.getVariable(), secondArg.getVariable())) {
                return new PowerWorker(
                        new Variable(firstArg.getVariable()),
                        new Variable(2)
                ).work();
            } else {
                return new OperationImpl(OperationType.MULTIPLICATION, firstArgument, secondArgument);
            }
        } else {
            return new OperationImpl(OperationType.MULTIPLICATION, firstArgument, secondArgument);
        }
    }
}