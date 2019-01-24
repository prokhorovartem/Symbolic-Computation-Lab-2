package symbolic.worker.calculation;

import lombok.NonNull;
import symbolic.model.OperationType;
import symbolic.model.impl.Variable;

public abstract class AbstractWorker {

    @NonNull
    protected Variable firstArgument;
    protected Variable secondArgument;
    protected OperationType operationType;

    AbstractWorker(Variable firstArgument) {
        this.firstArgument = firstArgument;
    }

    public AbstractWorker(OperationType operationType, Variable firstArgument, Variable secondArgument) {
        this.operationType = operationType;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
    }


    public abstract Variable work() throws CalculationException;
}
