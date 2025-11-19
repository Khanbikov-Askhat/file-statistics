package com.rtk.filestatistics.infrastructure.parser;

import java.util.Locale;

/**
 * Фабрика для создания парсеров комментариев по расширению файла.
 */
public class CommentParserFactory {

    /**
     * Возвращает парсер комментариев для указанного расширения файла.
     *
     * @param extension расширение файла
     * @return соответствующий парсер комментариев
     */
    public static CommentParser getParser(String extension) {
        if (extension == null) {
            return new DefaultCommentParser();
        }

        String ext = extension.toLowerCase(Locale.ROOT);

        switch (ext) {
            case "java":
                return new JavaCommentParser();
            case "sh":
            case "bash":
                return new BashCommentParser();
            default:
                return new DefaultCommentParser();
        }
    }
}
