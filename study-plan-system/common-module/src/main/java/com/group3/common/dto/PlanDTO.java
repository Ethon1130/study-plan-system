package com.group3.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlanDTO {
    private Long userId;
    private String title;
    private String descrition;
    private Integer priority;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
