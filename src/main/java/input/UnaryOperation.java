package input;

public enum UnaryOperation implements InputExpression {
    SIN,
    COS,
    TAN,
    CTG,
    INT;

    @Override
    public void accept(ArgumentConvertVisitor visitor) {
        visitor.visit(this);
    }

}
