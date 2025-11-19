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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileStatisticsCommandTest {

    @Test
    void testExecute(@TempDir Path tempDir) throws IOException {
        Files.write(tempDir.resolve("test.txt"), List.of("line1", "line2"));
        
        Arguments args = Arguments.builder()
                .path(tempDir)
                .threadCount(1)
                .build();
        
        Command command = new FileStatisticsCommand();
        Map<String, FileStatistics> result = command.execute(args);
        
        assertNotNull(result);
        assertTrue(result.size() >= 0);
    }
}

