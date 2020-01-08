package service;
import entity.Component;
import entity.ComponentType;
import entity.Composite;
import entity.Leaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TextOperations {
    private static final Logger LOGGER = LogManager.getLogger(TextOperations.class);

    public Component sortParagraphsByNumberOfSentences(Component text) {
        if (text == null) {
            LOGGER.error("A null component was met.");
            throw new NullPointerException("Text component cannot be null");
        }
        if (text.getType() != ComponentType.TEXT) {
            LOGGER.error("Incoming component is not text. Cannot sort by paragraphs");
            throw new IllegalArgumentException("Component that is not TEXT type cannot be sorted by paragraphs");
        }
        Composite sortedComponent = new Composite(ComponentType.TEXT);

        List<Component> paragraphs = new ArrayList<>();
        for (int i = 0; i < text.getChildNodes().size(); i++) {
            paragraphs.add((Component) text.getChild(i));
        }
        paragraphs = paragraphs.stream().sorted(Comparator.comparingInt(Component::getChildNodeListSize))
                .collect(Collectors.toList());
        for (Component paragraph : paragraphs) {
            sortedComponent.addComponent(paragraph);
        }

        LOGGER.info("Paragraphs was sorted by number of sentences");

        return sortedComponent;

    }

    public Component sortSentencesByWordLength(Component text) {
        Component result = new Composite(ComponentType.TEXT);
        
        if (text == null) {
            LOGGER.error("A null component was met.");
            throw new NullPointerException("Text component cannot be null");
        }
        if (text.getType() != ComponentType.TEXT) {
            LOGGER.error("Incoming component is not text. Cannot sort by word length");
            throw new IllegalArgumentException("Component that is not TEXT type cannot be sorted by word length");
        }

        for (int paragraphIndex = 0; paragraphIndex < text.getChildNodeListSize(); paragraphIndex++) {
            Composite paragraph = (Composite) text.getChild(paragraphIndex);
            Composite resultParagraph = new Composite(ComponentType.PARAGRAPH);

            for (int sentenceIndex = 0; sentenceIndex < paragraph.getChildNodeListSize(); sentenceIndex++) {
                Composite sentence = (Composite) paragraph.getChild(paragraphIndex);
                Composite resultSentence = new Composite(ComponentType.SENTENCE);
                List<Leaf> allWords = new ArrayList<>();

                for (int wordIndex = 0; wordIndex < sentence.getChildNodeListSize(); wordIndex++) {
                    Leaf lexeme = (Leaf) sentence.getChild(wordIndex);
                    clearLeaf(lexeme);
                    allWords.add(lexeme);
                }

                allWords.sort(Comparator.comparingInt(o -> o.getValue().
                        trim().
                        length()));
                int sizeOfAllLexemes = allWords.size();
                Leaf endOfSentenceLexeme = allWords.get(sizeOfAllLexemes - 1);
                String endOfSentenceString = endOfSentenceLexeme.getValue();
                endOfSentenceLexeme.setValue(endOfSentenceString + ".");
                for (Leaf lexeme : allWords) {
                    resultSentence.addComponent(lexeme);
                }
                resultParagraph.addComponent(resultSentence);
            }
            result.addComponent(resultParagraph);
        }

        LOGGER.info("Sentences was successfully sorted by length of tokens");
        return result;

    }

    public Component sortWordsBySymbolEntranceInDescendingOrder(Component text, char searchSymbol) {
        Composite result = new Composite(ComponentType.TEXT);

        if (text == null) {
            LOGGER.error("A null component was met.");
            throw new NullPointerException("Text component cannot be null");
        }
        if (text.getType() != ComponentType.TEXT) {
            LOGGER.error("Incoming component is not text. Cannot sort by symbol order");
            throw new IllegalArgumentException("Component that is not TEXT type cannot be sorted by symbol order");
        }


        for (int paragraphIndex = 0; paragraphIndex < text.getChildNodeListSize(); paragraphIndex++) {
            Component paragraph = (Composite) text.getChild(paragraphIndex);
            Composite resultParagraph = new Composite(ComponentType.PARAGRAPH);

            for (int sentenceIndex = 0; sentenceIndex < paragraph.getChildNodeListSize(); sentenceIndex++) {
                Component sentence = (Composite)paragraph.getChild(sentenceIndex);
                Composite resultSentence = new Composite(ComponentType.SENTENCE);

                List<Leaf> words = new ArrayList<>();
                for (int wordIndex = 0; wordIndex < sentence.getChildNodeListSize(); wordIndex++) {
                    Leaf currentWord = (Leaf) sentence.getChild(wordIndex);
                    clearLeaf(currentWord);
                    words.add(currentWord);
                }
                words.sort(Comparator.comparing(o -> ((Leaf) o).countAmountOfEntries(searchSymbol))
                        .thenComparing((e1, e2) -> ((Leaf) e2).getValue()
                                .compareToIgnoreCase(((Leaf) e1).getValue())));
                Collections.reverse(words);

                int sizeOfAllLexemes = words.size();
                String endOfSentence = words.get(sizeOfAllLexemes - 1).getValue();
                words.get(sizeOfAllLexemes - 1).setValue(endOfSentence + ".");
                for (Component lexeme : words) {
                    resultSentence.addComponent(lexeme);
                }
                resultParagraph.addComponent(resultSentence);
            }

            result.addComponent(resultParagraph);
        }

        LOGGER.info("Successfully sorted by amount of entries.");

        return result;
    }

    /**Removes all dots and spaces from given leaf.
     *
     * @param word
     */
    private void clearLeaf(Leaf word) {
        if (word.getValue().equals("  "))
            word.getValue().replace("  ", " ");
        if (word.getValue().contains("\n")) {
            word = new Leaf(word.getValue().replace("\n", " "));
        }
    }


}













