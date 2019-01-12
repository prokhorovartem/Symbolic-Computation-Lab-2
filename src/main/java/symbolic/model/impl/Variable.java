package symbolic.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import symbolic.model.Expression;
import symbolic.visitor.Visitor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class Variable implements Expression {
    @Getter
    @Setter
    private BigDecimal value;

    @Override
    public Expression accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean isOperation() {
        return false;
    }
}
