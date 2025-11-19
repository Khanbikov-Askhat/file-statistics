package com.rtk.filestatistics.infrastructure.parser;

/**
 * Парсер комментариев по умолчанию.
 * Не распознает комментарии для неизвестных форматов файлов.
 */
public class DefaultCommentParser implements CommentParser {

    /**
     * Проверяет, является ли строка строкой комментария.
     * Для неизвестных форматов всегда возвращает false.
     *
     * @param line строка для проверки
     * @return всегда false
     */
    @Override
    public boolean isCommentLine(String line) {
        return false;
    }
}
