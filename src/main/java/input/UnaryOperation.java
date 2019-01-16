package input;

import symbolic.model.Expression;
import symbolic.visitor.Visitor;

public enum UnaryOperation implements Expression {
    SIN,
    COS,
    TAN,
    CTG,
    INT;

    @Override
    public Expression accept(Visitor visitor) {
        return null;
    }

    @Override
    public void accept(ArgumentConvertVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isOperation() {
        return false;
    }
}
