package com.rtk.filestatistics.domain;

/**
 * Перечисление форматов вывода результатов.
 */
public enum OutputFormat {
    PLAIN,
    XML,
    JSON;

    public static OutputFormat fromString(String value) {
        if (value == null) {
            return PLAIN;
        }
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return PLAIN;
        }
    }
}
