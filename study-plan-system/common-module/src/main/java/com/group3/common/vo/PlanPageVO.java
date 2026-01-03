package com.group3.common.vo;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
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
