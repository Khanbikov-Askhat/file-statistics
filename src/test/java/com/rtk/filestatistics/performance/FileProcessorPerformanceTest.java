package com.rtk.filestatistics.performance;

import com.rtk.filestatistics.application.FileProcessor;
import com.rtk.filestatistics.domain.Arguments;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileProcessorPerformanceTest {

    @Test
    void testMultithreadedPerformance(@TempDir Path tempDir) throws IOException {
        // Создаем несколько тестовых файлов
        for (int i = 0; i < 10; i++) {
            Files.write(tempDir.resolve("test" + i + ".java"),
                    List.of("public class Test" + i + " {}", "// comment " + i));
        }

        Arguments args = Arguments.builder()
                .path(tempDir)
                .threadCount(4)
                .build();

        FileProcessor processor = new FileProcessor();
        long startTime = System.currentTimeMillis();
        Map<String, com.rtk.filestatistics.domain.FileStatistics> statistics = processor.processDirectory(tempDir, args);
        long endTime = System.currentTimeMillis();

        assertNotNull(statistics);
        System.out.println("Время многопоточной обработки: " + (endTime - startTime) + " мс");
    }

    @Test
    void testSingleThreadedPerformance(@TempDir Path tempDir) throws IOException {
        // Создаем несколько тестовых файлов
        for (int i = 0; i < 10; i++) {
            Files.write(tempDir.resolve("test" + i + ".java"),
                    List.of("public class Test" + i + " {}", "// comment " + i));
        }

        Arguments args = Arguments.builder()
                .path(tempDir)
                .threadCount(1)
                .build();

        FileProcessor processor = new FileProcessor();
        long startTime = System.currentTimeMillis();
        Map<String, com.rtk.filestatistics.domain.FileStatistics> statistics = processor.processDirectory(tempDir, args);
        long endTime = System.currentTimeMillis();

        assertNotNull(statistics);
        System.out.println("Время однопоточной обработки: " + (endTime - startTime) + " мс");
    }
}

