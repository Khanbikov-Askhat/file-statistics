package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;
import com.rtk.filestatistics.domain.OutputFormat;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutputFormatterContextTest {

    @Test
    void testGetFormatter_Plain() {
        OutputFormatterContext context = new OutputFormatterContext();
        OutputFormatter formatter = context.getFormatter(OutputFormat.PLAIN);
        assertTrue(formatter instanceof PlainTextFormatter);
    }

    @Test
    void testGetFormatter_Xml() {
        OutputFormatterContext context = new OutputFormatterContext();
        OutputFormatter formatter = context.getFormatter(OutputFormat.XML);
        assertTrue(formatter instanceof XmlFormatter);
    }

    @Test
    void testGetFormatter_Json() {
        OutputFormatterContext context = new OutputFormatterContext();
        OutputFormatter formatter = context.getFormatter(OutputFormat.JSON);
        assertTrue(formatter instanceof JsonFormatter);
    }

    @Test
    void testFormat() {
        Map<String, FileStatistics> statistics = new HashMap<>();
        FileStatistics stats = new FileStatistics("java");
        stats.incrementFileCount();
        statistics.put("java", stats);
        
        OutputFormatterContext context = new OutputFormatterContext();
        String result = context.format(statistics, OutputFormat.PLAIN);
        
        assertNotNull(result);
        assertTrue(result.contains("java"));
    }
}

