package com.rtk.filestatistics.presentation;

import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.domain.OutputFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    private final ArgumentParser parser = new ArgumentParser();

    @Test
    void testParsePath(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString()};
        Arguments arguments = parser.parseArguments(args);
        assertEquals(tempDir, arguments.getPath());
    }

    @Test
    void testParseRecursive(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--recursive"};
        Arguments arguments = parser.parseArguments(args);
        assertTrue(arguments.isRecursive());
    }

    @Test
    void testParseMaxDepth(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--max-depth=5"};
        Arguments arguments = parser.parseArguments(args);
        assertEquals(5, arguments.getMaxDepth());
    }

    @Test
    void testParseThread(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--thread=4"};
        Arguments arguments = parser.parseArguments(args);
        assertEquals(4, arguments.getThreadCount());
    }

    @Test
    void testParseIncludeExt(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--include-ext=java,xml,js"};
        Arguments arguments = parser.parseArguments(args);
        assertEquals(Set.of("java", "xml", "js"), arguments.getIncludeExtensions());
    }

    @Test
    void testParseExcludeExt(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--exclude-ext=class,jar"};
        Arguments arguments = parser.parseArguments(args);
        assertEquals(Set.of("class", "jar"), arguments.getExcludeExtensions());
    }

    @Test
    void testParseGitIgnore(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--git-ignore"};
        Arguments arguments = parser.parseArguments(args);
        assertTrue(arguments.isGitIgnore());
    }

    @Test
    void testParseOutput(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--output=json"};
        Arguments arguments = parser.parseArguments(args);
        assertEquals(OutputFormat.JSON, arguments.getOutputFormat());
    }

    @Test
    void testParseMultipleArguments(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {
                tempDir.toString(),
                "--recursive",
                "--max-depth=3",
                "--thread=4",
                "--include-ext=java,xml",
                "--output=xml"
        };
        Arguments arguments = parser.parseArguments(args);
        
        assertTrue(arguments.isRecursive());
        assertEquals(3, arguments.getMaxDepth());
        assertEquals(4, arguments.getThreadCount());
        assertEquals(Set.of("java", "xml"), arguments.getIncludeExtensions());
        assertEquals(OutputFormat.XML, arguments.getOutputFormat());
    }

    @Test
    void testParseInvalidPath() {
        String[] args = {"/nonexistent/path/that/does/not/exist"};
        assertThrows(IllegalArgumentException.class, () -> parser.parseArguments(args));
    }

    @Test
    void testParseInvalidMaxDepth(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--max-depth=invalid"};
        assertThrows(IllegalArgumentException.class, () -> parser.parseArguments(args));
    }

    @Test
    void testParseInvalidThread(@TempDir Path tempDir) throws IOException {
        Files.createDirectories(tempDir);
        String[] args = {tempDir.toString(), "--thread=invalid"};
        assertThrows(IllegalArgumentException.class, () -> parser.parseArguments(args));
    }

    @Test
    void testParseEmptyArgs() {
        String[] args = {};
        assertThrows(IllegalArgumentException.class, () -> parser.parseArguments(args));
    }
}

