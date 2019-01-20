package symbolic.visitor.impl;

import symbolic.model.Expression;
import symbolic.model.impl.Variable;
import symbolic.visitor.Visitor;
import symbolic.worker.impl.VariableWorker;

public class Resolver {
    public static String name;
    private static final Visitor VISITOR = new VisitorImpl();

    public static Expression resolveExpression(Expression expression) {
        return expression.accept(VISITOR);
    }

    public static Expression resolveVariable(Variable variable) {
        return new VariableWorker(variable, name).work();
    }
}
