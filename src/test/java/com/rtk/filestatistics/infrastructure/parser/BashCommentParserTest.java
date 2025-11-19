package com.rtk.filestatistics.infrastructure.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BashCommentParserTest {

    @Test
    void testIsCommentLine_SingleLineComment() {
        CommentParser parser = new BashCommentParser();
        assertTrue(parser.isCommentLine("# This is a comment"));
        assertFalse(parser.isCommentLine("echo 'Hello'"));
    }

    @Test
    void testIsCommentLine_CommentWithWhitespace() {
        CommentParser parser = new BashCommentParser();
        assertTrue(parser.isCommentLine("  # This is a comment"));
        assertTrue(parser.isCommentLine("\t# This is a comment"));
    }

    @Test
    void testIsCommentLine_NotAComment() {
        CommentParser parser = new BashCommentParser();
        assertFalse(parser.isCommentLine("echo \"# not a comment\""));
        assertFalse(parser.isCommentLine("VAR=value # comment at end"));
    }

    @Test
    void testIsCommentLine_EmptyLine() {
        CommentParser parser = new BashCommentParser();
        assertFalse(parser.isCommentLine(""));
        assertFalse(parser.isCommentLine("   "));
    }
}

