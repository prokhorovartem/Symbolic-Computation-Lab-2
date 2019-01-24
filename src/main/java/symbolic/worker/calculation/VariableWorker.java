package symbolic.worker.calculation;

import symbolic.model.impl.Variable;
import symbolic.visitor.calculation.CalculationParamHolder;

import java.util.Objects;

public class VariableWorker extends AbstractWorker {

    public VariableWorker(Variable variable) {
        super(Objects.requireNonNull(variable));
    }

    @Override
    public Variable work() {
        CalculationParamHolder holder = CalculationParamHolder.getInstance();
        if (firstArgument.isValueSet()) {
            return firstArgument;
        }
        else if (holder.getName().equals(firstArgument.getName())) {
            return new Variable(holder.getValue());
        } else {
            throw new RuntimeException(String.format("Symbol %s is unknown", firstArgument.getName()));
        }
    }
}
