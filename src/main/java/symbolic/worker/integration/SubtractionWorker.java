package symbolic.worker.integration;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.util.Objects;

public class SubtractionWorker extends AbstractWorker {
    public SubtractionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public SubtractionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().subtract(secondArg.getValue()));
            } else if (Objects.equals(firstArg.getName(), secondArg.getName())) {
                return new Variable(0);
            } else {
                firstArgument = new VariableWorker(firstArg).work();
                secondArgument = new VariableWorker(secondArg).work();
                return new OperationImpl(OperationType.SUBTRACTION, firstArgument, secondArgument);
            }
        } else {
            return new OperationImpl(OperationType.SUBTRACTION, firstArgument, secondArgument);
        }
    }
}