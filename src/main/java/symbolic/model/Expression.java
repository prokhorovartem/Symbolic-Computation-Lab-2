package symbolic.model;

import input.ArgumentConvertVisitor;
import symbolic.visitor.Visitor;

public interface Expression {

    Expression accept(Visitor visitor);

    void accept(ArgumentConvertVisitor visitor);

    boolean isOperation();
}
