package com.learning.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 前端API响应类，映射后端的Result<T>结构
 */
public class ApiResponse<T> {
    @JsonProperty("code")
    private Integer code;
    
    @JsonProperty("msg")
    private String msg;
    
    @JsonProperty("data")
    private T data;
    
    public boolean isSuccess() {
        return code != null && code == 1;
    }
    
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(1);
        response.setData(data);
        return response;
    }
    
    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(0);
        response.setMsg(message);
        return response;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
