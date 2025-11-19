package com.rtk.filestatistics.infrastructure.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaCommentParserTest {

    @Test
    void testIsCommentLine_SingleLineComment() {
        CommentParser parser = new JavaCommentParser();
        assertTrue(parser.isCommentLine("// This is a comment"));
        assertFalse(parser.isCommentLine("public class Test {}"));
    }

    @Test
    void testIsCommentLine_CommentWithWhitespace() {
        CommentParser parser = new JavaCommentParser();
        assertTrue(parser.isCommentLine("  // This is a comment"));
        assertTrue(parser.isCommentLine("\t// This is a comment"));
    }

    @Test
    void testIsCommentLine_NotAComment() {
        CommentParser parser = new JavaCommentParser();
        assertFalse(parser.isCommentLine("String s = \"// not a comment\";"));
        assertFalse(parser.isCommentLine("code(); // comment at end"));
    }

    @Test
    void testIsCommentLine_EmptyLine() {
        CommentParser parser = new JavaCommentParser();
        assertFalse(parser.isCommentLine(""));
        assertFalse(parser.isCommentLine("   "));
    }
}

