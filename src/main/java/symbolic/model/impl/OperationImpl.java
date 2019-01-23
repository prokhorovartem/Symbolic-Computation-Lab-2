package symbolic.model.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import symbolic.model.Expression;
import symbolic.model.Operation;
import symbolic.model.OperationType;
import symbolic.visitor.Visitor;

import javax.xml.bind.annotation.*;

@Getter
@Builder
@AllArgsConstructor
@XmlRootElement(name = "operation")
@XmlType(propOrder = {"firstArgument", "secondArgument"}, name = "operation")
public class OperationImpl implements Operation {
    @NonNull
    @XmlAttribute
    private OperationType operationType;
    @NonNull
    @XmlAnyElement
    private Expression firstArgument;
    @XmlAnyElement
    private Expression secondArgument;

    public OperationImpl(@NonNull OperationType operationType, @NonNull Expression firstArgument) {
        this.operationType = operationType;
        this.firstArgument = firstArgument;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public OperationImpl() {
    }
}
