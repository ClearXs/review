package com.jw.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Slf4j
public class CreateStream {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("gh");
        // 集合创建流
        list.stream().peek(System.out::println);
        list.parallelStream().peek(System.out::println);

        // 数组创建流
        Arrays.stream(new int[]{});

        // random创建无限流，使用limit限制它流大小
        new Random().ints().limit(2).mapToObj(Integer::toHexString).forEach(log::info);

        // 使用Stream产生流，此时产生的流也是无限流，使用limit限制它流的大小
        Stream.generate(() -> 1).limit(2).map(Object::toString).forEach(log::info);
    }
}
