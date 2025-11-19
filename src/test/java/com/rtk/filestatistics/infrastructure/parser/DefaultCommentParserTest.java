package com.rtk.filestatistics.infrastructure.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DefaultCommentParserTest {

    @Test
    void testIsCommentLine_AlwaysReturnsFalse() {
        CommentParser parser = new DefaultCommentParser();
        assertFalse(parser.isCommentLine("any line"));
        assertFalse(parser.isCommentLine("// looks like comment"));
        assertFalse(parser.isCommentLine("# looks like comment"));
        assertFalse(parser.isCommentLine(""));
        assertFalse(parser.isCommentLine(null));
    }
}

