package com.example.utils;

import java.util.List;

/**
 * Утилитарный класс с вспомогательными методами
 */
public class Utils {

    /**
     * Проверяет, является ли строка пустой или null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Объединяет список строк в одну строку
     */
    public static String join(List<String> strings, String delimiter) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            if (i > 0) {
                result.append(delimiter);
            }
            result.append(strings.get(i));
        }

        return result.toString();
    }
}