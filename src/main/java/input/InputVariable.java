package input;

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

    InputVariable(String variable) {
        this.variable = variable;
    }

    InputVariable(BigDecimal value) {
        this.value = value;
    }

    @Override
    public void accept(ArgumentConvertVisitor visitor) {

    }
}
