package interpreter.terminal;

import interpreter.Context;
import interpreter.Expression;

public class DivideOperation implements Expression {
    @Override
    public void interpret(Context c) {
        c.push(c.pop()/c.pop());
    }
}
