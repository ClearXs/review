package com.jw.springbootdatax;

import cn.hutool.db.handler.BeanHandler;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库
 * @author jiangw
 * @date 2021/3/16 17:49
 * @since 1.0
 */
public class DatabaseStrategy implements Strategy {

    @Override
    public String getValue(String param) {
        JSONObject connectionObj = JSONUtil.parseObj(param);
        if (!connectionObj.containsKey("jdbcUrl")) {
            return connectionObj.getStr("value");
        }
        String username = connectionObj.getStr("username");
        String password = connectionObj.getStr("password");
        JSONArray jdbcUrls = connectionObj.getJSONArray("jdbcUrl");
        List<String> values = new ArrayList<>();
        for (int i = 0; i < jdbcUrls.size(); i++) {
            String jdbcUrl = jdbcUrls.getStr(i);
            try {
                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(connectionObj.getStr("value"));
                ResultSet set = preparedStatement.executeQuery();
                BeanHandler<Map> bean = new BeanHandler<>(Map.class);
                Map handle = bean.handle(set);
                for (Object value : handle.values()) {
                    if (value != null) {
                        values.add(value.toString());
                    }
                }
                set.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JobLog.getInstance().log(throwables.getMessage());
            }
        }
        return String.join("", values);
    }
}
