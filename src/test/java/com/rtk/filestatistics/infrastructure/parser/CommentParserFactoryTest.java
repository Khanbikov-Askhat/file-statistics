package com.rtk.filestatistics.infrastructure.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommentParserFactoryTest {

    @Test
    void testGetParser_Java() {
        CommentParser parser = CommentParserFactory.getParser("java");
        assertTrue(parser instanceof JavaCommentParser);
    }

    @Test
    void testGetParser_Bash() {
        CommentParser parser = CommentParserFactory.getParser("sh");
        assertTrue(parser instanceof BashCommentParser);
    }

    @Test
    void testGetParser_BashScript() {
        CommentParser parser = CommentParserFactory.getParser("bash");
        assertTrue(parser instanceof BashCommentParser);
    }

    @Test
    void testGetParser_Unknown() {
        CommentParser parser = CommentParserFactory.getParser("unknown");
        assertTrue(parser instanceof DefaultCommentParser);
    }

    @Test
    void testGetParser_Null() {
        CommentParser parser = CommentParserFactory.getParser(null);
        assertTrue(parser instanceof DefaultCommentParser);
    }
}

