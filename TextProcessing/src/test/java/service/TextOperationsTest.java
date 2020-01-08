package service;

import entity.Component;
import org.junit.Assert;
import org.junit.Test;
import representation.TextBuilder;

public class TextOperationsTest {
    private static final String SORT_BY_SENT_NUM_SOURCE = "A simple paragraph. With 2 sentences." +
            "\t But now this is a simple paragraph with 1 sentence.";
    private static final String SORT_BY_SENT_NUM_CORRECT_RESULT = " But now this is a simple paragraph with 1 sentence \n" +
            "\tA simple paragraph With 2 sentences \n" +
            "\t";
    private static final String SORT_BY_WORD_LENGTH_SOURCE = "Very simple sentence to test function.";
    private static final String SORT_BY_WORD_LENGTH_CORRECT_RESULT = "to Very test simple sentence function. \n" +
            "\t";
    private static final String SORT_BY_SYMBOL_ENTRANCE_SOURCE ="a It's aaa been a long day without you,  aa my friend";
    private static final String SORT_BY_SYMBOL_ENTRANCE_CORRECT_RESULT ="aaa aa a a day  been friend It's long my without you. \n" +
            "\t";
    private final TextOperations sorter = new TextOperations();

    @Test
    public void sortParagraphsByNumOFSentences() {
        Component result = ChainBuilder.buildChain().parse(SORT_BY_SENT_NUM_SOURCE);
        result = sorter.sortParagraphsByNumberOfSentences(result);
        TextBuilder builder = new TextBuilder();
        Assert.assertEquals(SORT_BY_SENT_NUM_CORRECT_RESULT, builder.represent(result));
    }

    @Test
    public void sortByLengthOfWordsText() {
        Component result = ChainBuilder.buildChain().parse(SORT_BY_WORD_LENGTH_SOURCE);
        result = sorter.sortSentencesByWordLength(result);
        TextBuilder builder = new TextBuilder();
        Assert.assertEquals(SORT_BY_WORD_LENGTH_CORRECT_RESULT, builder.represent(result));
    }

    @Test
    public void reverseSortLexemesByOrderSymbol() {
        Component result = ChainBuilder.buildChain().parse(SORT_BY_SYMBOL_ENTRANCE_SOURCE);
        result = sorter.sortWordsBySymbolEntranceInDescendingOrder(result, 'a');
        TextBuilder builder = new TextBuilder();
        Assert.assertEquals(SORT_BY_SYMBOL_ENTRANCE_CORRECT_RESULT, builder.represent(result));
    }
}