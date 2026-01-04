package com.learning.model;

import lombok.Data;
import java.util.List;

@Data
public class ReportData {
    private Double completionRate;
    private Integer todoCount;
    private Integer overdueCount;
    private List<GanttDataVO> ganttData;
    
    public Double getCompletionPercentage() {
        if (completionRate == null) return 0.0;
        return completionRate * 100;
    }
}
