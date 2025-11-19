package com.rtk.filestatistics.application;

import com.rtk.filestatistics.domain.Arguments;
import com.rtk.filestatistics.domain.FileStatistics;
import com.rtk.filestatistics.infrastructure.collector.DefaultFileStatisticsCollector;
import com.rtk.filestatistics.infrastructure.collector.FileStatisticsCollector;
import com.rtk.filestatistics.infrastructure.filter.CompositeFileFilter;
import com.rtk.filestatistics.infrastructure.filter.ExtensionFileFilter;
import com.rtk.filestatistics.infrastructure.filter.FileFilter;
import com.rtk.filestatistics.infrastructure.filter.GitIgnoreFileFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Основной класс для обработки файлов и сбора статистики.
 * Поддерживает однопоточную и многопоточную обработку.
 */
public class FileProcessor {
    private final FileStatisticsCollector collector;
    private final List<FileProcessingObserver> observers = new ArrayList<>();

    public FileProcessor() {
        this.collector = new DefaultFileStatisticsCollector();
    }

    /**
     * Добавляет наблюдателя для отслеживания прогресса.
     *
     * @param observer наблюдатель для добавления
     */
    public void addObserver(FileProcessingObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    /**
     * Обрабатывает директорию и собирает статистику по файлам.
     *
     * @param directory директория для обработки
     * @param arguments аргументы обработки
     * @return карта статистики по расширениям файлов
     */
    public Map<String, FileStatistics> processDirectory(Path directory, Arguments arguments) {
        FileFilter filter = createFileFilter(arguments);
        int threadCount = arguments.getThreadCount();

        if (threadCount == 1) {
            return processDirectorySingleThreaded(directory, arguments, filter);
        } else {
            return processDirectoryMultithreaded(directory, arguments, filter, threadCount);
        }
    }

    private Map<String, FileStatistics> processDirectorySingleThreaded(
            Path directory, Arguments arguments, FileFilter filter) {
        Map<String, FileStatistics> statistics = new java.util.concurrent.ConcurrentHashMap<>();

        try {
            List<Path> files = collectFiles(directory, arguments, filter);
            notifyObserversStarted(files.size());

            for (Path file : files) {
                processFile(file, statistics);
                notifyObserversProgressed(statistics.size(), files.size());
            }

            notifyObserversCompleted();
        } catch (IOException e) {
            // Handle error
        }

        return statistics;
    }

    private Map<String, FileStatistics> processDirectoryMultithreaded(
            Path directory, Arguments arguments, FileFilter filter, int threadCount) {
        Map<String, FileStatistics> statistics = new java.util.concurrent.ConcurrentHashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CompletionService<FileStatistics> completionService = new ExecutorCompletionService<>(executor);
        AtomicInteger submittedTasks = new AtomicInteger(0);

        try {
            List<Path> files = collectFiles(directory, arguments, filter);
            notifyObserversStarted(files.size());

            for (Path file : files) {
                submittedTasks.incrementAndGet();
                completionService.submit(() -> collector.collectStatistics(file));
            }

            int processed = 0;
            while (processed < submittedTasks.get()) {
                Future<FileStatistics> future = completionService.take();
                FileStatistics fileStats = future.get();
                if (fileStats != null) {
                    mergeStatistics(statistics, fileStats);
                }
                processed++;
                notifyObserversProgressed(processed, files.size());
            }

            notifyObserversCompleted();
        } catch (Exception e) {
            // Handle error
        } finally {
            executor.shutdown();
        }

        return statistics;
    }

    /**
     * Собирает список файлов для обработки согласно аргументам и фильтрам.
     */
    private List<Path> collectFiles(Path directory, Arguments arguments, FileFilter filter) throws IOException {
        int maxDepth = arguments.isRecursive() ? arguments.getMaxDepth() : 1;
        try (Stream<Path> stream = Files.walk(directory, maxDepth)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(file -> !filter.shouldProcess(file))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Обрабатывает отдельный файл и обновляет статистику.
     */
    private void processFile(Path file, Map<String, FileStatistics> statistics) {
        FileStatistics fileStats = collector.collectStatistics(file);
        if (fileStats != null) {
            mergeStatistics(statistics, fileStats);
        }
    }

    /**
     * Объединяет статистику из обработанного файла в общую статистику.
     */
    private void mergeStatistics(Map<String, FileStatistics> statistics, FileStatistics fileStats) {
        String extension = fileStats.getExtension();
        statistics.merge(extension, fileStats, (existing, newStats) -> {
            existing.merge(newStats);
            return existing;
        });
    }

    /**
     * Создает фильтр файлов на основе аргументов.
     */
    private FileFilter createFileFilter(Arguments arguments) {
        CompositeFileFilter compositeFilter = new CompositeFileFilter(CompositeFileFilter.CompositionType.AND);
        boolean hasFilters = false;

        if (arguments.getIncludeExtensions() != null && !arguments.getIncludeExtensions().isEmpty()) {
            compositeFilter.addFilter(new ExtensionFileFilter(arguments.getIncludeExtensions(), true));
            hasFilters = true;
        }

        if (arguments.getExcludeExtensions() != null && !arguments.getExcludeExtensions().isEmpty()) {
            compositeFilter.addFilter(new ExtensionFileFilter(arguments.getExcludeExtensions(), false));
            hasFilters = true;
        }

        if (arguments.isGitIgnore()) {
            compositeFilter.addFilter(new GitIgnoreFileFilter(arguments.getPath()));
            hasFilters = true;
        }

        if (!hasFilters) {
            return path -> false;
        }

        return compositeFilter;
    }

    private void notifyObserversStarted(int totalFiles) {
        for (FileProcessingObserver observer : observers) {
            observer.onProcessingStarted(totalFiles);
        }
    }

    private void notifyObserversProgressed(int processedFiles, int totalFiles) {
        for (FileProcessingObserver observer : observers) {
            observer.onFileProcessed(processedFiles, totalFiles);
        }
    }

    private void notifyObserversCompleted() {
        for (FileProcessingObserver observer : observers) {
            observer.onProcessingCompleted();
        }
    }
}
