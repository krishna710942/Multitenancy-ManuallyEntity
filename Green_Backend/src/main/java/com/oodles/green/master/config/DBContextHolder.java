package com.oodles.green.master.config;

public class DBContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static String getCurrentDb() {
        return contextHolder.get();
    }

    public static void setCurrentDb(String dbType) {
        contextHolder.set(dbType);
    }

    public static void clear() {
        contextHolder.remove();
    }
}
