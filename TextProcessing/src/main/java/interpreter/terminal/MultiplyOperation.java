package interpreter.terminal;

import interpreter.Context;
import interpreter.Expression;

public class MultiplyOperation implements Expression {
    @Override
    public void interpret(Context c) {
        c.push(c.pop()*c.pop());
    }
}
