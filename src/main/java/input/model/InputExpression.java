package input.model;

import input.visitor.ArgumentConvertVisitor;

public interface InputExpression {
    void accept(ArgumentConvertVisitor visitor);
}
