package symbolic.visitor.impl;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.visitor.Visitor;
import symbolic.worker.Dispatcher;

public class VisitorImpl implements Visitor {
    @Override
    public Expression visit(Expression expression) {
        return expression.isOperation() ? visit((Operation) expression) : expression;
    }

    @Override
    public Expression visit(Operation operation) {
        return Dispatcher.resolveOperation(
                operation.getOperationType(),
                Resolver.resolveExpression(operation.getFirstArgument()),
                Resolver.resolveExpression(operation.getSecondArgument())
        );
    }
}
