package com.rtk.filestatistics.infrastructure.parser;

/**
 * Парсер комментариев для Java файлов.
 */
public class JavaCommentParser implements CommentParser {

    /**
     * Проверяет, является ли строка строкой комментария Java.
     *
     * @param line строка для проверки
     * @return true если строка начинается с "//"
     */
    @Override
    public boolean isCommentLine(String line) {
        if (line == null || line.isEmpty()) {
            return false;
        }
        String trimmed = line.trim();
        return trimmed.startsWith("//");
    }
}
