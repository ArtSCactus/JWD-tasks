package parsers;

import entity.Component;
import entity.Composite;
import entity.Leaf;
import entity.ComponentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class SentenceParser extends Parser{
    private static final Logger LOGGER = LogManager.getLogger(SentenceParser.class);
    private static final String WORDS_SPLITTER = "(\\s*-\\s*|\\s{1,3}|[,;:-])";
    private static final Pattern EXPRESSION_PATTERN  = Pattern.compile("(\\d|\\))[\\d^()|&><+\\-*/]+(\\d|\\))");

    @Override
    public Component parse(String text){
        List<String> words = toWords(text);
        Composite component = new Composite();
        component.setType(ComponentType.SENTENCE);
        AtomicInteger expressionsCounter = new AtomicInteger();
        words.forEach(currentWord ->{
            //cleaning words from spaces to get clear words
            String clearWord = currentWord.replaceAll("\\s+", "");
            Leaf leaf = new Leaf(clearWord);
            if (EXPRESSION_PATTERN.matcher(currentWord).find()){
                expressionsCounter.getAndIncrement();
                leaf = new Leaf(clearWord.replaceAll("[\n\t\\s+]", ""));
                leaf.setType(ComponentType.EXPRESSION);
            }
            component.addComponent(leaf);
        });
        LOGGER.info("Sentence successfully parsed on words. "+expressionsCounter+" expressions was found.");
        return component;
    }

    public List<String> toWords(String text){
        return Arrays.asList(text.split(WORDS_SPLITTER));
    }
}
