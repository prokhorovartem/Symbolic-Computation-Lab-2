package input;

public interface InputExpression {
    void accept(ArgumentConvertVisitor visitor);
}
