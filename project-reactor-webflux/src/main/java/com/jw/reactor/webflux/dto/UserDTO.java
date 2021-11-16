package com.jw.reactor.webflux.dto;

import com.jw.reactor.webflux.vo.UserVo;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO extends UserVo {

    private String createBy;

    private Date createTime;
}
