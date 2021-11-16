package com.jw.springbootdatax;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

/**
 * 运算符
 * @author jiangw
 * @date 2021/3/16 17:38
 * @since 1.0
 */
public class OperatorStrategy implements Strategy {

    private final static JexlEngine engine = new JexlEngine();

    @Override
    public String getValue(String param) {
        Expression expression = engine.createExpression(param);
        Object evaluate = expression.evaluate(new MapContext());
        return evaluate.toString();
    }
}
