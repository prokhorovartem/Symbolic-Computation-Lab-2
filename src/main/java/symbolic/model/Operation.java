package symbolic.model;

public interface Operation extends Expression {

    OperationType getOperationType();

    Expression getFirstArgument();

    Expression getSecondArgument();

    @Override
    default boolean isOperation() {
        return true;
    }
}
