package symbolic.worker.impl;

import lombok.NonNull;
import symbolic.model.Expression;
import symbolic.worker.Worker;

public abstract class AbstractWorker implements Worker {

    @NonNull
    protected Expression firstArgument;
    protected Expression secondArgument;

    AbstractWorker(Expression firstArgument, Expression secondArgument) {
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
    }

    AbstractWorker(Expression firstArgument) {
        this.firstArgument = firstArgument;
    }

    AbstractWorker() {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract Expression work();
}
