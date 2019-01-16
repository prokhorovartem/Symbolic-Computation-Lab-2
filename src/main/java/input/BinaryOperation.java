package input;

import symbolic.model.Expression;
import symbolic.visitor.Visitor;

public enum BinaryOperation implements Expression {
    ADDITION,
    SUBTRACTION,
    DIVISION,
    MULTIPLICATION,
    POW;

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
        return true;
    }
}
