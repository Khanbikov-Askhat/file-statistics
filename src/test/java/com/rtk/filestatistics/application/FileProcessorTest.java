package com.rtk.filestatistics.application;

import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.domain.FileStatistics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

    @Test
    void testProcessSingleFile(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.write(testFile, List.of("line1", "line2"));
        
        Arguments args = Arguments.builder()
                .path(tempDir)
                .threadCount(1)
                .build();
        
        FileProcessor processor = new FileProcessor();
        Map<String, FileStatistics> statistics = processor.processDirectory(tempDir, args);
        
        assertNotNull(statistics);
        assertEquals(1, statistics.size());
        assertTrue(statistics.containsKey("txt"));
        assertEquals(1, statistics.get("txt").getFileCount());
    }

    @Test
    void testProcessMultipleFiles(@TempDir Path tempDir) throws IOException {
        Files.write(tempDir.resolve("file1.txt"), List.of("line1"));
        Files.write(tempDir.resolve("file2.txt"), List.of("line1", "line2"));
        Files.write(tempDir.resolve("file3.java"), List.of("public class Test {}"));
        
        Arguments args = Arguments.builder()
                .path(tempDir)
                .threadCount(2)
                .recursive(false)
                .maxDepth(1)
                .build();
        
        FileProcessor processor = new FileProcessor();
        Map<String, FileStatistics> statistics = processor.processDirectory(tempDir, args);
        
        assertNotNull(statistics);
        assertTrue(statistics.size() >= 2);
        if (statistics.containsKey("txt")) {
            assertEquals(2, statistics.get("txt").getFileCount());
        }
        if (statistics.containsKey("java")) {
            assertEquals(1, statistics.get("java").getFileCount());
        }
    }
}

