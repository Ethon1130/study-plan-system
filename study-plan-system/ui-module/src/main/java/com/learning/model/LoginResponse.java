package com.learning.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 登录响应模型，映射后端UserLoginVO
 */
public class LoginResponse {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("username") 
    private String username;
    
    @JsonProperty("token")
    private String token;
    
    // 构造函数
    public LoginResponse() {}
    
    public LoginResponse(Long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
}
