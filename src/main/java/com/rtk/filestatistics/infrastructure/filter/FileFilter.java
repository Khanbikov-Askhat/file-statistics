package com.rtk.filestatistics.infrastructure.filter;

import java.nio.file.Path;

/**
 * Интерфейс фильтра файлов.
 */
public interface FileFilter {

    /**
     * Определяет, должен ли файл быть обработан.
     *
     * @param path путь к файлу
     * @return true если файл должен быть ИСКЛЮЧЕН из обработки (отфильтрован)
     */
    boolean shouldProcess(Path path);
}
