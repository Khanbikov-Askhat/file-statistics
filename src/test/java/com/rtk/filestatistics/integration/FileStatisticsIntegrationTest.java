package com.rtk.filestatistics.integration;

import com.rtk.filestatistics.application.FileStatisticsCommand;
import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.domain.FileStatistics;
import com.rtk.filestatistics.domain.OutputFormat;
import com.rtk.filestatistics.infrastructure.formatter.OutputFormatterContext;
import com.rtk.filestatistics.presentation.ArgumentParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileStatisticsIntegrationTest {

    @Test
    void testFullWorkflow_PlainText(@TempDir Path tempDir) throws IOException {
        Files.write(tempDir.resolve("test1.java"), List.of("public class Test {}", "// comment"));
        Files.write(tempDir.resolve("test2.java"), List.of("public class Test2 {}"));
        Files.write(tempDir.resolve("test.txt"), List.of("line1", "line2"));
        
        String[] args = {
                tempDir.toString(),
                "--recursive",
                "--output=plain"
        };
        
        ArgumentParser parser = new ArgumentParser();
        Arguments arguments = parser.parseArguments(args);
        
        FileStatisticsCommand command = new FileStatisticsCommand();
        Map<String, FileStatistics> statistics = command.execute(arguments);
        
        assertNotNull(statistics);
        assertTrue(statistics.size() >= 2);
        
        OutputFormatterContext formatter = new OutputFormatterContext();
        String output = formatter.format(statistics, OutputFormat.PLAIN);
        
        assertNotNull(output);
        assertTrue(output.contains("java"));
    }

    @Test
    void testFullWorkflow_WithFilters(@TempDir Path tempDir) throws IOException {
        Files.write(tempDir.resolve("test.java"), List.of("public class Test {}"));
        Files.write(tempDir.resolve("test.class"), List.of("binary"));
        Files.write(tempDir.resolve("test.txt"), List.of("line1"));
        
        Arguments args = Arguments.builder()
                .path(tempDir)
                .includeExtensions(Set.of("java", "txt"))
                .excludeExtensions(Set.of("class"))
                .threadCount(2)
                .build();
        
        FileStatisticsCommand command = new FileStatisticsCommand();
        Map<String, FileStatistics> statistics = command.execute(args);
        
        assertNotNull(statistics);
        assertTrue(statistics.containsKey("java") || statistics.containsKey("txt"));
    }

    @Test
    void testFullWorkflow_XmlOutput(@TempDir Path tempDir) throws IOException {
        Files.write(tempDir.resolve("test.java"), List.of("public class Test {}"));
        
        Arguments args = Arguments.builder()
                .path(tempDir)
                .outputFormat(OutputFormat.XML)
                .build();
        
        FileStatisticsCommand command = new FileStatisticsCommand();
        Map<String, FileStatistics> statistics = command.execute(args);
        
        OutputFormatterContext formatter = new OutputFormatterContext();
        String output = formatter.format(statistics, OutputFormat.XML);
        
        assertNotNull(output);
        assertTrue(output.contains("<statistics>"));
        assertTrue(output.contains("java"));
    }

    @Test
    void testFullWorkflow_JsonOutput(@TempDir Path tempDir) throws IOException {
        Files.write(tempDir.resolve("test.java"), List.of("public class Test {}"));
        
        Arguments args = Arguments.builder()
                .path(tempDir)
                .outputFormat(OutputFormat.JSON)
                .build();
        
        FileStatisticsCommand command = new FileStatisticsCommand();
        Map<String, FileStatistics> statistics = command.execute(args);
        
        OutputFormatterContext formatter = new OutputFormatterContext();
        String output = formatter.format(statistics, OutputFormat.JSON);
        
        assertNotNull(output);
        assertTrue(output.contains("\"statistics\""));
        assertTrue(output.contains("java"));
    }
}

