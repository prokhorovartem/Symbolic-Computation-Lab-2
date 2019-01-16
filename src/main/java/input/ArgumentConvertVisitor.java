package input;

public interface ArgumentConvertVisitor {
    void visit(BinaryOperation operation);

    void visit(UnaryOperation operation);

    void visit(Bracket bracket);
}
