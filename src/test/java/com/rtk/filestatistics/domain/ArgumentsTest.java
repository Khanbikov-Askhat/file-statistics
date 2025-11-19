package com.rtk.filestatistics.domain;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {

    @Test
    void testArgumentsWithPath() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .build();
        assertEquals(Paths.get("/test"), args.getPath());
    }

    @Test
    void testArgumentsWithRecursive() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .recursive(true)
                .build();
        assertTrue(args.isRecursive());
    }

    @Test
    void testArgumentsWithMaxDepth() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .maxDepth(5)
                .build();
        assertEquals(5, args.getMaxDepth());
    }

    @Test
    void testArgumentsWithThreadCount() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .threadCount(4)
                .build();
        assertEquals(4, args.getThreadCount());
    }

    @Test
    void testArgumentsWithIncludeExtensions() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .includeExtensions(Set.of("java", "xml"))
                .build();
        assertEquals(Set.of("java", "xml"), args.getIncludeExtensions());
    }

    @Test
    void testArgumentsWithExcludeExtensions() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .excludeExtensions(Set.of("class", "jar"))
                .build();
        assertEquals(Set.of("class", "jar"), args.getExcludeExtensions());
    }

    @Test
    void testArgumentsWithGitIgnore() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .gitIgnore(true)
                .build();
        assertTrue(args.isGitIgnore());
    }

    @Test
    void testArgumentsWithOutputFormat() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .outputFormat(OutputFormat.JSON)
                .build();
        assertEquals(OutputFormat.JSON, args.getOutputFormat());
    }

    @Test
    void testArgumentsValidation_NullPath() {
        assertThrows(IllegalArgumentException.class, () ->
                Arguments.builder().build());
    }

    @Test
    void testArgumentsDefaultValues() {
        Arguments args = Arguments.builder()
                .path(Paths.get("/test"))
                .build();
        assertFalse(args.isRecursive());
        assertEquals(Integer.MAX_VALUE, args.getMaxDepth());
        assertEquals(1, args.getThreadCount());
        assertFalse(args.isGitIgnore());
        assertEquals(OutputFormat.PLAIN, args.getOutputFormat());
    }
}

