package com.jw.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TerminalOperationTest {

    String str = "terminal operation test";

    @Test
    public void testForEachOrdered() {
        str.chars().parallel().forEach(c -> System.out.print((char) c));
        System.out.println("");
        System.out.println("-----");
        str.chars().parallel().forEachOrdered(c -> System.out.print((char) c));
    }

    @Test
    public void testCollect() {
        List<String> list = Stream.of(str.split(" ")).collect(Collectors.toList());
        System.out.println(list);

        // toArray IntFunction<A[]>，它的输入是一个整数，代表所需要数组的大小
        String[] s = Stream.of(str.split(" ")).toArray(String[]::new);
        Arrays.stream(s).forEach(System.out::println);
    }

    @Test
    public void testReduce() {
        // reduce它可以把数据流每一个元素根据操作依次传递进行，并把上一次操作的结果也作为结果传递进行。其中第一个参数上一次操作的结果，第二个参数是数据流中的元素
        // 判断位空的选项
        Optional<String> reduce = Arrays.stream(str.split(" ")).reduce((s1, s2) -> s1 + "-" + s2);
        // orElse 如果位空，则默认值为""
        System.out.println(reduce.orElse(""));

        // 带有初始化的值
        String reduce1 = Arrays.stream(str.split(" ")).reduce("", (s1, s2) -> s1 + "-" + s2);
        System.out.println(reduce1);
    }

    @Test
    public void testMin() throws Exception {
        Optional<String> min = Arrays.stream(str.split(" ")).min(Comparator.comparingInt(String::length));
        System.out.println(min.orElseThrow(Exception::new));
    }

    @Test
    public void testMax() throws Exception {
        Optional<String> max = Arrays.stream(str.split(" ")).max(Comparator.comparingInt(String::length));
        System.out.println(max.orElseThrow(Exception::new));
    }

    @Test
    public void testCount() {
        System.out.println(Arrays.stream(str.split(" ")).count());
    }

    @Test
    public void testFindFirst() {
        // 短路操作，寻找到就停止
        OptionalInt first = new Random().ints().findFirst();
        System.out.println(first.getAsInt());
    }

    @Test
    public void testFindAny() {
        // 找到任意一个就停止，它的结果不确定，用在并行操作下实现最高性能
        OptionalInt any = new Random().ints().findAny();
        System.out.println(any.getAsInt());
    }

    @Test
    public void testAllMatch() {
        boolean b = Arrays.stream(str.split(" ")).allMatch(s -> s.length() > 55);
        System.out.println(b);
    }


}
