package symbolic.worker.calculation;

import org.apfloat.Apfloat;
import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.Variable;

import java.util.Objects;

import static org.apfloat.ApfloatMath.*;

public class OperationWorker extends AbstractWorker {
    public OperationWorker(OperationType operationType, Expression firstArgument, Expression secondArgument) {
        super(operationType, (Variable) firstArgument, (Variable) secondArgument);
    }

    @Override
    public Variable work() {
        if (secondArgument != null) {
            return new Variable(Objects.requireNonNull(workBinary()).doubleValue());
        } else {
            return new Variable(Objects.requireNonNull(workUnary()).doubleValue());
        }
    }

    private Apfloat workUnary() {
        Apfloat arg = new Apfloat(firstArgument.getValue());
        switch (operationType) {
            case SIN:
                return sin(arg);
            case COS:
                return cos(arg);
            case TAN:
                return tan(arg);
            case EXP:
                return exp(arg);
            case LOG:
                return log(arg);
            default:
                return null;
        }
    }

    private Apfloat workBinary() {
        Apfloat one = new Apfloat(firstArgument.getValue());
        Apfloat two = new Apfloat(secondArgument.getValue());
        switch (operationType) {
            case ADDITION:
                return one.add(two);
            case SUBTRACTION:
                return one.subtract(two);
            case DIVISION:
                return one.divide(two);
            case MULTIPLICATION:
                return one.multiply(two);
            case POW:
                return pow(one, two.intValue());
            default:
                return null;

        }
    }
}
