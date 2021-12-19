package com.jw.basics.collection;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;

public class TestMap {

    @Test
    public void testMap() {
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("test", "test");
        map.put("tes", "221");
        Annotation[] annotations = TestAnnation.class.getAnnotations();
        System.out.println(annotations);

    }
}
