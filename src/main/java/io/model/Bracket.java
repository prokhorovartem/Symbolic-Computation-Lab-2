package io.model;

import io.visitor.ArgumentConvertVisitor;

public enum Bracket implements InputExpression {
    OPENING_BRACKET,
    CLOSING_BRACKET;

    @Override
    public void accept(ArgumentConvertVisitor visitor) {
        visitor.visit(this);
    }

}
