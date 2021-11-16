package com.jw.unittest.vo;

import com.jw.unittest.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(staticName = "of")
public class MessageVo {

    /**
     * 消息类型
     */
    private String type;

    /**
     * 标题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;

    /**
     * 源
     */
    private User source;

    /**
     * 目标
     */
    private User target;

}
