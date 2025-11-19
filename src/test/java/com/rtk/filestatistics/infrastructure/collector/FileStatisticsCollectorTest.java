package com.rtk.filestatistics.infrastructure.collector;

import com.rtk.filestatistics.domain.FileStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileStatisticsCollectorTest {

    @Test
    void testCollectStatistics(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.java");
        Files.write(testFile, List.of("public class Test {}", "// comment"));
        
        FileStatisticsCollector collector = new DefaultFileStatisticsCollector();
        FileStatistics stats = collector.collectStatistics(testFile);
        
        assertNotNull(stats);
        assertEquals("java", stats.getExtension());
    }

    @Test
    void testCollectStatistics_FileSize(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.write(testFile, List.of("line1", "line2", "line3"));
        
        FileStatisticsCollector collector = new DefaultFileStatisticsCollector();
        FileStatistics stats = collector.collectStatistics(testFile);
        
        assertNotNull(stats);
        assertTrue(stats.getTotalSize() > 0);
    }

    @Test
    void testCollectStatistics_LineCounting(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.java");
        Files.write(testFile, List.of(
                "public class Test {",
                "",
                "    // comment",
                "    private int value;",
                "}"
        ));
        
        FileStatisticsCollector collector = new DefaultFileStatisticsCollector();
        FileStatistics stats = collector.collectStatistics(testFile);
        
        assertNotNull(stats);
        assertEquals(5, stats.getTotalLines());
        assertEquals(4, stats.getNonEmptyLines());
        assertEquals(1, stats.getCommentLines());
    }

    @Test
    void testCollectStatistics_CommentLines(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.java");
        Files.write(testFile, List.of(
                "// comment 1",
                "public class Test {}",
                "  // comment 2"
        ));
        
        FileStatisticsCollector collector = new DefaultFileStatisticsCollector();
        FileStatistics stats = collector.collectStatistics(testFile);
        
        assertNotNull(stats);
        assertEquals(2, stats.getCommentLines());
    }

    @Test
    void testCollectStatistics_EmptyFile(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("empty.txt");
        Files.createFile(testFile);
        
        FileStatisticsCollector collector = new DefaultFileStatisticsCollector();
        FileStatistics stats = collector.collectStatistics(testFile);
        
        assertNotNull(stats);
        assertEquals(0, stats.getTotalLines());
        assertEquals(0, stats.getTotalSize());
    }

    @Test
    void testCollectStatistics_NonExistentFile() {
        FileStatisticsCollector collector = new DefaultFileStatisticsCollector();
        FileStatistics stats = collector.collectStatistics(java.nio.file.Paths.get("/nonexistent/file.txt"));
        
        assertNull(stats);
    }
}

