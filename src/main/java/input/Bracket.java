package input;

public enum Bracket implements InputExpression {
    OPENING_BRACKET,
    CLOSING_BRACKET;

    @Override
    public void accept(ArgumentConvertVisitor visitor) {
        visitor.visit(this);
    }

}
