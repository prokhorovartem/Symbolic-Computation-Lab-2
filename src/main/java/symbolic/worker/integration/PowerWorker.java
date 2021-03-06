package symbolic.worker.integration;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;
import symbolic.visitor.integration.IntegrationParamHolder;

public class PowerWorker extends AbstractWorker {
    public PowerWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public PowerWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        final String name = IntegrationParamHolder.getInstance().getName();

        if (firstArgument.isOperation() && secondArgument.isOperation()) {
            throw new UnsupportedOperationException("Pow of functions");
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                return new Variable(firstArg.getValue().pow(secondArg.getValue().intValue()));
            } else if (name.equals(firstArg.getName())) {
                return new OperationImpl(
                        OperationType.DIVISION,
                        new OperationImpl(
                                OperationType.POW,
                                firstArg,
                                new AdditionWorker(secondArg, new Variable(1)).work()
                        ),
                        new AdditionWorker(secondArg, new Variable(1)).work()
                );
            } else if (name.equals(secondArg.getName())) {
//                return new OperationImpl(
//                        OperationType.DIVISION,
//                        new OperationImpl(
//                                OperationType.POW,
//                                firstArg,
//                                secondArg
//                        ),
//                        new OperationImpl(OperationType.LN, firstArg)
//                );
                //todo
                throw new UnsupportedOperationException("a^" + name + " unsupported");
            }
        } else {
            return new OperationImpl(OperationType.POW, firstArgument, secondArgument);
        }
        throw new RuntimeException("Something went terribly wrong");
    }
}
