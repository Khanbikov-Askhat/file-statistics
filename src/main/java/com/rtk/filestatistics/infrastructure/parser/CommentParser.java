package com.rtk.filestatistics.infrastructure.parser;

/**
 * Интерфейс парсера комментариев.
 */
public interface CommentParser {

    /**
     * Проверяет, является ли строка строкой комментария.
     *
     * @param line строка для проверки
     * @return true если строка является комментарием
     */
    boolean isCommentLine(String line);
}
