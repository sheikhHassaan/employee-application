package com.example.model.helper;

import java.util.UUID;

public class HelperUtils {

    private HelperUtils() {
    }

    public static boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static String choose(String newVal, String oldVal) {
        return hasText(newVal) ? newVal : oldVal;
    }

    public static Double choose(Double newVal, Double oldVal) {
        return (newVal != null && newVal >= 0.0) ? newVal : oldVal;
    }

    public static boolean isUUID(String input) {
        if (input == null) return false;
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
