package com.group3.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    //姓名
    private String username;
    //密码
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    //手机号
    private String phone;
    //头像
    private String avatar;
    //邮箱
    private String email;
    //状态
    private Integer status;
    //注册时间
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime lastLoginTime;
    //用于实现乐观锁
    private Long version;
}
