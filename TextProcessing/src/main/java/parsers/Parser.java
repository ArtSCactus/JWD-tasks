package parsers;

import entity.Component;

public abstract class Parser {
    private Parser successor;

    public Parser getSuccessor() {
        return successor;
    }

    public void setSuccessor(Parser successor) {
        this.successor = successor;
    }

    public abstract Component parse(String text);
}
