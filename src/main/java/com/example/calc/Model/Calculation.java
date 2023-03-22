package com.example.calc.Model;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import static java.lang.Character.isDigit;
import static java.lang.Math.pow;

public class Calculation {
    public String calculate(List<String> inputString) {

        Stack<Double> digits_stack = new Stack<Double>();

        Collections.replaceAll(inputString, "x", "0");

        for (String s : inputString) {
            if (isDigit(s.charAt(0))) {
                digits_stack.push(Double.parseDouble(s));
            } else {
                double result = 0;
                switch (s) {
                    case "+" -> {
                        double operand1 = digits_stack.pop();
                        double operand2 = digits_stack.pop();
                        result = operand2 + operand1;
                        digits_stack.push(result);
                    }
                    case "-", "u" -> {
                        double operand1 = digits_stack.pop();
                        double operand2 = digits_stack.pop();
                        result = operand2 - operand1;
                        digits_stack.push(result);
                    }
                    case "*" -> {
                        double operand1 = digits_stack.pop();
                        double operand2 = digits_stack.pop();
                        result = operand2 * operand1;
                        digits_stack.push(result);
                    }
                    case "/" -> {
                        double operand1 = digits_stack.pop();
                        double operand2 = digits_stack.pop();

                        if (operand1 == 0) {
                            throw new IllegalArgumentException("You can't divide by zero");
                        }
                        result = operand2 / operand1;
                        digits_stack.push(result);
                    }
                    case "^" -> {
                        double operand1 = digits_stack.pop();
                        double operand2 = digits_stack.pop();
                        result = pow(operand2, operand1);
                        digits_stack.push(result);
                    }
                    case "s" -> {
                        result = Math.sin(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "c" -> {
                        result = Math.cos(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "t" -> {
                        result = Math.tan(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "p" -> {
                        result = Math.asin(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "v" -> {
                        result = Math.acos(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "d" -> {
                        result = Math.atan(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "q" -> {
                        result = Math.sqrt(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "o" -> {
                        result = Math.log10(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "n" -> {
                        result = Math.log(digits_stack.pop());
                        digits_stack.push(result);
                    }
                    case "m" -> {

                        double operand1 = digits_stack.pop();
                        double operand2 = digits_stack.pop();
                        result = operand2 % operand1;
                        digits_stack.push(result);
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (Double aDouble : digits_stack) {
            result.append(aDouble);
        }

        return result.toString();
    }
}
