package input;

public interface ArgumentConvertVisitor {
    void visit(BinaryOperation operation);

    void visit(UnaryOperation operation);

    void visit(Bracket bracket);

    void visit(InputVariable inputVariable);
}
