package symbolic.visitor.impl;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.model.impl.Variable;
import symbolic.visitor.Visitor;
import symbolic.worker.Dispatcher;

public class VisitorImpl implements Visitor {

    @Override
    public Expression visit(Expression expression) {
        return expression.isOperation() ? visit((Operation) expression) : expression;
    }

    @Override
    public Expression visit(Operation operation) {
        Dispatcher dispatcher = new Dispatcher();
        Resolver resolver = new Resolver();
        if (OperationType.POW.equals(operation.getOperationType()) && operation.getFirstArgument().isVariable()) {
            return dispatcher.resolveOperation(operation.getOperationType(), operation.getFirstArgument(), operation.getSecondArgument());
        } else {
            return dispatcher.resolveOperation(
                    operation.getOperationType(),
                    resolver.resolveExpression(operation.getFirstArgument()),
                    (operation.getSecondArgument() != null)
                            ? resolver.resolveExpression(operation.getSecondArgument()) : null
            );
        }
    }
}
