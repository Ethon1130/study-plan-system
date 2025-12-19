package com.group3.common.exception;

import lombok.Getter;

/**
 * 业务类异常
 */
@Getter
public class BusinessException extends RuntimeException{
    private final Integer code;
    public BusinessException(String message){
        super(message);
        this.code = 0;
    }
}
