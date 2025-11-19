package com.rtk.filestatistics.infrastructure.filter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Композитный фильтр файлов, объединяющий несколько фильтров.
 */
public class CompositeFileFilter implements FileFilter {

    /**
     * Тип композиции для объединения фильтров.
     */
    public enum CompositionType {
        /** Все фильтры должны вернуть true */
        AND,
        /** Хотя бы один фильтр должен вернуть true */
        OR
    }

    private final List<FileFilter> filters = new ArrayList<>();
    private final CompositionType compositionType;


    public CompositeFileFilter(CompositionType compositionType) {
        this.compositionType = compositionType;
    }

    public void addFilter(FileFilter filter) {
        if (filter != null) {
            filters.add(filter);
        }
    }

    /**
     * Определяет, должен ли файл быть обработан.
     *
     * @param path путь к файлу
     * @return true если файл должен быть ИСКЛЮЧЕН из обработки (отфильтрован)
     */

    @Override
    public boolean shouldProcess(Path path) {
        if (filters.isEmpty()) {
            return true;
        }

        if (compositionType == CompositionType.AND) {
            return filters.stream().allMatch(f -> f.shouldProcess(path));
        } else {
            return filters.stream().anyMatch(f -> f.shouldProcess(path));
        }
    }
}
