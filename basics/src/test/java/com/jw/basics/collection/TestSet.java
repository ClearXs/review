package com.jw.basics.collection;

import com.jw.basics.collection.model.User;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

public class TestSet {

    @Test
    public void add() {
        Set<String> hashSet = new HashSet<>();

        Set<User> treeSet = new TreeSet<>();
        User user = new User();
        treeSet.add(user);

        Set<String> linkedHashSet = new LinkedHashSet<>();
    }
}
