package com.dev.eatjeong.util;

import java.util.List;
import java.util.Map;

public class Util {

    /**
     * String, Map, List Null check and Empty check
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
}
