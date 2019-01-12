package symbolic.model;

import symbolic.visitor.Visitor;

public interface Expression {

    Expression accept(Visitor visitor);

    boolean isOperation();
}
