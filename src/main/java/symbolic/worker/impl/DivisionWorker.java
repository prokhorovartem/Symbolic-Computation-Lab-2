package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.math.BigDecimal;

public class DivisionWorker extends AbstractWorker {
    public DivisionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public DivisionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation() && secondArgument.isOperation()) {
            throw new UnsupportedOperationException("Div of functions");
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().divide(secondArg.getValue()));
            } else if (firstArg.getVariable().equals(secondArg.getVariable())) {
                return new Variable(
                        new BigDecimal(1)
                );
            }
        } else {
            return new OperationImpl(OperationType.DIVISION, firstArgument, secondArgument);
        }
        throw new RuntimeException("Something went terribly wrong");
    }
}