package com.rtk.filestatistics.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutputFormatTest {

    @Test
    void testOutputFormatFromString() {
        assertEquals(OutputFormat.PLAIN, OutputFormat.fromString("plain"));
        assertEquals(OutputFormat.XML, OutputFormat.fromString("xml"));
        assertEquals(OutputFormat.JSON, OutputFormat.fromString("json"));
    }

    @Test
    void testOutputFormatFromString_CaseInsensitive() {
        assertEquals(OutputFormat.PLAIN, OutputFormat.fromString("PLAIN"));
        assertEquals(OutputFormat.XML, OutputFormat.fromString("Xml"));
        assertEquals(OutputFormat.JSON, OutputFormat.fromString("JSON"));
    }

    @Test
    void testOutputFormatFromString_InvalidValue() {
        assertEquals(OutputFormat.PLAIN, OutputFormat.fromString("invalid"));
    }

    @Test
    void testOutputFormatFromString_NullValue() {
        assertEquals(OutputFormat.PLAIN, OutputFormat.fromString(null));
    }
}

