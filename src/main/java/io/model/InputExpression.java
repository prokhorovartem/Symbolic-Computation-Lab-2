package io.model;

import io.visitor.ArgumentConvertVisitor;

public interface InputExpression {
    void accept(ArgumentConvertVisitor visitor);
}
