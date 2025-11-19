package com.rtk.filestatistics.infrastructure.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExtensionFileFilterTest {

    @Test
    void testExtensionFileFilterInclude() {
        // Фильтр включения: обрабатываем только java и xml файлы
        Set<String> extensions = Set.of("java", "xml");
        FileFilter filter = new ExtensionFileFilter(extensions, true);

        // java и xml файлы - НЕ фильтруем (должны обрабатываться)
        assertFalse(filter.shouldProcess(Paths.get("test.java")));
        assertFalse(filter.shouldProcess(Paths.get("test.xml")));

        // txt файлы - фильтруем (НЕ должны обрабатываться)
        assertTrue(filter.shouldProcess(Paths.get("test.txt")));
    }

    @Test
    void testExtensionFileFilterExclude() {
        // Фильтр исключения: НЕ обрабатываем class и jar файлы
        Set<String> extensions = Set.of("class", "jar");
        FileFilter filter = new ExtensionFileFilter(extensions, false);

        // java файлы - НЕ фильтруем (должны обрабатываться)
        assertFalse(filter.shouldProcess(Paths.get("test.java")));

        // class и jar файлы - фильтруем (НЕ должны обрабатываться)
        assertTrue(filter.shouldProcess(Paths.get("test.class")));
        assertTrue(filter.shouldProcess(Paths.get("test.jar")));
    }

    @Test
    void testExtensionFileFilter_NoExtension() {
        // Фильтр включения: обрабатываем только java файлы
        Set<String> extensions = Set.of("java");
        FileFilter filter = new ExtensionFileFilter(extensions, true);

        // Файлы без расширения - фильтруем (НЕ должны обрабатываться)
        assertTrue(filter.shouldProcess(Paths.get("test")));
        assertTrue(filter.shouldProcess(Paths.get("test.")));
    }

    @Test
    void testExtensionFileFilter_CaseInsensitive() {
        // Фильтр включения: обрабатываем только java файлы (без учета регистра)
        Set<String> extensions = Set.of("java");
        FileFilter filter = new ExtensionFileFilter(extensions, true);

        // java файлы в разном регистре - НЕ фильтруем (должны обрабатываться)
        assertFalse(filter.shouldProcess(Paths.get("test.JAVA")));
        assertFalse(filter.shouldProcess(Paths.get("test.Java")));
    }
}

