package parsers;

import entity.Component;
import entity.Leaf;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SentenceParserTest {
private static final String TESTING_SENTENCE = "A normal sentence with 6&9|(3&4) expression here";
private static final String CORRECT_WORD ="normal";
private final List<String> correctWordsList = Arrays.asList("A","normal","sentence", "with", "6&9|(3&4)","expression", "here");

    @Test
    public void parse() {
    SentenceParser parser = new SentenceParser();
        Component component = parser.parse(TESTING_SENTENCE);
        Leaf leaf =(Leaf) component.getChild(1);
        Assert.assertEquals(CORRECT_WORD, leaf.getValue());
    }

    @Test
    public void toWords() {
        SentenceParser parser = new SentenceParser();
        List<String> methodsWords = parser.toWords(TESTING_SENTENCE);
        Assert.assertEquals(correctWordsList, methodsWords);
    }
}