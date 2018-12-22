package input.operand;

public class Number implements Operand {
    private Integer number;

    public Number(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }
}
