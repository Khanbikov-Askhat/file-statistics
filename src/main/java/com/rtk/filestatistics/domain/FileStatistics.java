package com.rtk.filestatistics.domain;

import java.util.concurrent.atomic.AtomicLong;

public class FileStatistics {
    private final String extension;
    private final AtomicLong fileCount = new AtomicLong(0);
    private final AtomicLong totalSize = new AtomicLong(0);
    private final AtomicLong totalLines = new AtomicLong(0);
    private final AtomicLong nonEmptyLines = new AtomicLong(0);
    private final AtomicLong commentLines = new AtomicLong(0);

    public FileStatistics(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public long getFileCount() {
        return fileCount.get();
    }

    public long getTotalSize() {
        return totalSize.get();
    }

    public long getTotalLines() {
        return totalLines.get();
    }

    public long getNonEmptyLines() {
        return nonEmptyLines.get();
    }

    public long getCommentLines() {
        return commentLines.get();
    }

    public void incrementFileCount() {
        fileCount.incrementAndGet();
    }

    public void addToTotalSize(long size) {
        totalSize.addAndGet(size);
    }

    public void incrementTotalLines() {
        totalLines.incrementAndGet();
    }

    public void incrementNonEmptyLines() {
        nonEmptyLines.incrementAndGet();
    }

    public void incrementCommentLines() {
        commentLines.incrementAndGet();
    }

    public void merge(FileStatistics other) {
        if (other == null) {
            return;
        }
        fileCount.addAndGet(other.fileCount.get());
        totalSize.addAndGet(other.totalSize.get());
        totalLines.addAndGet(other.totalLines.get());
        nonEmptyLines.addAndGet(other.nonEmptyLines.get());
        commentLines.addAndGet(other.commentLines.get());
    }
}
