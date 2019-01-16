package symbolic.model.impl;

import input.ArgumentConvertVisitor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import symbolic.model.Expression;
import symbolic.visitor.Visitor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Variable implements Expression {

    private BigDecimal value;
    private String variable;

    public Variable(BigDecimal value) {
        this.value = value;
    }

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(ArgumentConvertVisitor visitor) {

    }

    @Override
    public boolean isOperation() {
        return false;
    }
}
