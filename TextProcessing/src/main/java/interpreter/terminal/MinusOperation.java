package interpreter.terminal;

import interpreter.Context;
import interpreter.Expression;

public class MinusOperation implements Expression {
    @Override
    public void interpret(Context c) {
        c.push(c.pop()-c.pop());
    }
}
