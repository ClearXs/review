package com.jw.basics.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestList {

    @Test
    public void testAdd() {
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();

        System.out.println("arrayList: ");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 20000000; i++) {
            arrayList.add(String.valueOf(i));
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList消费时间: " + (end - start));

        start = System.currentTimeMillis();

        for (int i = 0; i < 20000000; i++) {
            linkedList.add(String.valueOf(i));
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList消费时间: " + (end - start));
    }
}
