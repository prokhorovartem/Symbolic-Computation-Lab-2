package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

public class PowerWorker extends AbstractWorker {
    public PowerWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public PowerWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation() && secondArgument.isOperation()) {
            throw new UnsupportedOperationException("Pow of functions");
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().pow(secondArg.getValue().intValue()));
            }
        } else {
            return new OperationImpl(OperationType.POW, firstArgument, secondArgument);
        }
        throw new RuntimeException("Something went terribly wrong");
    }
}
