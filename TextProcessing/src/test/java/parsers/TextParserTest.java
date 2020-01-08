package parsers;

import entity.Component;
import entity.Leaf;
import org.junit.Assert;
import org.junit.Test;
import service.ChainBuilder;

public class TextParserTest {
    private static final String TESTING_TEXT = "This is a small text. " +
            "\tJust to test parsers. You are in 2-d paragraph now. Or not." +
            "    Here was 4 spaces. You are in the 3-d paragraph now.";
    private static final String CORRECT_WORD = "You";

    @Test
    public void parse() {
        Parser parser = ChainBuilder.buildChain();
        Component component = parser.parse(TESTING_TEXT);
        Component paragraph = (Component) component.getChild(2);
        Component sentence = (Component) paragraph.getChild(1);
        Leaf word = (Leaf) sentence.getChild(0);
        Assert.assertEquals(CORRECT_WORD, word.getValue());
    }
}