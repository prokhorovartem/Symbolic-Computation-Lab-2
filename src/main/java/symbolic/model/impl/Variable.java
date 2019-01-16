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

    private BigDecimal value;

    public Variable(BigDecimal value) {
        this.value = value;
    }

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isOperation() {
        return false;
    }
}
