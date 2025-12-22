package com.group3.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportVO {
    private Double completionRate;
    private Integer todoCount;
    private Integer overdueCount;
    private List<GanttDataVO> ganttData;
}
