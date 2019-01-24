package io;

import io.model.BinaryOperation;
import io.model.Bracket;
import io.model.InputExpression;
import io.model.UnaryOperation;
import io.model.impl.InputVariable;
import symbolic.visitor.integration.IntegrationParamHolder;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputModel {
    private Resource resource;

    private Map<String, String> context = new HashMap<>();

    public InputModel(Resource resource) {
        this.resource = resource;
    }

    public List<InputExpression> parse() {
        String integral = null;

        try (Scanner sc = new Scanner(resource.getFile())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("\\delayed")) {
                    StringBuilder sb = new StringBuilder();
                    String[] stringWithoutSpace = line.trim().split(" ");
                    String[] split = stringWithoutSpace[1].split("=");
                    Pattern pattern = Pattern.compile("[-+*/^]");
                    Matcher matcher = pattern.matcher(split[1]);
                    List<String> operators = new ArrayList<>();
                    while (matcher.find()) {
                        operators.add(matcher.group());
                    }
                    String[] operands = split[1].split("[-+*/^]");
                    if (operators.size() > 0) {
                        for (int i = 0; i < operands.length; i++) {
                            if (context.containsKey(operands[i]))
                                sb.append(context.get(operands[i]));
                            else sb.append(operands[i]);
                            if (i != operands.length - 1)
                                sb.append(operators.get(i));
                        }
                        context.put(split[0], sb.toString());
                    } else
                        context.put(split[0], split[1]);
                }
                if (line.contains("$\\int")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(line);
                    String nextLine = sb.toString();
                    while (!nextLine.contains("$")) {
                        nextLine = sc.nextLine();
                        sb.append(nextLine);
                    }
                    integral = findIntegrationParam(sb);
                    break;
                } else if (!sc.hasNext()) System.out.println("Интеграл не найден");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файла не существует!");
        } catch (NoSuchElementException e) {
            System.out.println("Выражение написано неправильно");
        }
        return checkForContext(integral);
    }

    private String findIntegrationParam(StringBuilder integral) {
        String[] chars = integral.toString().split(" ");
        char[] diff = chars[chars.length - 1].toCharArray();
        if (diff[0] == 'd') {
            IntegrationParamHolder.getInstance().setName(String.valueOf(diff[1]));
        } else IntegrationParamHolder.getInstance().setName("x");
        return integral.toString().replace(chars[chars.length - 1], "").replaceAll("[$]*", "");
    }

    private List<InputExpression> checkForContext(String integral) {
        List<String> listOfOperationsAndOperands = createListOfOperationsAndOperands(integral, true);
        for (int i = 0; i < listOfOperationsAndOperands.size(); i++) {
            if (listOfOperationsAndOperands.get(i).matches("[\\d]+")
                    || listOfOperationsAndOperands.get(i).matches("[-+*/^]")) {
                continue;
            }
            if (listOfOperationsAndOperands.get(i).matches("[\\w]+"))
                try {
                    UnaryOperation.valueOf(listOfOperationsAndOperands.get(i).toUpperCase());
                } catch (Exception e) {
                    if (i != listOfOperationsAndOperands.size() - 1)
                        if (Objects.equals(listOfOperationsAndOperands.get(i + 1), "(")) {
                            StringBuilder sb = new StringBuilder();
                            while (!Objects.equals(listOfOperationsAndOperands.get(i), ")")) {
                                sb.append(listOfOperationsAndOperands.get(i));
                                listOfOperationsAndOperands.remove(i);
                            }
                            sb.append(listOfOperationsAndOperands.get(i));
                            listOfOperationsAndOperands.remove(i);
                            if (context.containsKey(sb.toString())) {
                                String delayedString = context.get(sb.toString());
                                List<String> context = createListOfOperationsAndOperands(delayedString, false);
                                listOfOperationsAndOperands.addAll(i, context);
                            }
                        } else if (context.containsKey(listOfOperationsAndOperands.get(i))) {
                            String delayedString = context.get(listOfOperationsAndOperands.get(i));
                            List<String> context = createListOfOperationsAndOperands(delayedString, false);
                            listOfOperationsAndOperands.remove(i);
                            listOfOperationsAndOperands.addAll(i, context);
                        } else if (context.containsKey(listOfOperationsAndOperands.get(i)))
                            listOfOperationsAndOperands.set(i, context.get(listOfOperationsAndOperands.get(i)));
                }
        }
        return convertStringToExpression(listOfOperationsAndOperands);

    }

    private List<String> createListOfOperationsAndOperands(String delayedString, boolean isFirst) {
        Pattern pattern = Pattern.compile("[\\d]+|[-+*/^]|[\\w]+|[()]");
        Matcher matcher = pattern.matcher(delayedString);
        List<String> words = new ArrayList<>();
        if (!isFirst)
            words.add("(");
        while (matcher.find()) {
            words.add(matcher.group());
        }
        if (!isFirst)
            words.add(")");
        return words;
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
                    if (!(words.get(i - 1).matches("[\\d]+")) && !Objects.equals(words.get(i - 1), IntegrationParamHolder.getInstance().getName())) {
                        try {
                            expressions.add(new InputVariable(new BigDecimal(words.get(i + 1)).multiply(BigDecimal.valueOf(-1))));
                            i++;
                        } catch (Exception e) {
                            expressions.add(BinaryOperation.SUBTRACTION);
                        }
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
                try {
                    expressions.add(UnaryOperation.valueOf(word.toUpperCase()));
                } catch (Exception e) {
                    if (i != words.size() - 1)
                        if (Objects.equals(words.get(i + 1), "(")) {
                            StringBuilder sb = new StringBuilder();
                            while (!Objects.equals(words.get(i), ")")) {
                                sb.append(words.get(i));
                                i++;
                            }
                            sb.append(words.get(i));
                            expressions.add(new InputVariable(sb.toString()));
                        } else
                            expressions.add(new InputVariable(word));
                    else
                        expressions.add(new InputVariable(word));
                }
        }
        return expressions;
    }
}
