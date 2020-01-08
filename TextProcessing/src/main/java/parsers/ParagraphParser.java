package parsers;

import entity.Component;
import entity.Composite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class ParagraphParser extends Parser {
    private static final Logger LOGGER = LogManager.getLogger(ParagraphParser.class);
    private static final String TO_SENTENCES_REGEXP = "(\\s*\\.{1,3}\\s*|[!?])";
    private Parser successor;

    public ParagraphParser(Parser successor) {
        this.successor = successor;
    }

    @Override
    public Component parse(String paragraph) {
        List<String> sentences = toSentences(paragraph);
        Component component = new Composite();
        sentences.forEach(currentSentence ->{
            Component sentenceComposite = successor.parse(currentSentence);
            component.addComponent(sentenceComposite);
        });
        LOGGER.info("Paragraph successfully parsed on sentences.");
        return component;
    }

    public List<String> toSentences(String paragraph){
        return Arrays.asList(paragraph.split(TO_SENTENCES_REGEXP));
    }
}
