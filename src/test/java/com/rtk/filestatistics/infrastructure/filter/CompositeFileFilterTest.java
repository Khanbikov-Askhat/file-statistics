package com.rtk.filestatistics.infrastructure.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositeFileFilterTest {

    @Test
    void testCompositeFileFilter_AND() {
        // Создаем фильтры:
        // - Включаем только java и xml файлы
        // - Исключаем class и jar файлы
        FileFilter includeFilter = new ExtensionFileFilter(Set.of("java", "xml"), true);
        FileFilter excludeFilter = new ExtensionFileFilter(Set.of("class", "jar"), false);

        CompositeFileFilter compositeFilter = new CompositeFileFilter(CompositeFileFilter.CompositionType.AND);
        compositeFilter.addFilter(includeFilter);
        compositeFilter.addFilter(excludeFilter);

        // java файл: соответствует включению, не соответствует исключению -> НЕ фильтровать
        assertFalse(compositeFilter.shouldProcess(Paths.get("main.java")));

        // class файл: не соответствует включению, соответствует исключению -> фильтровать
        assertTrue(compositeFilter.shouldProcess(Paths.get("main.class")));

        // txt файл: не соответствует включению, но и не соответствует исключению -> НЕ фильтровать
        assertFalse(compositeFilter.shouldProcess(Paths.get("main.txt")));
    }

    @Test
    void testCompositeFileFilter_OR() {
        // Создаем фильтры исключения:
        // - Исключаем class файлы
        // - Исключаем jar файлы
        FileFilter filter1 = new ExtensionFileFilter(Set.of("class"), false);
        FileFilter filter2 = new ExtensionFileFilter(Set.of("jar"), false);

        CompositeFileFilter compositeFilter = new CompositeFileFilter(CompositeFileFilter.CompositionType.OR);
        compositeFilter.addFilter(filter1);
        compositeFilter.addFilter(filter2);

        // class файлы: соответствуют первому фильтру -> фильтровать
        assertTrue(compositeFilter.shouldProcess(Paths.get("test.class")));

        // jar файлы: соответствуют второму фильтру -> фильтровать
        assertTrue(compositeFilter.shouldProcess(Paths.get("test.jar")));

        // java файлы: не соответствуют ни одному фильтру -> НЕ фильтровать
        assertFalse(compositeFilter.shouldProcess(Paths.get("test.java")));
    }

    @Test
    void testCompositeFileFilter_Empty() {
        // Пустой композитный фильтр не должен ничего фильтровать
        CompositeFileFilter compositeFilter = new CompositeFileFilter(CompositeFileFilter.CompositionType.AND);
        assertTrue(compositeFilter.shouldProcess(Paths.get("test.java")));
    }
}
