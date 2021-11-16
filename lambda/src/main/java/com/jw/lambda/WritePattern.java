package com.jw.lambda;

public class WritePattern {


    public static void main(String[] args) {
        // 方法
        // 1.
        Todo todo1 = (i) -> i * 2;
        // 2.基本的写法只有一个变量可以去掉括号，如果只有一行代码，它默认是有return
        Todo todo2 = i -> i * 2;
        // 3.可以定义参数的类型
        Todo todo3 = (int i) -> i * 2;
        // 4.可以有多行代码
        Todo todo4 = i -> {



            System.out.println("----");
            return i * 2;
        };
    }


    /**
     * 要求接口只能有一个要实现方法
     * 解释：
     * 1.如果有多个，就不知道我们写的lambda表达式应用的事哪个方法
     * 2.接口可以有多个默认方法
     * 3.单一责任制：一个接口只做一个事
     */
    @FunctionalInterface
    interface Todo {

        int doubleNum(int i);

        default int add(int x, int y) {
            return x + y;
        }
    }

    @FunctionalInterface
    interface TodoEfficientAdd {
        int addAll();

        default int add(int x, int y) {
            return x + y;
        }
    }

    interface CombineTodo extends Todo, TodoEfficientAdd {
        int addCombine();


        @Override
        default int add(int x, int y) {
            return Todo.super.add(x, y);
        }
    }
}


