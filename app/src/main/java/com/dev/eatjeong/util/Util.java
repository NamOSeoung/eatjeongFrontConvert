package com.dev.eatjeong.util;

import android.os.Build;

import java.util.List;
import java.util.Map;

public class Util {

    /**
     * String, Map, List Null check and Empty check
     * Null 또는 Empty일 경우 true
     * @param s Map, List, String
     * @return boolean
     */
    public static boolean isNullOrEmpty(Object s) {
        if (s == null) {
            return true;
        }
        if ((s instanceof String) && (((String) s).trim().length() == 0)) {
            return true;
        }
        if (s instanceof Map) {
            return ((Map<?, ?>) s).isEmpty();
        }
        if (s instanceof List) {
            return ((List<?>) s).isEmpty();
        }
        if (s instanceof Object[]) {
            return (((Object[]) s).length == 0);
        }
        return false;
    }

    /**
     * 에뮬레이터 여부 체크
     * 에뮬레이터일 경우 true
     * @return boolean
     */
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }
}
