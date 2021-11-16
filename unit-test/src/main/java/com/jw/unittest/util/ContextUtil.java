package com.jw.unittest.util;

import com.jw.unittest.vo.UserVo;

public class ContextUtil {

    public static UserVo getCurrentUser() {
        UserVo userVo = new UserVo();
        userVo.setId("1");
        return userVo;
    }

    public static String getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
