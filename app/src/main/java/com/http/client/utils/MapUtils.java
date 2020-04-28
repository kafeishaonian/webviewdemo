package com.http.client.utils;

import java.util.Map;

/**
 * Created by Hongmingwei on 2018/1/11.
 * Email: 648600445@qq.com
 */

public class MapUtils {

    public static <K, V> boolean isEmpty(Map<K, V> sourceMap) {
        return (sourceMap == null || sourceMap.size() == 0);
    }
}
