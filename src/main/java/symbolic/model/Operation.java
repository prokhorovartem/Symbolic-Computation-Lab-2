package symbolic.model;

public interface Operation extends Expression {

    Expression getOperationType();

    Expression getFirstArgument();

    Expression getSecondArgument();

    @Override
    default boolean isOperation() {
        return true;
    }
}
