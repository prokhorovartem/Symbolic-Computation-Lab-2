package input;

import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.impl.OperationImpl;

import java.util.List;

public class ArgumentConvertVisitorImpl implements ArgumentConvertVisitor {

    private List<Expression> reversedExpression;
    private int i;

    ArgumentConvertVisitorImpl(List<Expression> reversedExpression, int i) {
        this.reversedExpression = reversedExpression;
        this.i = i;
    }

    @Override
    public void visit(BinaryOperation binaryOperation) {
        Operation operation = OperationImpl.builder()
                .firstArgument(reversedExpression.get(i - 2))
                .operationType(reversedExpression.get(i))
                .secondArgument(reversedExpression.get(i - 1))
                .build();
        //вставляем новую операцию вместо трех старых
        reversedExpression.remove(i - 2);
        reversedExpression.remove(i - 2);
        reversedExpression.set(i - 2, operation);
    }

    @Override
    public void visit(UnaryOperation unaryOperation) {
        Operation operation = OperationImpl.builder()
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
