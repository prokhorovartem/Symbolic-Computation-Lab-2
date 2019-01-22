package symbolic.worker.impl;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.math.BigDecimal;

public class ExpWorker extends AbstractWorker {
    public ExpWorker(Expression firstArgument) {
        super(firstArgument);
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation()) {
            throw new UnsupportedOperationException("Exp of functions");
        } else if (firstArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            if (firstArg.isValueSet()) {
                Apfloat argument = new Apfloat(firstArg.getValue());
                return new Variable(new BigDecimal(ApfloatMath.exp(argument).doubleValue()));
            }
        } else {
            return new OperationImpl(OperationType.SIN, firstArgument);
        }
        throw new RuntimeException("Something went terribly wrong");
    }
}
