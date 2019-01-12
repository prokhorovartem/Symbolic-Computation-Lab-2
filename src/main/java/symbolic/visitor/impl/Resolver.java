package symbolic.visitor.impl;

import symbolic.model.Expression;
import symbolic.visitor.Visitor;

class Resolver {
    private static final Visitor VISITOR = new VisitorImpl();

    static Expression resolveExpression(Expression expression) {
        return expression.accept(VISITOR);
    }
}
