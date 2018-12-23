package input.operand;

public class Number implements Operand {
    private Double number;

    public Number(Double number) {
        this.number = number;
    }

    public Double getNumber() {
        return number;
    }
}
