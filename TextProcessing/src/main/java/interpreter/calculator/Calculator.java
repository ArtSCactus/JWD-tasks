package interpreter.calculator;

import interpreter.Context;
import interpreter.Expression;
import interpreter.nonterminal.NonTerminalExpression;
import interpreter.terminal.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {
    private static final Logger LOGGER = LogManager.getLogger(Calculator.class);

    private List<Expression> parse(String text) {
        List<Expression> expressions = new ArrayList<>();
        List<String> expressionSymbols = Arrays.asList(text.split("(\\d+\\.\\d+|\\d+)"));
        for (String symbol : expressionSymbols) {
            String currentSymbol = symbol;
            if (currentSymbol.isEmpty()) {
                continue;
            } else {
                switch (currentSymbol) {
                    case "+":
                        expressions.add(new PlusOperation());
                        break;
                    case "-":
                        expressions.add(new MinusOperation());
                        break;
                    case "/":
                        expressions.add(new DivideOperation());
                        break;
                    case "*":
                        expressions.add(new MultiplyOperation());
                        break;
                    case ">>":
                        expressions.add(new BitRightShiftOperation());
                        break;
                    case "<<":
                        expressions.add(new BitLeftShiftOperation());
                        break;
                    case "&":
                        expressions.add(new BitAndOperation());
                        break;
                    case "|":
                        expressions.add(new BiteOrOperation());
                        break;
                    case "^":
                        expressions.add(new BitLogicOrOperation());
                        break;
                    case "~":
                        expressions.add(new BitAddition());
                        break;
                    case ">>>":
                        expressions.add(new ZeroShift());
                        break;
                    default:
                        expressions.add(new NonTerminalExpression(Double.parseDouble(currentSymbol)));
                }
            }
        }
        LOGGER.info("A sequence of operation was determined: " + expressions.toString());
        return expressions;
    }

    /**
     * Calculates only simple expression without any branching.
     * Word "branching" means that expression has constructions in parentheses.
     * Example of expressions, that can be solved:
     * ~8, 3>>5, 3<<5, 6/3, 2*2*3, 5+4+3+4, 3<<5&8 and etc.
     * WARNING:
     * Priority of math operation doesn't work in this realization.
     * So be noticed about this and validate incoming expressions on operation order.
     * Also, if you want, you are free to modify this realization.
     *
     * @param expressionText Expression, that represented as String obj.
     * @return Result of expression, represented as Number child object.
     */
    public Number calculate(String expressionText) {
        List<Expression> expressions = parse(expressionText);
        Context context = new Context();
        List<String> numbers = Arrays.asList(expressionText.split("[><|&^+-/*~]+"));
        // reading from back to save order of appearance
        for (int index = numbers.size() - 1; index >= 0; index--) {
            // in expressions like ~8 can be found an empty String rows like "" in result of .split()
            if (!numbers.get(index).isEmpty()) {
                context.push(Double.parseDouble(numbers.get(index)));
            }
        }
        for (Expression expression : expressions) {
            expression.interpret(context);
        }
        double result = context.pop();
        LOGGER.info("A simple expression was calculated. Expression: " + expressionText + " Result: " + result);
        return result;
    }
}
