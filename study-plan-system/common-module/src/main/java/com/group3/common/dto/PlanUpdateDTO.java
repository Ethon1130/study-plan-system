package com.group3.common.dto;

import lombok.Data;

@Data
public class PlanUpdateDTO {
    private Long Id;
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
}
