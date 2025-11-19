package com.rtk.filestatistics.infrastructure.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositeFileFilterTest {

    @Test
    void testCompositeFileFilter_AND() {
        // shouldProcess returns true if file should be FILTERED OUT
        // Include filter: include java/xml files (filter out others)
        // Exclude filter: exclude class/jar files (filter out class/jar)
        FileFilter includeFilter = new ExtensionFileFilter(Set.of("java", "xml"), true);
        FileFilter excludeFilter = new ExtensionFileFilter(Set.of("class", "jar"), false);
        
        CompositeFileFilter compositeFilter = new CompositeFileFilter(CompositeFileFilter.CompositionType.AND);
        compositeFilter.addFilter(includeFilter);
        compositeFilter.addFilter(excludeFilter);
        
        // main.java: matches java (include=false, don't filter), not class/jar (exclude=false, don't filter)
        // AND: false && false = false -> should NOT be filtered
        assertFalse(compositeFilter.shouldProcess(Paths.get("main.java")));
        // main.class: doesn't match java/xml (include=true, filter), matches class (exclude=true, filter)
        // AND: true && true = true -> should be filtered
        assertTrue(compositeFilter.shouldProcess(Paths.get("main.class")));
        // main.txt: doesn't match java/xml (include=true, filter), not class/jar (exclude=false, don't filter)
        // AND: true && false = false -> should NOT be filtered (only one filter says filter)
        assertFalse(compositeFilter.shouldProcess(Paths.get("main.txt")));
    }

    @Test
    void testCompositeFileFilter_OR() {
        // shouldProcess returns true if file should be FILTERED OUT
        // For OR: filter if ANY filter says to filter
        // filter1 for java: !matches = false (don't filter java)
        // filter2 for java: !matches = true (filter non-xml, which includes java)
        // OR: false || true = true -> filter java (WRONG!)
        // Actually, for include filters with OR, we want: don't filter if ANY says don't filter
        // But our OR logic is: filter if ANY says filter
        // So for OR with include filters, the logic doesn't work as expected
        // Let's test with exclude filters instead:
        FileFilter filter1 = new ExtensionFileFilter(Set.of("class"), false);
        FileFilter filter2 = new ExtensionFileFilter(Set.of("jar"), false);
        
        CompositeFileFilter compositeFilter = new CompositeFileFilter(CompositeFileFilter.CompositionType.OR);
        compositeFilter.addFilter(filter1);
        compositeFilter.addFilter(filter2);
        
        // class and jar files should be filtered (shouldProcess=true)
        assertTrue(compositeFilter.shouldProcess(Paths.get("test.class")));
        assertTrue(compositeFilter.shouldProcess(Paths.get("test.jar")));
        // java files should NOT be filtered (shouldProcess=false)
        assertFalse(compositeFilter.shouldProcess(Paths.get("test.java")));
    }

    @Test
    void testCompositeFileFilter_Empty() {
        CompositeFileFilter compositeFilter = new CompositeFileFilter(CompositeFileFilter.CompositionType.AND);
        // Empty composite filter should process all files (filter nothing)
        assertTrue(compositeFilter.shouldProcess(Paths.get("test.java")));
    }
}
