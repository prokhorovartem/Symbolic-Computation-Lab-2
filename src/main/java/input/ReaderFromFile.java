package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReaderFromFile {
    public static String readExpression() {
        File file = new File("input.tex");
        String integral = null;
        int startExpression = 11;
        String endExpression = "\\,dx\\]";
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("\\[\\int")) {
                    integral = line.substring(startExpression, line.indexOf(endExpression)).replaceAll(" ", "");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файла не существует!");
        }
        return integral;
    }
}
