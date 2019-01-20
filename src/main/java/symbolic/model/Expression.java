package symbolic.model;

import symbolic.visitor.Visitor;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public interface Expression {

    Expression accept(Visitor visitor);

    default boolean isOperation() {
        return false;
    }

    default boolean isVariable() {
        return false;
    }
}
