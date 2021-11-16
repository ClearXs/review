package com.jw.springbootdatax;

public interface Strategy {

    String CONSTANT = "constant";

    String OPERATOR = "operator";

    String DATABASE = "database";

    /**
     * 根据不同的实现，获取对象的值
     * @return 返回的是一个string串
     * @param param
     */
    String getValue(String param);
}
