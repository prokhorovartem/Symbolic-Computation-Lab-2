package symbolic.model;

import input.ArgumentConvertVisitor;
import symbolic.visitor.Visitor;

public enum OperationType implements Expression{
    ADDITION,
    SUBTRACTION,
    DIVISION,
    MULTIPLICATION,
    POW,
    INT,
    SIN,
    COS,
    TAN,
    CTG,
    OPENING_BRACKET,
    CLOSING_BRACKET;

    @Override
    public Expression accept(Visitor visitor) {
        return null;
    }

    @Override
    public void accept(ArgumentConvertVisitor visitor) {

    }

    @Override
    public boolean isOperation() {
        return true;
    }
}
