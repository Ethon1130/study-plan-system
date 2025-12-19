package com.group3.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanVO {
    private Long id;
    //计划名称
    private String title;
    //优先级别  1:高, 2:中, 3:低
    private Integer priority;
    //0:待办, 1:进行中, 2:已完成
    private Integer status;
    //进度（0-100）
    private Integer progress;
    //结束时间
    private LocalDateTime endTime;
    //乐观锁
    private Long version;
}
