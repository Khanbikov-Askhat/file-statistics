package com.rtk.filestatistics.infrastructure.filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GitIgnoreFileFilterTest {

    @Test
    void testGitIgnoreBasicPatterns(@TempDir Path tempDir) throws IOException {
        Path gitIgnoreFile = tempDir.resolve(".gitignore");
        Files.write(gitIgnoreFile, List.of(
                "*.class",
                "*.jar",
                "target/",
                "# Comment line",
                ""
        ));
        
        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);
        
        assertTrue(filter.shouldProcess(tempDir.resolve("Test.class")));
        assertTrue(filter.shouldProcess(tempDir.resolve("app.jar")));
        assertTrue(filter.shouldProcess(tempDir.resolve("target/Main.class")));
        assertFalse(filter.shouldProcess(tempDir.resolve("Main.java")));
    }

    @Test
    void testGitIgnoreComplexPatterns(@TempDir Path tempDir) throws IOException {
        Path gitIgnoreFile = tempDir.resolve(".gitignore");
        Files.write(gitIgnoreFile, List.of(
                "build/",
                "*.tmp",
                "!important.tmp",
                "logs/*.log"
        ));
        
        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);
        
        assertTrue(filter.shouldProcess(tempDir.resolve("build/classes")));
        assertTrue(filter.shouldProcess(tempDir.resolve("temp.tmp")));
        assertFalse(filter.shouldProcess(tempDir.resolve("important.tmp")));
        assertTrue(filter.shouldProcess(tempDir.resolve("logs/app.log")));
    }

    @Test
    void testGitIgnore_NoGitIgnoreFile(@TempDir Path tempDir) {
        // No .gitignore file exists
        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);
        
        // Should not filter anything if .gitignore doesn't exist
        assertFalse(filter.shouldProcess(tempDir.resolve("test.java")));
    }
}

