package interpreter.terminal;

import interpreter.Context;
import interpreter.Expression;

public class BitAddition implements Expression {
    @Override
    public void interpret(Context c) {
        int a = c.pop().intValue();
        c.push((double) (~a));
    }
}
