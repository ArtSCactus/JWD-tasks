package service;

import interpreter.calculator.Calculator;
import interpreter.calculator.ExpressionRootFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExpressionHandler {
    private static final Logger LOGGER = LogManager.getLogger(ExpressionHandler.class);

    /**
     * Solves the math expression, represented as string object.
     * <p>
     * WARNING:
     * The current implementation of this method does not take into account the order
     * of performing mathematical operations.
     *
     * @param expression Math expression, represented as String object
     * @return result as String object.
     * @see Calculator#calculate(String)
     */
    public String solveExpression(String expression) {
        Calculator calculator = new Calculator();
        ExpressionRootFinder rootFinder = new ExpressionRootFinder();
        if (rootFinder.isExpressionContainsInnerExpressions(expression)) {
            while (rootFinder.isExpressionContainsInnerExpressions(expression)) {
                String rootExpression = rootFinder.findRoot(expression);
                expression = expression.replace("(" + rootExpression + ")",
                        calculator.calculate(rootExpression) + "");
            }
            String result = calculator.calculate(expression) + "";
            LOGGER.info("A complex expression was solved. Expression: " + expression + " Result: " + result);
            return result;
        } else {
            String result = calculator.calculate(expression) + "";
            LOGGER.info("A simple expression was solved. Expression: " + expression + " Result: " + result);
            return result;
        }
    }
}
