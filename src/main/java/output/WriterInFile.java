package output;

import input.operand.BinaryOperator;
import input.operand.Number;
import input.operand.Operand;
import input.operand.Variable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriterInFile {
    public static void writeResult(List<Operand> operands) {
        try (FileWriter writer = new FileWriter("output.xml", false)) {
            writer.write("<ANSWER>\n");
            for (int i = 0; i < operands.size(); i++) {
                if (operands.get(i) instanceof BinaryOperator) {
                    writer.write("<" + ((BinaryOperator) operands.get(i)).name() + "/>\n");
//                    writer.write("</" + ((BinaryOperator) operands.get(i)).name() + ">\n");
                }
                if (operands.get(i) instanceof Variable) {
                    writer.write("\t<VARIABLE>");
                    writer.write(((Variable) operands.get(i)).name());
                    writer.write("</VARIABLE>\n");
                }
                if (operands.get(i) instanceof Number) {
                    writer.write("\t<NUMBER>");
                    writer.write(((Number) operands.get(i)).getNumber().toString());
                    writer.write("</NUMBER>\n");
                }
            }
//            writer.write(String.valueOf(Integral.result * Integral.sign));
            writer.write("</ANSWER>");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
        }
    }
}
