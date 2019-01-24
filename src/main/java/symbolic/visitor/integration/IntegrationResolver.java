package symbolic.visitor.integration;

import symbolic.model.Expression;
import symbolic.visitor.Visitor;

public class IntegrationResolver implements symbolic.visitor.Resolver {
    private static final Visitor VISITOR = new IntegrationVisitor();

    @Override
    public Expression resolveExpression(Expression expression) {
        return expression.accept(VISITOR);
    }
}
