package interpreter.nonterminal;

import interpreter.Context;
import interpreter.Expression;

public class NonTerminalExpression implements Expression {
    private double number;

    public NonTerminalExpression(double number) {
        this.number = number;
    }

    @Override
    public void interpret(Context c) {
        c.push(number);
    }
}
