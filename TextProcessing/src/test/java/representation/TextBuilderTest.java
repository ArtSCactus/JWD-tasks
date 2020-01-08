package representation;

import entity.Component;
import org.junit.Assert;
import org.junit.Test;
import reader.Reader;
import service.ChainBuilder;

import java.io.IOException;

public class TextBuilderTest {
    private static final String SOURCE_TEXT_PATH = "src/test/resources/source text.txt";
    private static final String TEST = "It has survived not only (five) centuries  but also the leap into 52.0 " +
            "electronic typesetting  remaining 0.0 essentially 0.0 unchanged It was popularised in the 5.0 in the with " +
            "the release of Letraset sheets containing Lorem Ipsum passages  and more recently with desktop publishing " +
            "software like Aldus PageMaker including versions of lorem Ipsum \n" +
            "\tIt is a long established fact that a reader will be distracted by the readable content of a page when " +
            "looking at its layout The point of using 2.0 Ipsum is that it has a more or less normal distribution of" +
            " letters  as opposed to using (Content here)  content here'  making it look like readable English \n" +
            "\tIt is a 0.0 established fact that a reader will be of a page when looking at its layout \n" +
            "\tBye \n\t";

    @Test
    public void shouldCorrectlyRestoreText() throws IOException {
        TextBuilder textBuilder = new TextBuilder();
        Component parsedText = ChainBuilder.buildChain().parse(Reader.readText(SOURCE_TEXT_PATH));
        String representedText = textBuilder.represent(parsedText);
        Assert.assertEquals(TEST, representedText);
    }

}