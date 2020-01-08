package parsers;

import entity.Component;
import entity.Leaf;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ParagraphParserTest {
    private static final String TESTING_TEXT = "This is a small text. Just to test parsers. Or not.";
    private static final String CORRECT_WORD ="Just";
    private final List<String> correctSentences = Arrays.asList("This is a small text",
            "Just to test parsers","Or not");
    @Test
    public void parse() {
        ParagraphParser paragraphParser = new ParagraphParser(new SentenceParser());
        Component component = paragraphParser.parse(TESTING_TEXT);
        Component sentence = (Component) component.getChild(1);
        Leaf word = (Leaf) sentence.getChild(0);
        String result = word.getValue();
        Assert.assertEquals(CORRECT_WORD, result);
    }

    @Test
    public void toSentences() {
        ParagraphParser paragraphParser = new ParagraphParser(new SentenceParser());
        List<String> methodResult = paragraphParser.toSentences(TESTING_TEXT);
        Assert.assertEquals(correctSentences, methodResult);
    }
}