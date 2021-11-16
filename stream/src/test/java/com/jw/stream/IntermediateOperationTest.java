package com.jw.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class IntermediateOperationTest {

    String str = "intermediate operation test";

    @Test
    public void testMap() {
        // 把数据流中每一个值使用提供的Function一一执行，最终返回个数相同的数据流
        Arrays.stream(str.split(" "))
                .filter(s -> s.length() > 5)
                .map(s -> Integer.toString(s.length()))
                .forEach(System.out::println);
    }

    @Test
    public void testFlatMap() {
        // flat是扁平的意思。它把数据流中的每一个值，使用所提供的函数执行一遍，一一对应。得到元素相同的数据流。只不过，
        // 面的元素也是一个子数据流。最后把子数据流合并得到的元素个数大概率与源数据流个数不同
        // 比如字符串，"sss"，可以把它拆解成's','s','s'的子数据流。所以flatMap返回的是一个Stream类型，表示对元素怎么拆分。
        Arrays.stream(str.split(" "))
                .flatMap(s -> s.chars().boxed())
                .forEach(System.out::println);

    }
}
