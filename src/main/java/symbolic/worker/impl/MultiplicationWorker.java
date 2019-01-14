package symbolic.worker.impl;

import symbolic.model.Expression;

public class MultiplicationWorker extends AbstractWorker {
    public MultiplicationWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public MultiplicationWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        return null;
    }
}