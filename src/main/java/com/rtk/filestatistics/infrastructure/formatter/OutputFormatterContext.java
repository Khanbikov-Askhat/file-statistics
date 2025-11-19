package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;
import com.rtk.filestatistics.domain.OutputFormat;

import java.util.Map;

/**
 * Контекст для выбора и использования подходящего форматтера вывода.
 * Реализует паттерн Стратегия для динамического выбора формата вывода.
 */
public class OutputFormatterContext {

    public OutputFormatter getFormatter(OutputFormat format) {
        switch (format) {
            case XML:
                return new XmlFormatter();
            case JSON:
                return new JsonFormatter();
            case PLAIN:
            default:
                return new PlainTextFormatter();
        }
    }

    /**
     * Форматирует статистику с использованием указанного формата вывода.
     *
     * @param statistics статистика для форматирования
     * @param format формат вывода (PLAIN, XML, JSON)
     * @return отформатированная строка со статистикой
     */
    public String format(Map<String, FileStatistics> statistics, OutputFormat format) {
        OutputFormatter formatter = getFormatter(format);
        return formatter.format(statistics);
    }
}
