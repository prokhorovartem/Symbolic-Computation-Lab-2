package io;

import io.model.BinaryOperation;
import io.model.Bracket;
import io.model.InputExpression;
import io.model.UnaryOperation;
import io.model.impl.InputVariable;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputModel {
    private Resource resource;

    public InputModel(Resource resource) {
        this.resource = resource;
    }

    public List<InputExpression> parse() {
        String integral = null;
        try (Scanner sc = new Scanner(resource.getFile())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("$\\int")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(line);
                    String nextLine = sb.toString();
                    while (!nextLine.contains("$")) {
                        nextLine = sc.nextLine();
                        sb.append(nextLine);
                    }
                    integral = sb.toString().replaceAll("[,$]*", "").replace("dx", "");
                    break;
                } else if (!sc.hasNext()) System.out.println("Интеграл не найден");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файла не существует!");
        } catch (NoSuchElementException e) {
            System.out.println("Выражение написано неправильно");
        }
        return createListOfOperationsAndOperands(integral);
    }

    private List<InputExpression> createListOfOperationsAndOperands(String integral) {
        Pattern pattern = Pattern.compile("[\\d]+|[-+*/^]|[\\w]+|[()]");
        Matcher matcher = pattern.matcher(integral);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return convertStringToExpression(words);

    }

    private List<InputExpression> convertStringToExpression(List<String> words) {
        List<InputExpression> expressions = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (word.matches("[\\d]+")) {
                expressions.add(new InputVariable(new BigDecimal(word)));
                continue;
            }
            if (word.matches("[-+*/^]")) {
                if (word.equals("+")) {
                    expressions.add(BinaryOperation.ADDITION);
                }
                if (word.equals("-"))
                    if (!(words.get(i - 1).matches("[\\d]+"))) {
                        expressions.add(new InputVariable(new BigDecimal(words.get(i + 1)).multiply(BigDecimal.valueOf(-1))));
                        i++;
                    } else
                        expressions.add(BinaryOperation.SUBTRACTION);
                if (word.equals("*"))
                    expressions.add(BinaryOperation.MULTIPLICATION);
                if (word.equals("/"))
                    expressions.add(BinaryOperation.DIVISION);
                if (word.equals("^"))
                    expressions.add(BinaryOperation.POW);
                continue;
            }
            if (word.equals("x")) {
                expressions.add(new InputVariable(word));
                continue;
            }
            if (word.equals("(")) {
                expressions.add(Bracket.OPENING_BRACKET);
                continue;
            }
            if (word.equals(")")) {
                expressions.add(Bracket.CLOSING_BRACKET);
                continue;
            }
            if (word.matches("[\\w]+"))
                expressions.add(UnaryOperation.valueOf(word.toUpperCase()));
        }
        return expressions;
    }
}