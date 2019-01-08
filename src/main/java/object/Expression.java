package object;

import lombok.Data;

@Data
public class Expression implements Operand {
    private Operand firstOperand;
    private Operand secondOperand;
    private Operation operation;

    public Expression(Operand firstOperand, Operand secondOperand, Operation operation) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.operation = operation;
    }
}
