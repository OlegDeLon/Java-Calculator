package com.example.calc.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static java.lang.Character.isDigit;

public class PolishNotation {
    private final static Map<Character, Integer> dict = Map.ofEntries(
            Map.entry('(', 0),
            Map.entry('+', 1),
            Map.entry('-', 1),
            Map.entry('x', 2),
            Map.entry('*', 2),
            Map.entry('/', 2),
            Map.entry('m', 2),
            Map.entry('^', 3),
            Map.entry('s', 4),
            Map.entry('c', 4),
            Map.entry('t', 4),
            Map.entry('p', 4),
            Map.entry('v', 4),
            Map.entry('d', 4),
            Map.entry('q', 4),
            Map.entry('n', 4),
            Map.entry('o', 4),
            Map.entry('u', 5)
    );

    private String parseIncome(String income_string) {

        if (income_string.contains("asin")) {
            income_string = income_string.replaceAll("asin", "p");
        }
        if (income_string.contains("acos")) {
            income_string = income_string.replaceAll("acos", "v");
        }
        if (income_string.contains("atan")) {
            income_string = income_string.replaceAll("atan", "d");
        }
        if (income_string.contains("sin")) {
            income_string = income_string.replaceAll("sin", "s");
        }
        if (income_string.contains("cos")) {
            income_string = income_string.replaceAll("cos", "c");
        }
        if (income_string.contains("tan")) {
            income_string = income_string.replaceAll("tan", "t");
        }
        if (income_string.contains("sqrt")) {
            income_string = income_string.replaceAll("sqrt", "q");
        }
        if (income_string.contains("ln")) {
            income_string = income_string.replaceAll("ln", "n");
        }
        if (income_string.contains("log")) {
            income_string = income_string.replaceAll("log", "o");
        }
        if (income_string.contains("mod")) {
            income_string = income_string.replaceAll("mod", "m");
        }
        if (income_string.charAt(0) == '-') {
            income_string = income_string.replace(income_string.charAt(0), 'u');
        }
        for (int i = 0; i < income_string.length(); ++i) {
            if (i < income_string.length() - 1 && income_string.charAt(i) == '(' && income_string.charAt(i + 1) == '-') {
//                income_string = income_string.replace(income_string.charAt(i + 1), 'u');
                income_string = income_string.substring(0, i + 1) + 'u' + income_string.substring(i + 2);
            }

            if (isOperator(income_string.charAt(i)) && income_string.charAt(i + 1) == '-') {
                income_string = income_string.replace(income_string.charAt(i + 1), 'u');
            }

            if (income_string.charAt(i) == 'E') {
                income_string = income_string.substring(0, i) + "*10" + income_string.substring(i + 1);
            }
        }
        return income_string;
    }

    public boolean isTrigonometry(char current) {
        return current == 'p' || current == 'v' || current == 'd'
                || current == 's' || current == 'c' || current == 't'
                || current == 'q' || current == 'n' || current == 'o'
                || current == 'm';
    }

    public boolean isOperator(char current) {
        return current == '+' || current == '-' || current == '*' || current == '/';
    }

    private void inputValidation(String income_string) {
        int leftbracket = 0;
        int rightbracket = 0;

        for (int i = 0; i < income_string.length(); ++i) {
            char cur = income_string.charAt(i);

            if (income_string.charAt(i) == '(') {
                leftbracket++;
                if (i < income_string.length() - 1) {
                    if (income_string.charAt(i + 1) == '*'
                            || income_string.charAt(i + 1) == '/'
                            || income_string.charAt(i + 1) == '+'
                            || income_string.charAt(i + 1) == ')'
                            || income_string.charAt(i + 1) == 'm'
                            || income_string.charAt(i + 1) == '^') {
                        throw new IllegalArgumentException("Invalid operand after open bracket");
                    }
                }

                if (i != 0) {
                    if (isDigit(income_string.charAt(i - 1)) || income_string.charAt(i - 1) == ')') {
                        throw new IllegalArgumentException("Invalid operand before open bracket");
                    }
                }
            }

            if (income_string.charAt(i) == ')') {
                rightbracket++;
                if (leftbracket < rightbracket) {
                    throw new IllegalArgumentException("Something wrong with brackets");
                }
                if (i != 0) {
                    if (income_string.charAt(i - 1) == '*'
                            || income_string.charAt(i - 1) == '/'
                            || income_string.charAt(i - 1) == '+'
                            || income_string.charAt(i - 1) == '-'
                            || income_string.charAt(i - 1) == 'm'
                            || income_string.charAt(i - 1) == '^') {
                        throw new IllegalArgumentException("Invalid operand before close bracket");
                    }
                }
                if (i < income_string.length() - 1) {
                    if (isDigit(income_string.charAt(i + 1)) || income_string.charAt(i + 1) == '(') {
                        throw new IllegalArgumentException("Invalid operand after close bracket");
                    }
                }

            }

            if (i < income_string.length() - 1) {
                if (income_string.charAt(i) != ')' && income_string.charAt(i) != '('
                        && !isDigit(income_string.charAt(i)) && !isDigit(income_string.charAt(i + 1))
                        && income_string.charAt(i + 1) != '(' && income_string.charAt(i + 1) != 'x'
                        && income_string.charAt(i) != 'x' && !isTrigonometry(income_string.charAt(i+1))
                        && income_string.charAt(i + 1) != 'u' && income_string.charAt(i + 1) != ')' ) {

                    throw new IllegalArgumentException("Operators shouldn't go one by one");
                }
            }
        }

        if (leftbracket != rightbracket) {
            throw new IllegalArgumentException("Open brackets != close brackets");
        }

    }

    public List<String> makePolish(String income_string) {

        income_string = parseIncome(income_string);

        inputValidation(income_string);

        Stack<Character> oper_stack = new Stack<Character>();

        List<String> finalstring = new ArrayList<>();


        int index = 0;
        while (index < income_string.length()) {

            if (isDigit(income_string.charAt(index))) {

                StringBuilder digit = new StringBuilder();

                while(isDigit(income_string.charAt(index)) || income_string.charAt(index) == '.' && index < income_string.length() - 1) {
                    digit.append(income_string.charAt(index));
                    if (index < income_string.length() - 1) {
                        index++;
                    } else {
                        break;
                    }
                }

                finalstring.add(digit.toString());
                if (index == income_string.length() - 1) {
                    break;
                }
            } else {

                if (income_string.charAt(index) == 'u') {
                    finalstring.add("0");
                }

                if (income_string.charAt(index) == ')') {

                    while(oper_stack.peek() != '(') {
                        finalstring.add(Character.toString(oper_stack.pop()));
                    }

                    oper_stack.pop();
                    index++;
                    continue;

                } else if (income_string.charAt(index) == '(') {
                    oper_stack.push(income_string.charAt(index));

                } else if (!oper_stack.isEmpty() && dict.get(income_string.charAt(index)) <= dict.get(oper_stack.peek())) {

                    finalstring.add(Character.toString(oper_stack.pop()));

                    while(!oper_stack.isEmpty() && dict.get(income_string.charAt(index)) <= dict.get(oper_stack.peek())) {
                        finalstring.add(Character.toString(oper_stack.pop()));
                    }

                    oper_stack.push(income_string.charAt(index));
                }  else {
                    oper_stack.push(income_string.charAt(index));
                }
                index++;
            }
        }

        while(!oper_stack.isEmpty()) {
            finalstring.add(Character.toString(oper_stack.pop()));
        }

        return finalstring;
    }
}
