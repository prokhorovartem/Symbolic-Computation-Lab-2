package symbolic.visitor.calculation;

import symbolic.model.Expression;
import symbolic.visitor.Resolver;
import symbolic.visitor.Visitor;

public class CalculationResolver implements Resolver {
    private static final Visitor VISITOR = new CalculationVisitor();

    @Override
    public Expression resolveExpression(Expression expression) {
        return expression.accept(VISITOR);
    }
}
