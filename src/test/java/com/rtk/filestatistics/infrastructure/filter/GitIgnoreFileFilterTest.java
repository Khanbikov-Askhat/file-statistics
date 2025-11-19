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
        // Создаем .gitignore с основными правилами игнорирования
        Path gitIgnoreFile = tempDir.resolve(".gitignore");
        Files.write(gitIgnoreFile, List.of(
                "*.class",      // Игнорировать все class файлы
                "*.jar",        // Игнорировать все jar файлы
                "target/",      // Игнорировать всю директорию target
                "# Это комментарий",
                ""              // Пустая строка
        ));

        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);

        // Проверяем, что игнорируемые файлы фильтруются
        assertTrue(filter.shouldProcess(tempDir.resolve("Test.class")));
        assertTrue(filter.shouldProcess(tempDir.resolve("app.jar")));
        assertTrue(filter.shouldProcess(tempDir.resolve("target/Main.class")));

        // Проверяем, что обычные файлы НЕ фильтруются
        assertFalse(filter.shouldProcess(tempDir.resolve("Main.java")));
        assertFalse(filter.shouldProcess(tempDir.resolve("pom.xml")));
    }

    @Test
    void testGitIgnoreComplexPatterns(@TempDir Path tempDir) throws IOException {
        // Тестируем сложные шаблоны .gitignore
        Path gitIgnoreFile = tempDir.resolve(".gitignore");
        Files.write(gitIgnoreFile, List.of(
                "build/",       // Игнорировать директорию build
                "*.tmp",        // Игнорировать все tmp файлы
                "!important.tmp", // НЕ игнорировать important.tmp
                "logs/*.log"    // Игнорировать log файлы в logs
        ));

        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);

        // Директория build игнорируется
        assertTrue(filter.shouldProcess(tempDir.resolve("build/classes")));
        assertTrue(filter.shouldProcess(tempDir.resolve("build/lib.jar")));

        // Обычные tmp файлы игнорируются
        assertTrue(filter.shouldProcess(tempDir.resolve("temp.tmp")));
        assertTrue(filter.shouldProcess(tempDir.resolve("cache.tmp")));

        // Важный tmp файл НЕ игнорируется (исключение)
        assertFalse(filter.shouldProcess(tempDir.resolve("important.tmp")));

        // Log файлы в директории logs игнорируются
        assertTrue(filter.shouldProcess(tempDir.resolve("logs/app.log")));
        assertTrue(filter.shouldProcess(tempDir.resolve("logs/debug.log")));

        // Файлы вне logs НЕ игнорируются
        assertFalse(filter.shouldProcess(tempDir.resolve("app.log")));
    }

    @Test
    void testGitIgnore_NoGitIgnoreFile(@TempDir Path tempDir) {
        // Тестируем случай, когда .gitignore отсутствует
        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);

        // Без .gitignore никакие файлы не должны фильтроваться
        assertFalse(filter.shouldProcess(tempDir.resolve("test.java")));
        assertFalse(filter.shouldProcess(tempDir.resolve("Test.class")));
        assertFalse(filter.shouldProcess(tempDir.resolve("temp.tmp")));
    }

    @Test
    void testGitIgnore_EmptyGitIgnore(@TempDir Path tempDir) throws IOException {
        // Тестируем пустой .gitignore файл
        Path gitIgnoreFile = tempDir.resolve(".gitignore");
        Files.write(gitIgnoreFile, List.of());

        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);

        // Пустой .gitignore не должен ничего фильтровать
        assertFalse(filter.shouldProcess(tempDir.resolve("test.java")));
        assertFalse(filter.shouldProcess(tempDir.resolve("Test.class")));
    }

    @Test
    void testGitIgnore_CommentsAndEmptyLines(@TempDir Path tempDir) throws IOException {
        // Тестируем .gitignore с комментариями и пустыми строками
        Path gitIgnoreFile = tempDir.resolve(".gitignore");
        Files.write(gitIgnoreFile, List.of(
                "# Это комментарий",
                "",
                "*.tmp",
                "",
                "# Еще один комментарий",
                "temp/"
        ));

        GitIgnoreFileFilter filter = new GitIgnoreFileFilter(tempDir);

        // Проверяем, что комментарии и пустые строки игнорируются
        assertTrue(filter.shouldProcess(tempDir.resolve("file.tmp")));
        assertTrue(filter.shouldProcess(tempDir.resolve("temp/data.txt")));
        assertFalse(filter.shouldProcess(tempDir.resolve("test.java")));
    }
}

