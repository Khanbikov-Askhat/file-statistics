package com.rtk.filestatistics.infrastructure.collector;

import com.rtk.filestatistics.domain.FileStatistics;
import com.rtk.filestatistics.infrastructure.parser.CommentParser;
import com.rtk.filestatistics.infrastructure.parser.CommentParserFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Абстрактный базовый класс для сборщиков статистики файлов.
 * Реализует основную логику сбора статистики.
 */
public class AbstractFileStatisticsCollector implements FileStatisticsCollector {

    /**
     * Собирает статистику по указанному файлу.
     *
     * @param filePath путь к файлу
     * @return статистика файла или null, если файл невалиден
     */
    @Override
    public FileStatistics collectStatistics(Path filePath) {
        if (filePath == null || !Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            return null;
        }

        String extension = getExtension(filePath);
        FileStatistics stats = new FileStatistics(extension);

        try {
            stats.incrementFileCount();
            long fileSize = Files.size(filePath);
            stats.addToTotalSize(fileSize);

            processFileLines(filePath, stats);
        } catch (IOException e) {
            return null;
        }

        return stats;
    }

    /**
     * Обрабатывает строки файла для сбора статистики.
     *
     * @param filePath путь к файлу
     * @param stats объект статистики для обновления
     * @throws IOException если произошла ошибка чтения файла
     */
    protected void processFileLines(Path filePath, FileStatistics stats) throws IOException {
        String extension = getExtension(filePath);
        CommentParser parser = CommentParserFactory.getParser(extension);

        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            stats.incrementTotalLines();

            if (isNonEmptyLine(line)) {
                stats.incrementNonEmptyLines();
            }

            if (parser.isCommentLine(line)) {
                stats.incrementCommentLines();
            }
        }
    }

    /**
     * Извлекает расширение файла из пути.
     *
     * @param filePath путь к файлу
     * @return расширение файла или пустая строка, если расширения нет
     */
    protected String getExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0 && lastDot < fileName.length() - 1) {
            return fileName.substring(lastDot + 1);
        }
        return "";
    }

    /**
     * Проверяет, является ли строка непустой.
     *
     * @param line строка для проверки
     * @return true если строка не пустая после trim()
     */
    protected boolean isNonEmptyLine(String line) {
        if (line == null) {
            return false;
        }
        return !line.trim().isEmpty();
    }
}
