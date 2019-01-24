package symbolic.visitor.integration;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.visitor.Resolver;
import symbolic.visitor.Visitor;
import symbolic.worker.integration.Dispatcher;

public class IntegrationVisitor implements Visitor {

    @Override
    public Expression visit(Expression expression) {
        return expression.isOperation() ? visit((Operation) expression) : expression;
    }

    @Override
    public Expression visit(Operation operation) {
        Dispatcher dispatcher = new Dispatcher();
        Resolver resolver = new IntegrationResolver();
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
