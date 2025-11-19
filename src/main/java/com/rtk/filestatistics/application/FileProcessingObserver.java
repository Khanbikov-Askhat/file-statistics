package com.rtk.filestatistics.application;

/**
 * Интерфейс наблюдателя за процессом обработки файлов.
 * Позволяет отслеживать прогресс обработки файлов.
 */
public interface FileProcessingObserver {

    /**
     * Вызывается при начале обработки файлов.
     *
     * @param totalFiles общее количество файлов для обработки
     */
    void onProcessingStarted(int totalFiles);

    /**
     * Вызывается при обработке каждого файла.
     *
     * @param processedFiles количество обработанных файлов на данный момент
     * @param totalFiles общее количество файлов
     */
    void onFileProcessed(int processedFiles, int totalFiles);

    /**
     * Вызывается при завершении обработки файлов.
     */
    void onProcessingCompleted();
}
