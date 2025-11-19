package com.rtk.filestatistics.infrastructure.collector;

import com.rtk.filestatistics.domain.FileStatistics;

import java.nio.file.Path;

/**
 * Интерфейс для сборщиков статистики файлов.
 */
public interface FileStatisticsCollector {

    /**
     * Собирает статистику по указанному файлу.
     *
     * @param filePath путь к файлу
     * @return статистика файла или null, если файл невалиден
     */
    FileStatistics collectStatistics(Path filePath);
}
