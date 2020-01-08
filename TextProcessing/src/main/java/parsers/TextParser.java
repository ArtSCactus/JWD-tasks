package parsers;

import entity.Component;
import entity.ComponentType;
import entity.Composite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class TextParser extends Parser {
    private static final Logger LOGGER = LogManager.getLogger(TextParser.class);
    private Parser successor;

    private static final String PARAGRAPH_REGEXP = "(\\t|\\s{4})";

    public TextParser(Parser successor) {
        this.successor = successor;
    }

    @Override
    public Component parse(String text) {
        List<String> paragraphs = Arrays.asList(text.split(PARAGRAPH_REGEXP));
        Composite textComposite = new Composite();
        textComposite.setType(ComponentType.TEXT);
        paragraphs.forEach(currentString ->{
            Component paragraphComposite = successor.parse(currentString);
            ((Composite)paragraphComposite).setType(ComponentType.PARAGRAPH);
            textComposite.addComponent(paragraphComposite);
        });
        LOGGER.info("Text successfully parsed on paragraphs.");
        return textComposite;
    }
}
