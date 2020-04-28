package com.http.client.utils;

/**
 * Created by Hongmingwei on 2018/1/11.
 * Email: 648600445@qq.com
 */

public class StringUtils {
    public static boolean isNotBlank(String str) {
        return (str != null && str.trim().length() > 0);
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static int stringToInt(String str) {
        if (isEmpty(str)) {
            return 0;
        }

        int ret = 0;
        try {
            ret = Integer.parseInt(str);
        } catch (Exception e) {

        }
        return ret;
    }

    public static long stringToLong(String str) {
        if (isEmpty(str)) {
            return 0l;
        }
        long dest = 0l;
        try {
            dest = Long.parseLong(str);
        } catch (Exception e) {

        }
        return dest;
    }
}
