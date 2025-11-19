package com.rtk.filestatistics.application;

import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.domain.FileStatistics;

import java.util.Map;

/**
 * Интерфейс команды для выполнения операций с файлами.
 */
public interface Command {

    /**
     * Выполняет команду с заданными аргументами.
     *
     * @param arguments аргументы для выполнения команды
     * @return карта статистики по расширениям файлов
     */
    Map<String, FileStatistics> execute(Arguments arguments);
}
