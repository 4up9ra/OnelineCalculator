package ru.bagrov.onelinecalculator.services;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServiceTest {

    @Test
    public void testAddition()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "10 + 5";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("15.0", result);
    }

    @Test
    public void testSubtraction()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "10 - 5";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("5.0", result);
    }

    @Test
    public void testMultiplication()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "10 * 5";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("50.0", result);
    }

    @Test
    public void testDivision()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "10 / 5";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("2.0", result);
    }

    @Test
    public void testSqrt()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "sqrt(16)";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("4.0", result);
    }

    @Test
    public void testSin()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "sin(16)";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("-0.2879033166650653", result);
    }

    @Test
    public void testWithUnaryOperator()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "2 - (-2)";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("4.0", result);
    }

    @Test
    public void testWithUnaryOperatorWithNegativeResult()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "2 + -4";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("-2.0", result);
    }

    @Test
    public void testExpressionShouldReturnZero()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "2 * (sqrt(16) - 20.5) + 100 / 5 - -13";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("0.0", result);
    }

    @Test
    public void testDivisionByZero()  {

        //given
        CalculatorService calculatorService = new CalculatorService();
        String expression = "10 / 0";

        //when
        String result = calculatorService.calculate(expression).split(": ")[1];

        //then
        assertEquals("Деление на ноль невозможно", result);
    }

}