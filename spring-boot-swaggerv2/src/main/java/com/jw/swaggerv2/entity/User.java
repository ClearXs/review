package com.jw.swaggerv2.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户实体")
public class User {

    @ApiModelProperty("id")
    private long id;

    @ApiModelProperty("名称")
    private String name;
}
