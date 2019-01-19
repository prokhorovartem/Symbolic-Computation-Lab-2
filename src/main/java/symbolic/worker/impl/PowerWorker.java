package symbolic.worker.impl;

import symbolic.model.Expression;
import symbolic.worker.Worker;

public class PowerWorker extends AbstractWorker {
    public PowerWorker(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument);
    }

    public PowerWorker(Expression firstArgument) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression work() {
        return null;
    }
}
