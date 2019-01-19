package symbolic.model.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.visitor.Visitor;

@Getter
@Builder
@AllArgsConstructor
public class OperationImpl implements Operation {
    @NonNull
    private OperationType operationType;
    @NonNull
    private Expression firstArgument;
    private Expression secondArgument;

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
