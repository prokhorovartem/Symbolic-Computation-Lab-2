package symbolic.visitor;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.worker.calculation.CalculationException;

public interface Visitor {
    Expression visit(Expression expression);

    Expression visit(Operation operation);
}
