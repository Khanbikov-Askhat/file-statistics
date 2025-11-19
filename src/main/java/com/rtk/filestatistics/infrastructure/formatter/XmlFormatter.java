package com.rtk.filestatistics.infrastructure.formatter;

import com.rtk.filestatistics.domain.FileStatistics;

import java.util.Map;

/**
 * Форматтер для вывода статистики в формате XML.
 * Создает структурированный XML документ со статистикой.
 */
public class XmlFormatter implements OutputFormatter {

    /**
     * Форматирует статистику по файлам в XML строку.
     *
     * @param statistics карта статистики по расширениям файлов
     * @return строка в формате XML или пустой XML документ если статистика пуста
     */
    @Override
    public String format(Map<String, FileStatistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<statistics/>";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<statistics>\n");

        for (Map.Entry<String, FileStatistics> entry : statistics.entrySet()) {
            String extension = entry.getKey();
            FileStatistics stats = entry.getValue();

            sb.append("  <extension name=\"").append(escapeXml(extension)).append("\">\n");
            sb.append("    <files>").append(stats.getFileCount()).append("</files>\n");
            sb.append("    <totalSize>").append(stats.getTotalSize()).append("</totalSize>\n");
            sb.append("    <totalLines>").append(stats.getTotalLines()).append("</totalLines>\n");
            sb.append("    <nonEmptyLines>").append(stats.getNonEmptyLines()).append("</nonEmptyLines>\n");
            sb.append("    <commentLines>").append(stats.getCommentLines()).append("</commentLines>\n");
            sb.append("  </extension>\n");
        }

        sb.append("</statistics>");
        return sb.toString();
    }

    /**
     * Экранирует специальные XML символы в строке.
     *
     * @param text исходная строка для экранирования
     * @return экранированная строка, безопасная для вставки в XML
     */
    private String escapeXml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
