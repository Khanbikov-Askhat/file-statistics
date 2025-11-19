package com.rtk.filestatistics.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileStatisticsTest {

    @Test
    void testFileStatisticsCreation() {
        FileStatistics stats = new FileStatistics("java");
        assertNotNull(stats);
    }

    @Test
    void testGetExtension() {
        FileStatistics stats = new FileStatistics("java");
        assertEquals("java", stats.getExtension());
    }

    @Test
    void testInitialCounters() {
        FileStatistics stats = new FileStatistics("java");
        assertEquals(0, stats.getFileCount());
        assertEquals(0, stats.getTotalSize());
        assertEquals(0, stats.getTotalLines());
        assertEquals(0, stats.getNonEmptyLines());
        assertEquals(0, stats.getCommentLines());
    }

    @Test
    void testIncrementFileCount() {
        FileStatistics stats = new FileStatistics("java");
        stats.incrementFileCount();
        assertEquals(1, stats.getFileCount());
    }

    @Test
    void testAddToTotalSize() {
        FileStatistics stats = new FileStatistics("java");
        stats.addToTotalSize(100);
        assertEquals(100, stats.getTotalSize());
    }

    @Test
    void testIncrementTotalLines() {
        FileStatistics stats = new FileStatistics("java");
        stats.incrementTotalLines();
        assertEquals(1, stats.getTotalLines());
    }

    @Test
    void testIncrementNonEmptyLines() {
        FileStatistics stats = new FileStatistics("java");
        stats.incrementNonEmptyLines();
        assertEquals(1, stats.getNonEmptyLines());
    }

    @Test
    void testIncrementCommentLines() {
        FileStatistics stats = new FileStatistics("java");
        stats.incrementCommentLines();
        assertEquals(1, stats.getCommentLines());
    }

    @Test
    void testMerge() {
        FileStatistics stats1 = new FileStatistics("java");
        stats1.incrementFileCount();
        stats1.addToTotalSize(100);
        stats1.incrementTotalLines();
        
        FileStatistics stats2 = new FileStatistics("java");
        stats2.incrementFileCount();
        stats2.addToTotalSize(200);
        stats2.incrementTotalLines();
        
        stats1.merge(stats2);
        assertEquals(2, stats1.getFileCount());
        assertEquals(300, stats1.getTotalSize());
        assertEquals(2, stats1.getTotalLines());
    }
}

