package com.example.deep.nested;

// Файл во вложенной директории
public class DeepFile {
    private int depth;

    public DeepFile(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}