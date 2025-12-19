package com.group3.common.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学习计划
 */
public class Plan implements Serializable {
    private Long id;

    //所属用户ID关联 User
    private Long userId;
    //计划名称
    private String title;
    //详细描述计划
    private String description;
    //优先级别  1:高, 2:中, 3:低
    private Integer priority;
    //0:待办, 1:进行中, 2:已完成
    private Integer status;
    //进度（0-100）
    private Integer progress;
    //开始时间
    private LocalDateTime startTime;
    //结束时间
    private LocalDateTime endTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    //乐观锁
    private Long version;
}
