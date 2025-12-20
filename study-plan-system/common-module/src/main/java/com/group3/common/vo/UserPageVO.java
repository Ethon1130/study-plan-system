package com.group3.common.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理员学生账号分页查询
 */
@Data
public class UserPageVO {
    private Integer id;
    private String username;
    private String email;
    private Integer status;
    private LocalDateTime lastLoginTime;
}
