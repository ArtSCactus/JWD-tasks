package interpreter.terminal;

import interpreter.Context;
import interpreter.Expression;

public class BitLogicOrOperation implements Expression {
    @Override
    public void interpret(Context c) {
        Integer a = c.pop().intValue();
        Integer b = c.pop().intValue();
        c.push((double) (a ^ b));
    }
}
