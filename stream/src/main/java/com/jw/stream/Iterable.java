package com.jw.stream;

import java.util.stream.IntStream;

public class Iterable {

    public static void main(String[] args) {

        int[] nums = {1, 3, 5};
        // 外部迭代
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        // 中间操作（返回stream流操作）
        // 终止操作（产生副作用，）
        int sum1 = IntStream.of(nums).map(Iterable::doubleNum).sum();
        System.out.println(sum1);
        IntStream.of(nums).map(Iterable::doubleNum);

    }

    public static int doubleNum(int num) {
        System.out.println("执行乘2: " + num);
        return num * 2;
    }
}
