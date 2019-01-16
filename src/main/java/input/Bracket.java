package input;

import symbolic.model.Expression;
import symbolic.visitor.Visitor;

public enum Bracket implements Expression {
    OPENING_BRACKET,
    CLOSING_BRACKET;

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
