package io.model;

import io.visitor.ArgumentConvertVisitor;

public enum UnaryOperation implements InputExpression {
    SIN,
    COS,
    TAN,
    INT;

    @Override
    public void accept(ArgumentConvertVisitor visitor) {
        visitor.visit(this);
    }

}
