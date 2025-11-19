package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlainTextFormatterTest {

    @Test
    void testFormat() {
        Map<String, FileStatistics> statistics = new HashMap<>();
        FileStatistics stats = new FileStatistics("java");
        stats.incrementFileCount();
        stats.addToTotalSize(100);
        statistics.put("java", stats);
        
        OutputFormatter formatter = new PlainTextFormatter();
        String result = formatter.format(statistics);
        
        assertNotNull(result);
        assertTrue(result.contains("java"));
        assertTrue(result.contains("1"));
    }
}

