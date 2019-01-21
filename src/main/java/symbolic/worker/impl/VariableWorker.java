package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.OperationImpl;
import symbolic.model.impl.Variable;

import java.math.BigDecimal;

public class VariableWorker extends AbstractWorker {
    private final Variable variable;
    private final String name;

    public VariableWorker(Variable variable, String name) {
        super(variable);
        this.variable = variable;
        this.name = name;
    }

    @Override
    public Expression work() {
        if (!variable.isValueSet() && variable.getName().equals(name)) {
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
            } else {
                return new OperationImpl(
                        OperationType.MULTIPLICATION,
                        new Variable(variable.getValue()),
                        new Variable(name)
                );
            }
        }
    }
}
