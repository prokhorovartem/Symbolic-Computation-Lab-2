package symbolic.visitor.calculation;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.impl.Variable;
import symbolic.visitor.Resolver;
import symbolic.visitor.Visitor;
import symbolic.worker.calculation.CalculationException;
import symbolic.worker.calculation.OperationWorker;
import symbolic.worker.calculation.VariableWorker;

public class CalculationVisitor implements Visitor {
    @Override
    public Expression visit(Expression expression) {
        return expression.isOperation() ? visit((Operation) expression) : visit((Variable) expression);
    }

    @Override
    public Expression visit(Operation operation) {
        Resolver resolver = new CalculationResolver();
        return new OperationWorker(
                operation.getOperationType(),
                resolver.resolveExpression(operation.getFirstArgument()),
                resolver.resolveExpression(operation.getSecondArgument())

        ).work();
    }

    private Expression visit(Variable variable){
        return new VariableWorker(variable).work();
    }
}
