package com.jw.lambda;

import cn.hutool.core.lang.func.Func1;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class MethodReference {

    static class Review {

        private String knowledgePoints;

        private Integer order;

        public Review() {

        }

        public Review(String knowledgePoints) {
            this.knowledgePoints = knowledgePoints;
        }

        public Review(String knowledgePoints, Integer order) {
            this.knowledgePoints = knowledgePoints;
            this.order = order;
        }


        public static String learn(String point) {
            return "学习: " + point;
        }

        public String doLearn(String point) {
            return learn(point);
        }

        public void point(String point) {
            System.out.println(point);
        }

        @Override
        public String toString() {
            return "Review{" +
                    "knowledgePoints='" + knowledgePoints + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        // 静态方法引用
        Function<String, String> learn = Review::learn;
        System.out.println(learn.apply("point"));

        // 构造函数调用
        // 无参构造函数
        Supplier<Review> supplier = Review::new;
        Review review = supplier.get();
        // 含有一个参数的构造函数
        Function<String, Review> create = Review::new;
        // 含有两个参数的钩爪函数
        BiFunction<String, Integer, Review> order = Review::new;
        // 动态方法引用
//        Function<String, String> doLearn = review::doLearn;
        // 输入输出类型相同
        UnaryOperator<String> doLearn = review::doLearn;
        System.out.println(doLearn.apply("ppp"));


        // 类名调用动态方法
        BiFunction<Review, String, String> dynamicFun = Review::doLearn;
        System.out.println(dynamicFun.apply(review, "pppppp"));

    }

}
