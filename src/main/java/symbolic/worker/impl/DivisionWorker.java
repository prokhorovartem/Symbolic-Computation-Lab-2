package symbolic.worker.impl;

import symbolic.model.Expression;

public class DivisionWorker extends AbstractWorker {
    public DivisionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public DivisionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        return null;
    }
}