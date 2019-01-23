package symbolic.model.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import symbolic.model.Expression;
import symbolic.visitor.Visitor;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@XmlType(propOrder = {"value", "name"}, name = "variable")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Variable implements Expression {

    @XmlAttribute
    private BigDecimal value = null;
    @XmlAttribute
    private String name = null;

    public Variable(Integer value) {
        this.value = BigDecimal.valueOf(value);
    }

    public Variable(BigDecimal value) {
        this.value = value;
    }

    public Variable(String name) {
        this.name = name;
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
