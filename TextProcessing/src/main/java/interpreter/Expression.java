package interpreter;

@FunctionalInterface
public interface Expression {
    void interpret(Context c);
}
