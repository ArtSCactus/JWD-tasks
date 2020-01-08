package interpreter;

import java.util.ArrayDeque;
import java.util.Deque;

public class Context {
    private final Deque<Double> values = new ArrayDeque<>();

    public Double pop() {
        return values.pop();
    }

    public void push(double n) {
        values.push(n);
    }

    public void push(Double n){
        values.push(n);
    }
}
