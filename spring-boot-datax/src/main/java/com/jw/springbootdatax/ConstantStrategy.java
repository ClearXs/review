package com.jw.springbootdatax;

import org.springframework.util.StringUtils;

/**
 * 常量
 * @author jiangw
 * @date 2021/3/16 17:01
 * @since 1.0
 */
public class ConstantStrategy implements Strategy {

    @Override
    public String getValue(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        return param;
    }
}
