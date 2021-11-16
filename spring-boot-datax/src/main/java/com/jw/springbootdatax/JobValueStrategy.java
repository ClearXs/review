package com.jw.springbootdatax;

public enum JobValueStrategy {

    /**
     * 常量策略
     */
    CONSTANT(Strategy.CONSTANT, new ConstantStrategy()),

    /**
     * 运算发策略
     */
    OPERATOR(Strategy.OPERATOR, new OperatorStrategy()),

    /**
     * 数据库策略
     */
    DATABASE(Strategy.DATABASE, new DatabaseStrategy());

    private final String type;

    private final Strategy strategy;

    JobValueStrategy(String type, Strategy strategy) {
        this.type = type;
        this.strategy = strategy;
    }

    public static Strategy getStrategy(String type) {
        for (JobValueStrategy value : values()) {
            if (value.getType().equals(type)) {
                return value.getStrategy();
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public Strategy getStrategy() {
        return strategy;
    }
}
