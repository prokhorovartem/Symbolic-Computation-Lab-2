package symbolic.model;

import symbolic.visitor.Visitor;

public interface Expression {

    Expression accept(Visitor visitor);

    default boolean isOperation() {
        return false;
    }

    default boolean isVariable() {
        return false;
    }
}
