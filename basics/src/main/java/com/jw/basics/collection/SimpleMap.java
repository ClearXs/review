package com.jw.basics.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleMap {

    public static void main(String[] args) {

        Map<String, Object> hashMap = new HashMap<>();

        for (int i = 0; i < 16; i++) {
            hashMap.put("test", "val" + i);
        }

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

        String key = map.computeIfAbsent("key", k -> "value");
        System.out.println(key);
        // 如果存在相同key，那么他不会再次插入并非且返回值是原先的value
        String key1 = map.computeIfAbsent("key", k -> "value1");
        System.out.println(key1);

        String key2 = map.get("key");
        System.out.println(key2);
    }

}
