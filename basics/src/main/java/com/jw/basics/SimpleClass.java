package com.jw.basics;

import java.lang.reflect.Method;

public class SimpleClass {

    public static void main(String[] args) {
        Class<Test> testClass = Test.class;
        Method[] declaredMethods = testClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {

            System.out.println(declaredMethod);
        }
    }

    interface Test {


        void test1(String t1);

        void test1(String t1, String t2);

        void test2(String t1);
    }
}
