package symbolic.worker.integration;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.math.BigDecimal;

public class LogWorker extends AbstractWorker {
    public LogWorker(Expression firstArgument) {
        super(firstArgument);
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation()) {
            throw new UnsupportedOperationException("Log of functions");
        } else if (firstArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            if (firstArg.isValueSet()) {
                Apfloat argument = new Apfloat(firstArg.getValue());
                return new Variable(new BigDecimal(ApfloatMath.log(argument).doubleValue()));
            } else {
                return new OperationImpl(
                        OperationType.MULTIPLICATION,
                        new Variable(firstArg.getName()),
                        new OperationImpl(
                                OperationType.SUBTRACTION,
                                new OperationImpl(
                                        OperationType.LOG,
                                        firstArgument
                                ),
                                new Variable(1)
                        )
                );
            }
        } else {
            return new OperationImpl(OperationType.LOG, firstArgument);
        }
    }
}
