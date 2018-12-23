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
            for (Operand operand : operands) {
                if (operand instanceof BinaryOperator) {
                    writer.write("<" + ((BinaryOperator) operand).name() + "/>\n");
                }
                if (operand instanceof Variable) {
                    writer.write("\t<VARIABLE>");
                    writer.write(((Variable) operand).name());
                    writer.write("</VARIABLE>\n");
                }
                if (operand instanceof Number) {
                    writer.write("\t<NUMBER>");
                    writer.write(((Number) operand).getNumber().toString());
                    writer.write("</NUMBER>\n");
                }
            }
            writer.write("</ANSWER>");
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
        }
    }
}
