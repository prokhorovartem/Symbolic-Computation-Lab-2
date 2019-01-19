package symbolic.visitor.impl;

import symbolic.model.Expression;
import symbolic.visitor.Visitor;

public class Resolver {
    private static final Visitor VISITOR = new VisitorImpl();

    public static Expression resolveExpression(Expression expression) {
        return expression.accept(VISITOR);
    }
}
