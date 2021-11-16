package com.jw.springbootdatax;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * job info value
 * @author jiangw
 * @date 2021/3/17 9:21
 * @since 1.0
 */
public class JobValue {

    /**
     * info value
     */
    private String value;

    /**
     * info type
     */
    private String type;

    /**
     * database 读取连接属性
     */
    private String readBy = "reader";

    private JobValue() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReadBy() {
        return readBy;
    }

    public void setReadBy(String readBy) {
        this.readBy = readBy;
    }

    public static JobValue getJobValue(Object value) {
        JSONObject valueObj = JSONUtil.parseObj(value);
        JobValue jobValue = new JobValue();
        if (valueObj.containsKey("value")) {
            jobValue.setValue(valueObj.getStr("value"));
        }
        if (valueObj.containsKey("type")) {
            jobValue.setType(valueObj.getStr("type"));
        }
        if (valueObj.containsKey("readBy")) {
            jobValue.setReadBy(valueObj.getStr("readBy"));
        }
        return jobValue;
    }

}
