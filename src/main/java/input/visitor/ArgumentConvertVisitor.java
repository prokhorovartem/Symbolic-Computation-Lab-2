package input.visitor;

import input.model.BinaryOperation;
import input.model.Bracket;
import input.model.impl.InputVariable;
import input.model.UnaryOperation;

public interface ArgumentConvertVisitor {
    void visit(BinaryOperation operation);

    void visit(UnaryOperation operation);

    void visit(Bracket bracket);

    void visit(InputVariable inputVariable);
}
