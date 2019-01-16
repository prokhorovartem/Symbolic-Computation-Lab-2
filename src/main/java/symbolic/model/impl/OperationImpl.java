package symbolic.model.impl;

import input.ArgumentConvertVisitor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.visitor.Visitor;

@Getter
@Builder
public class OperationImpl implements Operation {
    @NonNull
    private Expression operationType;
    @NonNull
    private Expression firstArgument;
    private Expression secondArgument;

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(ArgumentConvertVisitor visitor) {

    }
}
