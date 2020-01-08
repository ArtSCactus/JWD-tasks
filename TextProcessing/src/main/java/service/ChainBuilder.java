package service;

import parsers.ParagraphParser;
import parsers.Parser;
import parsers.SentenceParser;
import parsers.TextParser;

public class ChainBuilder {
    public static Parser buildChain() {
        return new TextParser(new ParagraphParser(new SentenceParser()));
    }
}
