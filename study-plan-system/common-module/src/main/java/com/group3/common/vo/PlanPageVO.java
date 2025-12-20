package com.group3.common.vo;

import java.time.LocalDateTime;

public class PlanPageVO {
    private Long id;
    private String title;
    private Integer priority;
    private Integer status;
    private Integer progress;
    private LocalDateTime endTime;
    private Long version;
    // 不返回 description、startTime 等不需要的字段
}
