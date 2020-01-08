package interpreter.calculator;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;

public class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @Test(dataProvider = "test expressions and their results")
    public void shouldCorrectlyCalculateResult(int correctValue, String expression) throws ParseException {
        Assert.assertEquals((double) correctValue, calculator.calculate(expression));
    }

    @DataProvider(name = "test expressions and their results")
    public Object[][] getTestData() {
        return new Object[][]{{3>>5, "3>>5"},
                {3<<5, "3<<5"},
                {4&8, "4&8"},
                {4>>>2, "4>>>2"},
                {10|5, "10|5"},
                {~8, "~8"},
                {9^4, "9^4"}};
    }
}