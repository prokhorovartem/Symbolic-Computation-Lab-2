package input;

public enum BinaryOperation implements InputExpression {
    ADDITION,
    SUBTRACTION,
    DIVISION,
    MULTIPLICATION,
    POW;

    @Override
    public void accept(ArgumentConvertVisitor visitor) {
        visitor.visit(this);
    }

}
