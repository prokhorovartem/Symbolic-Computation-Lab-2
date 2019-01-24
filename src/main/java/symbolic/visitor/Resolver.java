package symbolic.visitor;

import symbolic.model.Expression;

public interface Resolver {
    Expression resolveExpression(Expression expression);
}
