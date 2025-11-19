package com.rtk.filestatistics.infrastructure.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionFileFilterTest {

    @Test
    void testExtensionFileFilterInclude() {
        // shouldProcess returns true if file should be FILTERED OUT
        Set<String> extensions = Set.of("java", "xml");
        FileFilter filter = new ExtensionFileFilter(extensions, true);
        
        // java and xml files should NOT be filtered (shouldProcess=false)
        assertFalse(filter.shouldProcess(Paths.get("test.java")));
        assertFalse(filter.shouldProcess(Paths.get("test.xml")));
        // txt files should be filtered (shouldProcess=true)
        assertTrue(filter.shouldProcess(Paths.get("test.txt")));
    }

    @Test
    void testExtensionFileFilterExclude() {
        // shouldProcess returns true if file should be FILTERED OUT
        Set<String> extensions = Set.of("class", "jar");
        FileFilter filter = new ExtensionFileFilter(extensions, false);
        
        // java files should NOT be filtered (shouldProcess=false)
        assertFalse(filter.shouldProcess(Paths.get("test.java")));
        // class and jar files should be filtered (shouldProcess=true)
        assertTrue(filter.shouldProcess(Paths.get("test.class")));
        assertTrue(filter.shouldProcess(Paths.get("test.jar")));
    }

    @Test
    void testExtensionFileFilter_NoExtension() {
        // shouldProcess returns true if file should be FILTERED OUT
        Set<String> extensions = Set.of("java");
        FileFilter filter = new ExtensionFileFilter(extensions, true);
        
        // Files without extension should be filtered (shouldProcess=true)
        assertTrue(filter.shouldProcess(Paths.get("test")));
        assertTrue(filter.shouldProcess(Paths.get("test.")));
    }

    @Test
    void testExtensionFileFilter_CaseInsensitive() {
        // shouldProcess returns true if file should be FILTERED OUT
        Set<String> extensions = Set.of("java");
        FileFilter filter = new ExtensionFileFilter(extensions, true);
        
        // java files (case insensitive) should NOT be filtered (shouldProcess=false)
        assertFalse(filter.shouldProcess(Paths.get("test.JAVA")));
        assertFalse(filter.shouldProcess(Paths.get("test.Java")));
    }
}

