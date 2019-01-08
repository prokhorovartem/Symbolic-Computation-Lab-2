package object;

import lombok.Data;

@Data
public class Number implements Operand {
    private Double number;

    public Number(Double number) {
        this.number = number;
    }
}
