package symbolic.model.impl;

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

    private BigDecimal value = null;
    private String variable = null;

    public Variable(Integer value) {
        this.value = BigDecimal.valueOf(value);
    }

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
    public boolean isVariable() {
        return true;
    }

    public boolean isValueSet() {
        return value != null;
    }
}
