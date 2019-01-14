package symbolic.worker.impl;

import symbolic.model.Expression;

public class SubtractionWorker extends AbstractWorker {
    public SubtractionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public SubtractionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        return null;
    }
}