package cedacri.training;

import static cedacri.training.Calculator.calculateRpnExpression;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator("4 + (2 /2+3)*7-5");
        Calculator calculator1 = new Calculator("( 1 + 2) * 3");
        Calculator calculator2 = new Calculator("4*(5/3)");
        Calculator calculator3 = new Calculator("(11 + 18) * 20 - 2 ");
        Calculator calculator4 = new Calculator("(350 * 18) / 27 - 85 + (741 / 9) ");
        Calculator calculator5 = new Calculator("10/(1*5)+(26-96-2)+(6*4)/2");

        System.out.println("4 + (2 / 2 + 3) * 7 - 5 = " + calculateRpnExpression(calculator.getExpression()) + "\n");
        System.out.println("(1 + 2) * 3 = " + calculateRpnExpression(calculator1.getExpression()) + "\n");
        System.out.println("4 * (5 / 3) = " + calculateRpnExpression(calculator2.getExpression()) + "\n");
        System.out.println("(11 + 18) * 20 - 2 = " + calculateRpnExpression(calculator3.getExpression()) + "\n");
        System.out.println("(350 * 18) / 27 - 85 + (741 / 9) = " + calculateRpnExpression(calculator4.getExpression()) + "\n");
        System.out.println("10/(1*5)+(26-96-2)+(6*4)/2 = " + calculateRpnExpression(calculator5.getExpression()) + "\n");
    }
}