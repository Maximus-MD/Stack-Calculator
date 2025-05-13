package cedacri.training;

import lombok.Getter;

import java.util.Stack;

@Getter
public class Calculator {
    private final String expression;

    public Calculator(String expression) {
        this.expression = expression;
    }

    public static double calculateRpnExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Expression is empty.");
        }

        Stack<Double> stack = new Stack<>();

        expression = infixToRPN(expression);

        String[] expressionArray = expression.split("");

        double x, y;
        double result;

        for (String s : expressionArray) {
            try {
                double number = Double.parseDouble(s);
                stack.push(number);
            } catch (NumberFormatException e) {
                if (stack.size() >= 2) {
                    y = stack.pop();
                    x = stack.pop();

                    result = switch (s) {
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

    private static String infixToRPN(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (Character.isLetter(c)) {
                throw new IllegalArgumentException("Invalid expression : not a digit.");
            } else if(isSpace(c)){
                continue;
            } else if (Character.isDigit(c)) {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && priorityOfOperator(c) <= priorityOfOperator(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    private static boolean isSpace(char c) {
        return c == ' ';
    }

    private static int priorityOfOperator(char c) {
        if (c == '/' || c == '*') {
            return 2;
        } else if (c == '-' || c == '+') {
            return 1;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator("4 + (2 /2+3)*7-5");
        Calculator calculator1 = new Calculator("( 1 + 2) * 3");
        Calculator calculator2 = new Calculator("((6/3)+2)*4");

        System.out.println("4 + (2 / 2 + 3) * 7 - 5 = " + calculateRpnExpression(calculator.getExpression()));
        System.out.println("(1 + 2) * 3 = " + calculateRpnExpression(calculator1.getExpression()));
        System.out.println("((6 / 3) + 2) * 4 = " + calculateRpnExpression(calculator2.getExpression()));
    }
}