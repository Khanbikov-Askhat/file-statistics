package com.rtk.filestatistics.presentation;

import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.domain.OutputFormat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Парсер аргументов командной строки для приложения статистики файлов.
 * Обрабатывает аргументы и создает объект {@link Arguments} для настройки обработки.
 */
public class ArgumentParser {

    private static final String RECURSIVE_FLAG = "--recursive";
    private static final String MAX_DEPTH_FLAG = "--max-depth=";
    private static final String THREAD_FLAG = "--thread=";
    private static final String INCLUDE_EXT_FLAG = "--include-ext=";
    private static final String EXCLUDE_EXT_FLAG = "--exclude-ext=";
    private static final String GIT_IGNORE_FLAG = "--git-ignore";
    private static final String OUTPUT_FLAG = "--output=";

    /**
     * Парсит аргументы командной строки в объект Arguments.
     *
     * @param args аргументы командной строки
     * @return объект Arguments с настройками обработки
     * @throws IllegalArgumentException если аргументы невалидны
     */
    public Arguments parseArguments(String[] args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("Path argument is required");
        }

        Arguments.Builder builder = Arguments.builder();

        // Первый аргумент всегда путь к директории
        Path path = Paths.get(args[0]);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be an existing directory: " + args[0]);
        }
        builder.path(path);

        // Парсинг оставшихся аргументов
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];

            if (RECURSIVE_FLAG.equals(arg)) {
                builder.recursive(true);
            } else if (arg.startsWith(MAX_DEPTH_FLAG)) {
                int maxDepth = parseInteger(arg.substring(MAX_DEPTH_FLAG.length()), "max-depth");
                builder.maxDepth(maxDepth);
            } else if (arg.startsWith(THREAD_FLAG)) {
                int threadCount = parseInteger(arg.substring(THREAD_FLAG.length()), "thread");
                builder.threadCount(threadCount);
            } else if (arg.startsWith(INCLUDE_EXT_FLAG)) {
                Set<String> extensions = parseExtensions(arg.substring(INCLUDE_EXT_FLAG.length()));
                builder.includeExtensions(extensions);
            } else if (arg.startsWith(EXCLUDE_EXT_FLAG)) {
                Set<String> extensions = parseExtensions(arg.substring(EXCLUDE_EXT_FLAG.length()));
                builder.excludeExtensions(extensions);
            } else if (GIT_IGNORE_FLAG.equals(arg)) {
                builder.gitIgnore(true);
            } else if (arg.startsWith(OUTPUT_FLAG)) {
                String format = arg.substring(OUTPUT_FLAG.length());
                OutputFormat outputFormat = OutputFormat.fromString(format);
                builder.outputFormat(outputFormat);
            } else {
                throw new IllegalArgumentException("Unknown argument: " + arg);
            }
        }

        return builder.build();
    }

    /**
     * Парсит строковое значение в целое число.
     *
     * @param value строковое значение для парсинга
     * @param paramName имя параметра для сообщений об ошибках
     * @return целочисленное значение
     * @throws IllegalArgumentException если значение невалидно
     */
    private int parseInteger(String value, String paramName) {
        try {
            int result = Integer.parseInt(value);
            if (result <= 0) {
                throw new IllegalArgumentException(paramName + " must be positive");
            }
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid " + paramName + " value: " + value);
        }
    }

    /**
     * Парсит строку с расширениями файлов.
     *
     * @param value строка с расширениями, разделенными запятыми
     * @return множество расширений файлов
     */
    private Set<String> parseExtensions(String value) {
        if (value == null || value.trim().isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }
}
