package interpreter.calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionRootFinder {
    private static final Logger LOGGER = LogManager.getLogger(ExpressionRootFinder.class);
    private static final Pattern COMMON_INNER_EXPRESSION_TEMPLATE = Pattern.compile("\\(.{2,}\\)");
    private static final Pattern SINGLE_INNER_EXP_TEMPLATE = Pattern.compile("\\(.{2,}?\\)");

    /**
     * Returns the deepest expression in parentheses.
     * If there is multiple expressions in parentheses, will be returned the deepest one.
     * <p>
     * For example:
     * in expression 5+4*(10+2) will be returned 10+2
     * in expression 2+2*(4+4*(10+1)) will be returned 10+1
     * in expression 2+2*(10*(2+2)+10*(3+3)) will be returned 2+2
     * in expression 2+2*(10*(2*(5+5)+2)+10*(3+3)) will be returned 5+5
     *
     * @param textExpression Math expression like 2+2*(3+10)
     * @return clear and the deepest expression without parentheses
     */
    public String findRoot(String textExpression) {
        String rootExpression = textExpression;
        while (true) {
            Matcher matcher = COMMON_INNER_EXPRESSION_TEMPLATE.matcher(rootExpression);
            if (!matcher.find()) {
                break;
            } else {
                rootExpression = matcher.group();
                rootExpression = new StringBuilder(rootExpression).deleteCharAt(0).toString();
                rootExpression = new StringBuilder(rootExpression).deleteCharAt(rootExpression.length() - 1).toString();
                matcher = SINGLE_INNER_EXP_TEMPLATE.matcher(rootExpression);
                int counter = 0; // groupCount() doesn't work as expected
                while (matcher.find()) {
                    counter++;
                }
                if (counter > 1) {
                    matcher.reset();
                    List<List<String>> branches = new ArrayList<>();
                    while (matcher.find()) {
                        branches.add(findRootSequences(matcher.group()));
                    }
                    List<String> longestBranch = new ArrayList<>();
                    for (List<String> branch : branches) {
                        if (branch.size() > longestBranch.size()) {
                            longestBranch = branch;
                        }
                    }
                    rootExpression = longestBranch.get(longestBranch.size() - 1);
                }
            }
        }
        LOGGER.info("A root of expression " + textExpression + " was found: " + rootExpression);
        return rootExpression;
    }

    /**
     * Returns sequence of roots in math expression with parentheses.
     * If there is multiple expressions in parentheses, will be returned the last expression in parentheses
     * before multiple math expressions in parentheses.
     * <p>
     * Example:
     * in expression 2+(1*(3+10)) will be output: [1*(3+10), 3+10];
     * in expression 2+(1*(3+10)+2*(5+5)) will be output: [1*(3+10)+2*(5+5)];
     * in expression 1+(4*(10+(4*(15+10)))) will be output: [4*(10+(4*(15+10))), 10+(4*(15+10)),
     * 4*(15+10), 15+10];
     *
     * @param sourceExpression Expression, where the sequence will be searched
     * @return {@code List<String>} of math expressions from parent to the deepest.
     */
    public List<String> findRootSequences(String sourceExpression) {
        String currentExpression = sourceExpression;
        List<String> expressions = new ArrayList<>();
        while (true) {
            Matcher matcher = COMMON_INNER_EXPRESSION_TEMPLATE.matcher(currentExpression);
            matcher.find();
            currentExpression = matcher.group();
            currentExpression = new StringBuilder(currentExpression).deleteCharAt(0).toString();
            currentExpression = new StringBuilder(currentExpression).deleteCharAt(currentExpression.length() - 1).toString();
            expressions.add(currentExpression);
            matcher = COMMON_INNER_EXPRESSION_TEMPLATE.matcher(currentExpression);
            if (!matcher.find()) {
                break;
            } else {
                matcher = SINGLE_INNER_EXP_TEMPLATE.matcher(currentExpression);
                int counter = 0;
                while (matcher.find()) {
                    counter++;
                }
                if (counter > 1) {
                    break;
                }
            }
        }
        LOGGER.info("In expression " + sourceExpression + " was found a" +
                " sequence of root expressions: " + expressions.toString());
        return expressions;
    }

    /**
     * Validates does the expression has at least one construction with parentheses.
     *
     * @param expression source expression to validate
     * @return true if this expression contains at least 1 construction with parentheses.
     */
    public boolean isExpressionContainsInnerExpressions(String expression) {
        return COMMON_INNER_EXPRESSION_TEMPLATE.matcher(expression).find();
    }
}
