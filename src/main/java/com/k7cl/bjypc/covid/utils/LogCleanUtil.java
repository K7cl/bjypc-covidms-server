package com.k7cl.bjypc.covid.utils;

public class LogCleanUtil {
    public static String clean(String st) {
        return st.replace('\t', '_').replace('\n', '_').replace('\r', '_');
    }
}
