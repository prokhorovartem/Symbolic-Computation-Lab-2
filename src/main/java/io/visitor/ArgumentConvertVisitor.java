package io.visitor;

import io.model.BinaryOperation;
import io.model.Bracket;
import io.model.impl.InputVariable;
import io.model.UnaryOperation;

public interface ArgumentConvertVisitor {
    void visit(BinaryOperation operation);

    void visit(UnaryOperation operation);

    void visit(Bracket bracket);

    void visit(InputVariable inputVariable);
}
