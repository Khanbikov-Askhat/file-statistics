package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;

import java.util.Map;

/**
 * Форматтер для вывода статистики в простом текстовом формате.
 * Создает человеко-читаемое представление статистики.
 */
public class PlainTextFormatter implements OutputFormatter {

    /**
     * Форматирует статистику по файлам в простой текстовый формат.
     *
     * @param statistics карта статистики по расширениям файлов
     * @return отформатированная текстовая строка или сообщение о отсутствии статистики
     */
    @Override
    public String format(Map<String, FileStatistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return "No statistics available.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("File Statistics:\n");
        sb.append("===============\n\n");

        for (Map.Entry<String, FileStatistics> entry : statistics.entrySet()) {
            String extension = entry.getKey();
            FileStatistics stats = entry.getValue();

            sb.append("Extension: .").append(extension).append("\n");
            sb.append("  Files: ").append(stats.getFileCount()).append("\n");
            sb.append("  Total Size: ").append(stats.getTotalSize()).append(" bytes\n");
            sb.append("  Total Lines: ").append(stats.getTotalLines()).append("\n");
            sb.append("  Non-empty Lines: ").append(stats.getNonEmptyLines()).append("\n");
            sb.append("  Comment Lines: ").append(stats.getCommentLines()).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }
}
