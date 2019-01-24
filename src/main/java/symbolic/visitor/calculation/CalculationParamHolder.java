package symbolic.visitor.calculation;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class CalculationParamHolder {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private BigDecimal value;

    private static CalculationParamHolder instance = null;

    public static CalculationParamHolder getInstance() {
        if (instance == null) {
            instance = new CalculationParamHolder();
        }
        return instance;
    }
}
