package com.rtk.filestatistics.infrastructure.filter;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Set;

/**
 * Фильтр файлов по расширению.
 */
public class ExtensionFileFilter implements FileFilter {

    private final Set<String> extensions;
    private final boolean include;

    public ExtensionFileFilter(Set<String> extensions, boolean include) {
        this.extensions = extensions;
        this.include = include;
    }

    /**
     * Определяет, должен ли файл быть обработан.
     *
     * @param path путь к файлу
     * @return true если файл должен быть ИСКЛЮЧЕН из обработки
     */
    @Override
    public boolean shouldProcess(Path path) {
        // shouldProcess returns true if file should be FILTERED OUT (ignored)
        String extension = getExtension(path);
        boolean matches = extensions.contains(extension.toLowerCase(Locale.ROOT));
        // If include=true: filter out files that DON'T match (return !matches)
        // If include=false: filter out files that DO match (return matches)
        return include ? !matches : matches;
    }

    /**
     * Извлекает расширение файла из пути.
     */
    private String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0 && lastDot < fileName.length() - 1) {
            return fileName.substring(lastDot + 1);
        }
        return "";
    }
}