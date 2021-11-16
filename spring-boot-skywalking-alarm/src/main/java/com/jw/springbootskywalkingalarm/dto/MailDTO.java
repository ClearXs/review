package com.jw.springbootskywalkingalarm.dto;

import lombok.Data;

@Data
public class MailDTO {

    private Integer scopeId;
    private String scope;
    private String name;
    private String id0;
    private String id1;
    private String ruleName;
    private String alarmMessage;
    private Long startTime;
}
