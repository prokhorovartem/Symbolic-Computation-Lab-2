package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

public class SubtractionWorker extends AbstractWorker {
    public SubtractionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public SubtractionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation() && secondArgument.isOperation()) {
            throw new UnsupportedOperationException("Sub of functions");
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().subtract(secondArg.getValue()));
            }
        } else {
            return new OperationImpl(OperationType.SUBTRACTION, firstArgument, secondArgument);
        }
        throw new RuntimeException("Something went terribly wrong");
    }
}