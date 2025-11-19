package com.rtk.filestatistics.infrastructure.filter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Фильтр файлов на основе .gitignore.
 */
public class GitIgnoreFileFilter implements FileFilter {

    private final Path rootPath;
    private final List<Pattern> ignorePatterns = new ArrayList<>();
    private final List<Pattern> negatePatterns = new ArrayList<>();

    /**
     * Создает новый GitIgnoreFileFilter.
     *
     * @param rootPath корневая директория, где расположен .gitignore
     */
    public GitIgnoreFileFilter(Path rootPath) {
        this.rootPath = rootPath;
        loadGitIgnore();
    }

    /**
     * Загружает и парсит .gitignore файл.
     */
    private void loadGitIgnore() {
        Path gitIgnoreFile = rootPath.resolve(".gitignore");
        if (!Files.exists(gitIgnoreFile)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(gitIgnoreFile);
            for (String line : lines) {
                String trimmed = line.trim();

                // Пропускаем пустые строки и комментарии
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                // Обрабатываем отрицание
                if (trimmed.startsWith("!")) {
                    String pattern = trimmed.substring(1);
                    negatePatterns.add(compilePattern(pattern));
                } else {
                    ignorePatterns.add(compilePattern(trimmed));
                }
            }
        } catch (IOException e) {
            // Если не можем прочитать .gitignore, не фильтруем ничего
        }
    }

    /**
     * Компилирует шаблон .gitignore в регулярное выражение.
     */
    private Pattern compilePattern(String pattern) {
        // Конвертируем .gitignore шаблон в regex
        String regex = pattern
                .replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".");

        // Обрабатываем шаблоны директорий (оканчивающиеся на /)
        if (pattern.endsWith("/")) {
            regex = regex.substring(0, regex.length() - 2) + ".*";
        }

        return Pattern.compile(regex);
    }

    /**
     * Определяет, должен ли файл быть обработан на основе .gitignore.
     *
     * @param path путь к файлу
     * @return true если файл должен быть ИСКЛЮЧЕН из обработки (игнорируется)
     */
    @Override
    public boolean shouldProcess(Path path) {
        if (ignorePatterns.isEmpty()) {
            return false; // Нет .gitignore или пустой, не фильтруем
        }

        String relativePath = rootPath.relativize(path).toString()
                .replace("\\", "/");

        // Сначала проверяем отрицающие шаблоны
        for (Pattern negatePattern : negatePatterns) {
            if (negatePattern.matcher(relativePath).matches()) {
                return false; // Явно не игнорируется
            }
        }

        // Проверяем игнорирующие шаблоны
        for (Pattern ignorePattern : ignorePatterns) {
            if (ignorePattern.matcher(relativePath).matches()) {
                return true; // Файл игнорируется
            }
        }

        return false; // Не игнорируется
    }
}
