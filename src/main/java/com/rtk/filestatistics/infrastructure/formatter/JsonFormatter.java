package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;

import java.util.Map;

/**
 * Форматтер для вывода статистики в формате JSON.
 * Преобразует статистику по файлам в структурированный JSON.
 */
public class JsonFormatter implements OutputFormatter {

    /**
     * Форматирует статистику по файлам в строку JSON.
     *
     * @param statistics карта статистики по расширениям файлов
     * @return строка в формате JSON или "{}" если статистика пуста
     */
    @Override
    public String format(Map<String, FileStatistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"statistics\": [\n");

        boolean first = true;
        for (Map.Entry<String, FileStatistics> entry : statistics.entrySet()) {
            if (!first) {
                sb.append(",\n");
            }
            first = false;

            String extension = entry.getKey();
            FileStatistics stats = entry.getValue();

            sb.append("    {\n");
            sb.append("      \"extension\": \"").append(escapeJson(extension)).append("\",\n");
            sb.append("      \"fileCount\": ").append(stats.getFileCount()).append(",\n");
            sb.append("      \"totalSize\": ").append(stats.getTotalSize()).append(",\n");
            sb.append("      \"totalLines\": ").append(stats.getTotalLines()).append(",\n");
            sb.append("      \"nonEmptyLines\": ").append(stats.getNonEmptyLines()).append(",\n");
            sb.append("      \"commentLines\": ").append(stats.getCommentLines()).append("\n");
            sb.append("    }");
        }

        sb.append("\n  ]\n}");
        return sb.toString();
    }

    /**
     * Экранирует специальные символы в строке для корректного представления в JSON.
     *
     * @param text исходная строка для экранирования
     * @return экранированная строка, готовая для вставки в JSON
     */
    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
