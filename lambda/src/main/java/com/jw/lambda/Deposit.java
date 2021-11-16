package com.jw.lambda;

import java.text.DecimalFormat;
import java.util.function.Function;

public class Deposit {

    private int money;

    public Deposit(int money) {
        this.money = money;
    }

    interface MoneyFormat {
        String format(Integer money);
    }

    public void printDeposit(MoneyFormat moneyFormat) {
        System.out.println("deposit: " + moneyFormat.format(money));
    }

    public void printDeposit(Function<Integer, String> format) {
        System.out.println("deposit: " + format.apply(money));
    }

    public static void main(String[] args) {
        Deposit deposit = new Deposit(1111111111);
        Function<Integer, String> format = i -> new DecimalFormat("#,###").format(i);
        // andThen：先执行完apply后将结果作为after的输入，执行after。
        deposit.printDeposit(format.andThen(s -> "my" + s));
    }

}
