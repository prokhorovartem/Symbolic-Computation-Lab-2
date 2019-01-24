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

    private Map<Template, String> context = new HashMap<>();

    public InputModel(Resource resource) {
        this.resource = resource;
    }

    public List<InputExpression> parse() {
        String integral = null;

        try (Scanner sc = new Scanner(resource.getFile())) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.contains("\\delayed")) {
                    StringBuilder nameOfFunction = new StringBuilder();
                    StringBuilder operandsOfFunctionInLeft = new StringBuilder();
                    StringBuilder operandsOfFunctionInRight = new StringBuilder();
                    List<String> stringWithoutSpace = Arrays.asList(line.trim().split(" "));
                    List<String> leftAndRight = Arrays.asList(stringWithoutSpace.get(1).split("="));
                    char[] charsOfLeftSide = leftAndRight.get(0).toCharArray();
                    for (int i = 0; i < charsOfLeftSide.length; i++) {
                        if (charsOfLeftSide[i] == '(') {
                            while (charsOfLeftSide[i + 1] != ')') {
                                operandsOfFunctionInLeft.append(charsOfLeftSide[i + 1]);
                                i++;
                            }
                            break;
                        } else nameOfFunction.append(charsOfLeftSide[i]);
                    }
                    String[] split = operandsOfFunctionInLeft.toString().split(",");
                    List<String> operandsOfFunction = Arrays.asList(split);
                    Template template = new Template(nameOfFunction.toString(), operandsOfFunction);
                    Pattern pattern = Pattern.compile("[-+*/^]");
                    Matcher matcher = pattern.matcher(leftAndRight.get(1));
                    List<String> operators = new ArrayList<>();
                    while (matcher.find()) {
                        operators.add(matcher.group());
                    }
                    String[] operands = leftAndRight.get(1).split("[-+*/^]");
                    if (operators.size() > 0) {
                        for (int i = 0; i < operands.length; i++) {
                            if (context.containsKey(new Template(operands[i], null)))
                                operandsOfFunctionInRight.append(context.get(new Template(operands[i], null)));
                            else operandsOfFunctionInRight.append(operands[i]);
                            if (i != operands.length - 1)
                                operandsOfFunctionInRight.append(operators.get(i));
                        }
                        context.put(template, operandsOfFunctionInRight.toString());
                    } else
                        context.put(template, leftAndRight.get(1));
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
                    if (i != listOfOperationsAndOperands.size() - 1) {
                        if (Objects.equals(listOfOperationsAndOperands.get(i + 1), "(")) {
                            StringBuilder function = new StringBuilder();
                            StringBuilder operands = new StringBuilder();
                            function.append(listOfOperationsAndOperands.get(i));
                            if (context.containsKey(new Template(function.toString(), null))) {
                                Set<Map.Entry<Template, String>> entrySet = context.entrySet();
                                String desiredObject = context.get(new Template(function.toString(), null));
                                List<String> arguments = new ArrayList<>();
                                for (Map.Entry<Template, String> pair : entrySet) {
                                    if (desiredObject.equals(pair.getValue())) {
                                        arguments = pair.getKey().getArguments();
                                        break;
                                    }
                                }
                                Map<String, String> argumentsAndValues = new HashMap<>();
                                listOfOperationsAndOperands.remove(i);
                                listOfOperationsAndOperands.remove(i);
                                while (!Objects.equals(listOfOperationsAndOperands.get(i), ")")) {
                                    operands.append(listOfOperationsAndOperands.get(i));
                                    listOfOperationsAndOperands.remove(i);
                                }
                                listOfOperationsAndOperands.remove(i);
                                String[] operandsArray = operands.toString().split(",");
                                for (int j = 0; j < arguments.size(); j++) {
                                    argumentsAndValues.put(arguments.get(j), operandsArray[j]);
                                }
                                String delayedString = context.get(new Template(function.toString(), null));
                                Pattern pattern = Pattern.compile("[\\d]+|[-+*/^]|[\\w]+|[()]");
                                Matcher matcher = pattern.matcher(delayedString);
                                List<String> words = new ArrayList<>();
                                while (matcher.find()) {
                                    words.add(matcher.group());
                                }
                                for (int j = 0; j < words.size(); j++) {
                                    if (argumentsAndValues.containsKey(words.get(j))) {
                                        words.set(j, argumentsAndValues.get(words.get(j)));
                                    }
                                }
                                String string = String.join("", words);
                                List<String> context = createListOfOperationsAndOperands(string, false);
                                listOfOperationsAndOperands.addAll(i, context);
                            } else {
                                while (!Objects.equals(listOfOperationsAndOperands.get(i), ")")) {
                                    function.append(listOfOperationsAndOperands.get(i));
                                    listOfOperationsAndOperands.remove(i);
                                }
                            }
                        } else if (context.containsKey(new Template(listOfOperationsAndOperands.get(i), null))) {
                            String delayedString = context.get(new Template(listOfOperationsAndOperands.get(i), null));
                            List<String> context = createListOfOperationsAndOperands(delayedString, false);
                            listOfOperationsAndOperands.remove(i);
                            listOfOperationsAndOperands.addAll(i, context);
                        }
                    } else if (context.containsKey(new Template(listOfOperationsAndOperands.get(i), null))) {
                        String delayedString = context.get(new Template(listOfOperationsAndOperands.get(i), null));
                        List<String> context = createListOfOperationsAndOperands(delayedString, false);
                        listOfOperationsAndOperands.remove(i);
                        listOfOperationsAndOperands.addAll(i, context);
                    }
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
