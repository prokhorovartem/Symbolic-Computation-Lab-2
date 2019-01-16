package input;

import java.util.List;

public class ArgumentConvertVisitorImpl implements ArgumentConvertVisitor {

    private List<InputExpression> reversedExpression;
    private int i;

    ArgumentConvertVisitorImpl(List<InputExpression> reversedExpression, int i) {
        this.reversedExpression = reversedExpression;
        this.i = i;
    }

    @Override
    public void visit(BinaryOperation binaryOperation) {
        InputOperation operation = InputOperation.builder()
                .firstArgument(reversedExpression.get(i - 2))
                .operationType(reversedExpression.get(i))
                .secondArgument(reversedExpression.get(i - 1))
                .build();
//        вставляем новую операцию вместо трех старых
        reversedExpression.remove(i - 2);
        reversedExpression.remove(i - 2);
        reversedExpression.set(i - 2, operation);
    }

    @Override
    public void visit(UnaryOperation unaryOperation) {
        InputOperation operation = InputOperation.builder()
                .firstArgument(reversedExpression.get(i - 1))
                .operationType(reversedExpression.get(i))
                .build();
        //вставляем новую операцию вместо двух старых
        reversedExpression.remove(i - 1);
        reversedExpression.set(i - 1, operation);
    }

    @Override
    public void visit(Bracket bracket) {
        reversedExpression.remove(i);
    }
}
