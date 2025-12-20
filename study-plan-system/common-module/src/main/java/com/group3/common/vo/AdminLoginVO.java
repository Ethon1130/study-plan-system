package com.group3.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminLoginVO {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
