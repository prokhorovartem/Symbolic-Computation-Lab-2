package symbolic.worker.integration;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.math.BigDecimal;

public class SinWorker extends AbstractWorker {
    public SinWorker(Expression firstArgument) {
        super(firstArgument);
    }

    @Override
    public Expression work() {
        if (firstArgument.isOperation()) {
            throw new UnsupportedOperationException("Sin of functions");
        } else if (firstArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            if (firstArg.isValueSet()) {
                Apfloat argument = new Apfloat(firstArg.getValue());
                return new Variable(new BigDecimal(ApfloatMath.sin(argument).doubleValue()));
            } else {
                return new OperationImpl(
                        OperationType.MULTIPLICATION,
                        new Variable(-1),
                        new OperationImpl(
                                OperationType.COS,
                                new Variable(firstArg.getName())
                        )
                );
            }
        } else {
            return new OperationImpl(OperationType.SIN, firstArgument);
        }
    }
}
