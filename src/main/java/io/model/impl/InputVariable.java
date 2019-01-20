package io.model.impl;

import io.model.InputExpression;
import io.visitor.ArgumentConvertVisitor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class InputVariable implements InputExpression {

    private BigDecimal value;
    private String variable;

    public InputVariable(String variable) {
        this.variable = variable;
    }

    public InputVariable(BigDecimal value) {
        this.value = value;
    }

    @Override
    public void accept(ArgumentConvertVisitor visitor) {
        visitor.visit(this);
    }
}
