package com.rtk.filestatistics.application;

import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.domain.FileStatistics;

import java.util.Map;

public class FileStatisticsCommand implements Command {

    private final FileProcessor processor;

    public FileStatisticsCommand() {
        this.processor = new FileProcessor();
    }

    /**
     * Выполняет сбор статистики по файлам согласно аргументам.
     *
     * @param arguments аргументы обработки
     * @return карта статистики по расширениям файлов
     */
    @Override
    public Map<String, FileStatistics> execute(Arguments arguments) {
        return processor.processDirectory(arguments.getPath(), arguments);
    }
}
