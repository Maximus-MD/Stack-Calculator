package cedacri.training;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Getter
public class Calculator {
    private final String expression;

    public Calculator(String expression) {
        this.expression = expression;
    }

    public static double calculateRpnExpression(String expression) {
        Stack<Double> stack = new Stack<>();

        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Expression is empty.");
        }

        List<String> expressionArray = infixToRPN(expression);

        for (String s : expressionArray) {
            try {
                double number = Double.parseDouble(s);
                stack.push(number);
            } catch (NumberFormatException e) {
                if (stack.size() >= 2) {
                    double y = stack.pop();
                    double x = stack.pop();

                    double result = switch (s) {
                        case "+" -> x + y;
                        case "-" -> x - y;
                        case "*" -> x * y;
                        case "/" -> x / y;
                        default -> throw new IllegalArgumentException("Unknown operator : " + s);
                    };
                    stack.push(result);
                }
            }
        }
        return stack.pop();
    }

    private static List<String> infixToRPN(String expression) {
        Stack<String> stack = new Stack<>();
        List<String> result = new ArrayList<>();
        String[] splitExpression = expression
                .replaceAll("([()*/+-])", " $1 ")
                .trim()
                .split("\\s+");

        for (String s : splitExpression) {
            if (s.matches("(?=.*[A-Za-z])(?=.*\\d).*")) {
                throw new IllegalArgumentException("Invalid expression : not a digit.");
            } else if (s.matches("[0-9]+")) {
                result.add(s);
            } else if (s.equals("(")) {
                stack.push(s);
            } else if (s.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && priorityOfOperator(s) <= priorityOfOperator(stack.peek())) {
                    result.add(stack.pop());
                }
                stack.push(s);
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }

        System.out.println("RPN expression : " + result);
        return result;
    }

    private static int priorityOfOperator(String str) {
        if (str.equals("/") || str.equals("*")) {
            return 2;
        } else if (str.equals("-") || str.equals("+")) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator("4 + (2 /2+3)*7-5");
        Calculator calculator1 = new Calculator("( 1 + 2) * 3");
        Calculator calculator2 = new Calculator("4*(5/3)");
        Calculator calculator3 = new Calculator("(11 + 18) * 20 - 2 ");
        Calculator calculator4 = new Calculator("(350 * 18) / 27 - 85 + (741 / 9) ");

        System.out.println("4 + (2 / 2 + 3) * 7 - 5 = " + calculateRpnExpression(calculator.getExpression()) + "\n");
        System.out.println("(1 + 2) * 3 = " + calculateRpnExpression(calculator1.getExpression()) + "\n");
        System.out.println("4 * (5 / 3) = " + calculateRpnExpression(calculator2.getExpression()) + "\n");
        System.out.println("(11 + 18) * 20 - 2 = " + calculateRpnExpression(calculator3.getExpression()) + "\n");
        System.out.println("(350 * 18) / 27 - 85 + (741 / 9) = " + calculateRpnExpression(calculator4.getExpression()) + "\n");
    }
}