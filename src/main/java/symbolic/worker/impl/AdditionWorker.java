package symbolic.worker.impl;

import symbolic.model.Expression;

public class AdditionWorker extends AbstractWorker {
    public AdditionWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public AdditionWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        return null;
    }
}
