package symbolic.worker.impl;

import org.apfloat.Apfloat;
import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;
import symbolic.visitor.impl.IntegrationParamHolder;

import java.math.BigDecimal;
import java.util.Objects;

public class DivisionWorker extends AbstractWorker {
    public DivisionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public DivisionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        final String name = IntegrationParamHolder.getInstance().getName();
        if (firstArgument.isOperation() && secondArgument.isOperation()) {
            throw new UnsupportedOperationException("Div of functions");
        } else if (firstArgument.isVariable() && secondArgument.isVariable()) {
            Variable firstArg = (Variable) firstArgument;
            Variable secondArg = (Variable) secondArgument;
            if (firstArg.isValueSet() && secondArg.isValueSet()) {
                Apfloat firstArgument = new Apfloat(firstArg.getValue());
                Apfloat secondArgument = new Apfloat(secondArg.getValue());
                return new Variable(new BigDecimal(firstArgument.divide(secondArgument).doubleValue()));
            } else if (Objects.equals(firstArg.getName(), secondArg.getName())) {
                return new Variable(BigDecimal.ONE);
            } else if (name.equals(firstArg.getName())) {
                return new OperationImpl(
                        OperationType.DIVISION,
                        new VariableWorker(firstArg).work(),
                        secondArgument
                );
            } else if (name.equals(secondArg.getName())) {
                return new OperationImpl(
                        OperationType.MULTIPLICATION,
                        firstArgument,
                        new OperationImpl(
                                OperationType.LOG,
                                secondArgument
                        )
                );
            } else {
                return new OperationImpl(OperationType.DIVISION, firstArgument, secondArgument);
            }
        } else {
            return new OperationImpl(OperationType.DIVISION, firstArgument, secondArgument);
        }
    }
}