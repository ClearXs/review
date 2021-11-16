package com.jw.basics.collection;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SimpleSet {

    public static void main(String[] args) {
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("b");
        treeSet.add("a");
        for (String s : treeSet) {
            System.out.println(s);
        }
        Set<String> hashSet = new HashSet<>();
        hashSet.add("b");
        hashSet.add("a");
        for (String s : hashSet) {
            System.out.println(s);
        }
    }
}
