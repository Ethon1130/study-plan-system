package com.group3.common.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;


@Data
public class UserPageDTO {
    @Parameter(description = "页码", example = "1")  //swagger添加之后测试更方便，不用在url里写变量
    private Integer page = 1;

    @Parameter(description = "每页数量", example = "10")
    private Integer pageSize = 10;

    @Parameter(description = "用户名", example = "user1")
    private String name;
}