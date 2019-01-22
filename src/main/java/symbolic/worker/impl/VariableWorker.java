package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;
import symbolic.visitor.impl.IntegrationParamHolder;

import java.math.BigDecimal;

public class VariableWorker extends AbstractWorker {
    private final Variable variable;

    public VariableWorker(Variable variable) {
        super(variable);
        this.variable = variable;
    }

    @Override
    public Expression work() {
        final String name = IntegrationParamHolder.getInstance().getName();
        if (!variable.isValueSet() && name.equals(variable.getName())) {
            return new OperationImpl(
                    OperationType.DIVISION,
                    new OperationImpl(
                            OperationType.POW,
                            new Variable(variable.getName()),
                            new Variable(2)
                    ),
                    new Variable(2)
            );
        } else {
            if (BigDecimal.ONE.equals(variable.getValue())) {
                return new Variable(name);
            } else if (variable.isValueSet()) {
                return new OperationImpl(
                        OperationType.MULTIPLICATION,
                        new Variable(variable.getValue()),
                        new Variable(name)
                );
            } else {
                return new OperationImpl(
                        OperationType.MULTIPLICATION,
                        new Variable(variable.getName()),
                        new Variable(name)
                );
            }
        }
    }
}
