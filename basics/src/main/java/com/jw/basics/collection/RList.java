package com.jw.basics.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RList {

    /**
     * 总结：
     * 1.ArrayList
     * @param args
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        String s = list.get(0);
        String remove = list.remove(0);
        list = new LinkedList<>();
        list.add(0, "123");
        list.add("123");
        list.add(1, "123");
        String s1 = list.get(0);
        list.remove(1);


    }

}
