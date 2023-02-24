package ru.bagrov.onelinecalculator.services;


import org.springframework.stereotype.Service;

import java.util.Stack;


@Service
public class CalculatorService {

    public String calculate(String input) {

        String rpn = expressionToRPN(input);
        String answer = RPNToAnswer(rpn);

        return "Результат вычисления выражения " + input + " равен: " + answer;
    }

    private String expressionToRPN(String expression) {
        String preparedExpression = prepareExpression(expression);
        StringBuilder rpnExpression = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        int priority;

        for (int i = 0; i < preparedExpression.length(); i++) {
            priority = getPriority(preparedExpression.charAt(i));

            if (priority == 0) {
                rpnExpression.append(preparedExpression.charAt(i));
            }
            if (priority == 1) {
                stack.push(preparedExpression.charAt(i));
            }
            if (priority > 1) {
                rpnExpression.append(' ');
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) {
                        rpnExpression.append(stack.pop());
                    } else break;
                }
                stack.push(preparedExpression.charAt(i));
            }
            if (priority == -1) {
                rpnExpression.append(' ');
                while (getPriority(stack.peek()) != 1) {
                    rpnExpression.append(stack.pop());
                }
                stack.pop();
            }
        }
        while (!stack.empty()) {
            rpnExpression.append(stack.pop());
        }
        return rpnExpression.toString();
    }

    private String prepareExpression(String expression) {
        StringBuilder preparedExpression = new StringBuilder();
        if (expression.trim().length() <= 1) {
            return "z";
        }
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == 's') {
                if (expression.length() - i >= 2 && expression.charAt(i + 1) == 'i') {
                    StringBuilder temp = new StringBuilder();
                    temp.append(c).append(expression.charAt(i + 1)).append(expression.charAt(i + 2));
                    if (!temp.toString().equals("sin")) {
                        return "z";
                    } else {
                        i += 2;
                    }
                } else if (expression.charAt(i + 1) == 'q' && expression.length() - i >= 3) {
                    StringBuilder temp = new StringBuilder();
                    temp.append(c).append(expression.charAt(i + 1)).append(expression.charAt(i + 2)).append(expression.charAt(i + 3));
                    if (!temp.toString().equals("sqrt")) {
                        return "z";
                    } else {
                        c = 'q';
                        i += 3;
                    }
                } else {
                    return "z";
                }
            }
            if (c == '-') {
                if (i == 0) {
                    preparedExpression.append('0');
                } else if (expression.charAt(i - 1) == '(') {
                    preparedExpression.append('0');
                } else {
                    for (int j = i - 1; j > 0; j--) {
                        if (expression.charAt(j) == '+' ||
                                expression.charAt(j) == '-' ||
                                expression.charAt(j) == '*' ||
                                expression.charAt(j) == '/' ||
                                expression.charAt(j) == 's' ||
                                expression.charAt(j) == 'q') {
                            c = '~';
                            break;
                        } else if (expression.charAt(j) >= 48 && expression.charAt(j) <= 57) {
                            break;
                        }
                    }
                }
            }
            preparedExpression.append(c);
        }
        return preparedExpression.toString();
    }

    private String RPNToAnswer(String rpn) {
        String operand = "";
        Stack<Double> stack = new Stack<>();
        boolean unaryFlag = false;

        for (int i = 0; i < rpn.length(); i++) {

            if (rpn.charAt(i) == 'z') {
                return "Некорректно указана функция";
            }

            if (rpn.charAt(i) == '~') {
                unaryFlag = true;
                continue;
            }
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                }
                try {
                    if (unaryFlag) {
                        stack.push(Double.parseDouble(operand) * -1);
                        unaryFlag = false;
                    } else {
                        stack.push(Double.parseDouble(operand));
                    }
                } catch (NumberFormatException e) {
                    return operand + " не является числом";
                }
                operand = "";
            }

            if (getPriority(rpn.charAt(i)) > 1 && getPriority(rpn.charAt(i)) < 4) {
                double rightOperand = stack.pop();
                double leftOperand = stack.pop();

                switch (rpn.charAt(i)) {
                    case '+' -> stack.push(leftOperand + rightOperand);
                    case '-' -> stack.push(leftOperand - rightOperand);
                    case '*' -> stack.push(leftOperand * rightOperand);
                    case '/' -> {
                        if (rightOperand == 0) {
                            return "Деление на ноль невозможно";
                        }
                        stack.push(leftOperand / rightOperand);
                    }
                }
            }
            if (getPriority(rpn.charAt(i)) == 4) {
                double rightOperand = stack.pop();
                switch (rpn.charAt(i)) {
                    case 'q' -> stack.push(Math.sqrt(rightOperand));
                    case 's' -> stack.push(Math.sin(rightOperand));
                }
            }
        }
        return String.valueOf(stack.pop());
    }

    private int getPriority(Character c) {
        if (c == 'q' || c == 's') {
            return 4;
        } else if (c == '*' || c == '/') {
            return 3;
        } else if (c == '+' || c == '-') {
            return 2;
        } else if (c == '(') {
            return 1;
        } else if (c == ')') {
            return -1;
        } else return 0;
    }
}
