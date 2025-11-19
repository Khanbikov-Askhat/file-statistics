package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;

import java.util.Map;

/**
 * Интерфейс для форматирования статистики по файлам в различные форматы вывода.
 * Определяет контракт для всех реализаций форматтеров.
 */
public interface OutputFormatter {

    /**
     * Преобразует статистику по файлам в строку заданного формата.
     *
     * @param statistics карта статистики, где ключ - расширение файла,
     *                   значение - соответствующая статистика
     * @return отформатированная строка со статистикой
     */
    String format(Map<String, FileStatistics> statistics);
}
