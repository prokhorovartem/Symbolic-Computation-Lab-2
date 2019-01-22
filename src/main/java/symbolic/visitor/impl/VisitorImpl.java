package symbolic.visitor.impl;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.impl.Variable;
import symbolic.visitor.Visitor;
import symbolic.worker.Dispatcher;

public class VisitorImpl implements Visitor {

    @Override
    public Expression visit(Expression expression) {
        Resolver resolver = new Resolver();
        return expression.isOperation() ? visit((Operation) expression) : resolver.resolveVariable((Variable) expression);
    }

    @Override
    public Expression visit(Operation operation) {
        Dispatcher dispatcher = new Dispatcher();
        Resolver resolver = new Resolver();
        return dispatcher.resolveOperation(
                operation.getOperationType(),
                resolver.resolveExpression(operation.getFirstArgument()),
                (operation.getSecondArgument() != null)
                        ? resolver.resolveExpression(operation.getSecondArgument()) : null
        );
    }
}
