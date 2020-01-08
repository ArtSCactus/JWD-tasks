package service;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.ParseException;

public class ExpressionHandlerTest {
    private static final ExpressionHandler SOLVER = new ExpressionHandler();

    @Test(dataProvider = "solved expressions for solver test")
    public void testSolveExpression(String result, String expression) {
        Assert.assertEquals(SOLVER.solveExpression(expression), result);
    }

    @DataProvider(name = "solved expressions for solver test")
    public Object[][] getSolvedExpressionsForTest() {
        return new String[][]{
                {"46.0", "2+2+2+(10*4)"},
                {"9.0", "~9^5>>3&8|1"},
                {"104160.0", "48*36+8*(50+10)"}
        };
    }
}