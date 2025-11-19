package com.rtk.filestatistics.domain;

import java.nio.file.Path;
import java.util.Set;

public class Arguments {
    private final Path path;
    private final boolean recursive;
    private final int maxDepth;
    private final int threadCount;
    private final Set<String> includeExtensions;
    private final Set<String> excludeExtensions;
    private final boolean gitIgnore;
    private final OutputFormat outputFormat;

    private Arguments(Builder builder) {
        this.path = builder.path;
        this.recursive = builder.recursive;
        this.maxDepth = builder.maxDepth;
        this.threadCount = builder.threadCount;
        this.includeExtensions = builder.includeExtensions;
        this.excludeExtensions = builder.excludeExtensions;
        this.gitIgnore = builder.gitIgnore;
        this.outputFormat = builder.outputFormat;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Path getPath() {
        return path;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public Set<String> getIncludeExtensions() {
        return includeExtensions;
    }

    public Set<String> getExcludeExtensions() {
        return excludeExtensions;
    }

    public boolean isGitIgnore() {
        return gitIgnore;
    }

    public OutputFormat getOutputFormat() {
        return outputFormat;
    }

    /**
     * Builder для построения объектов Arguments.
     */
    public static class Builder {
        private Path path;
        private boolean recursive = false;
        private int maxDepth = Integer.MAX_VALUE;
        private int threadCount = 1;
        private Set<String> includeExtensions;
        private Set<String> excludeExtensions;
        private boolean gitIgnore = false;
        private OutputFormat outputFormat = OutputFormat.PLAIN;

        public Builder path(Path path) {
            this.path = path;
            return this;
        }

        public Builder recursive(boolean recursive) {
            this.recursive = recursive;
            return this;
        }

        public Builder maxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
            return this;
        }

        public Builder threadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        public Builder includeExtensions(Set<String> includeExtensions) {
            this.includeExtensions = includeExtensions;
            return this;
        }

        public Builder excludeExtensions(Set<String> excludeExtensions) {
            this.excludeExtensions = excludeExtensions;
            return this;
        }

        public Builder gitIgnore(boolean gitIgnore) {
            this.gitIgnore = gitIgnore;
            return this;
        }

        public Builder outputFormat(OutputFormat outputFormat) {
            this.outputFormat = outputFormat;
            return this;
        }

        public Arguments build() {
            if (path == null) {
                throw new IllegalArgumentException("Path is required");
            }
            return new Arguments(this);
        }
    }
}
