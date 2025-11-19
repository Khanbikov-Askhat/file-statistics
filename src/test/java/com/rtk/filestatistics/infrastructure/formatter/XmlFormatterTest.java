package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class XmlFormatterTest {

    @Test
    void testFormat() {
        Map<String, FileStatistics> statistics = new HashMap<>();
        FileStatistics stats = new FileStatistics("java");
        stats.incrementFileCount();
        statistics.put("java", stats);
        
        OutputFormatter formatter = new XmlFormatter();
        String result = formatter.format(statistics);
        
        assertNotNull(result);
        assertTrue(result.contains("<statistics>"));
        assertTrue(result.contains("java"));
    }
}

