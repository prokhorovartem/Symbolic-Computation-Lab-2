package model;

import symbolic.model.Expression;
import symbolic.model.OperationType;
import symbolic.model.impl.Variable;

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

    public List parse() {
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

    private List createListOfOperationsAndOperands(String integral) {
        Pattern pattern = Pattern.compile("[\\d]+|[-+*/^]|[\\w]+|[()]");
        Matcher matcher = pattern.matcher(integral);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return convertStringToExpression(words);

    }

    private List convertStringToExpression(List<String> words) {
        List<Expression> expressions = new ArrayList<>();
        for (String word : words) {
            if (word.matches("[\\d]+")) {
                expressions.add(new Variable(new BigDecimal(word)));
                continue;
            }
            if (word.matches("[-+*/^]")) {
                if (word.equals("+"))
                    expressions.add(OperationType.ADDITION);
                if (word.equals("-"))
                    expressions.add(OperationType.SUBTRACTION);
                if (word.equals("*"))
                    expressions.add(OperationType.MULTIPLICATION);
                if (word.equals("/"))
                    expressions.add(OperationType.DIVISION);
                if (word.equals("^"))
                    expressions.add(OperationType.POW);
                continue;
            }
            if (word.equals("x")) {
                expressions.add(new Variable(word));
                continue;
            }
            if (word.equals("(")) {
                expressions.add(OperationType.OPENING_BRACKET);
                continue;
            }
            if (word.equals(")")) {
                expressions.add(OperationType.CLOSING_BRACKET);
                continue;
            }
            if (word.matches("[\\w]+"))
                expressions.add(OperationType.valueOf(word.toUpperCase()));
        }
        return expressions;
    }

//    private static String reversePolandNotation(String expression) {
//        StringBuilder sbStack = new StringBuilder(), sbOut = new StringBuilder();
//        char cIn, cTmp;
//
//        for (int i = 0; i < expression.length(); i++) {
//            cIn = expression.charAt(i);
//            if (isOperation(cIn)) {
//                while (sbStack.length() > 0) {
//                    cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
//                    if (isOperation(cTmp) && (operationPriority(cIn) <= operationPriority(cTmp))) {
//                        sbOut.append(" ").append(cTmp).append(" ");
//                        sbStack.setLength(sbStack.length() - 1);
//                    } else {
////                        sbOut.append(" ");
//                        break;
//                    }
//                }
//                sbOut.append(" ");
//                sbStack.append(cIn);
//            } else if ('(' == cIn) {
//                sbStack.append(cIn);
//            } else if (')' == cIn) {
//                cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
//                while ('(' != cTmp) {
//                    if (sbStack.length() < 1) {
//                        try {
//                            throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    sbOut.append(" ").append(cTmp);
//                    sbStack.setLength(sbStack.length() - 1);
//                    cTmp = sbStack.substring(sbStack.length() - 1).charAt(0);
//                }
//                sbStack.setLength(sbStack.length() - 1);
//            } else {
//                // Если символ не оператор - добавляем в выходную последовательность
//                sbOut.append(cIn);
//            }
//        }
//
//        // Если в стеке остались операторы, добавляем их в входную строку
//        while (sbStack.length() > 0) {
//            sbOut.append(" ").append(sbStack.substring(sbStack.length() - 1));
//            sbStack.setLength(sbStack.length() - 1);
//        }
//
//        return sbOut.toString();
//    }
//
//    private static byte operationPriority(char operation) {
//        switch (operation) {
//            case '^':
//                return 3;
//            case '*':
//            case '/':
//                return 2;
//        }
//        return 1; // Тут остается + и -
//    }
//
//    private static boolean isOperation(char c) {
//        switch (c) {
//            case '+':
//            case '-':
//            case '*':
//            case '/':
//            case '^':
//                return true;
//        }
//        return false;
//    }

}
