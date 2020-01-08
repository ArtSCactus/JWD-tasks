package interpreter.calculator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExpressionRootFinderTest {
    private static final List<String> SEQUENCE_ONE = new ArrayList<>(Arrays.asList("4+4*(10+1)", "10+1"));
    private static final List<String> SEQUENCE_TWO = new ArrayList<>(Collections.singletonList("4+(10+4*3)+(10+4*2)"));
    private static final List<String> SEQUENCE_THREE = new ArrayList<>(Arrays.asList(
            "4*(10+(4*(15+10)))", "10+(4*(15+10))", "4*(15+10)", "15+10"));

    @Test(dataProvider = "test expressions for root finding")
    public void findRootExpression(String correctExpressionRoot, String testExpression) {
       Assert.assertEquals(new ExpressionRootFinder().findRoot(testExpression), correctExpressionRoot);
    }
    @DataProvider(name = "test expressions for root finding")
    public Object[][] getDataForRootFindingTest(){
        return new String[][]{{"3+4", "5*(3*(3+4))"},
                {"10*10+4", "10+34*(25+60*(10*10+4))"},
                {"10+4*3", "5*(4+(10+4*3)+(10+4*2))"},
                {"10+1", "2+2*(4+4*(10+1))"},
                {"10+2", "5+4*(10+2)"},
                {"3+10", "2+(1*(3+10))"}};
    }
    @DataProvider(name = "test expressions for sequence finding")
    public Object[][] getDataForSequenceFindingTest(){
        return new Object[][]{
                {SEQUENCE_ONE, "2+2*(4+4*(10+1))"},
                {SEQUENCE_TWO, "5*(4+(10+4*3)+(10+4*2))"},
                {SEQUENCE_THREE, "1+(4*(10+(4*(15+10))))"}
        };
    }
    @Test(dataProvider = "test expressions for sequence finding")
    public void shouldReturnCorrectSequence(List<String> correctSequence, String expression){
        List<String> methodsSequence = new ExpressionRootFinder().findRootSequences(expression);
        System.out.println(methodsSequence.toString());
        Assert.assertEquals(methodsSequence.size(), correctSequence.size());
        Assert.assertEquals(methodsSequence, correctSequence);
    }
    @DataProvider(name = "test data for inner expression existence validation")
    public Object[][] getDataForInnerExpressionValidation(){
        return new Object[][]{
                {true, "2+3*(10*2+3)"},
                {false, "2+2"}
        };
    }
    @Test(dataProvider = "test data for inner expression existence validation")
    public void shouldCorrectlyDeterminePresenceOfNestedExpression(boolean answer, String expression){
        Assert.assertEquals(new ExpressionRootFinder().isExpressionContainsInnerExpressions(expression), answer);
    }
}