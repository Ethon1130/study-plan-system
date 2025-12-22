package com.group3.service.impl;

import com.group3.common.context.BaseContext;
import com.group3.common.entity.Plan;
import com.group3.common.vo.GanttDataVO;
import com.group3.common.vo.ReportVO;
import com.group3.mapper.ReportMapper;
import com.group3.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    
    @Autowired
    private ReportMapper reportMapper;
    
    @Override
    public ReportVO getStatistics() {
        Long userId = BaseContext.getCurrentId();
        log.info("获取用户{}的统计数据", userId);
        
        Integer totalPlans = reportMapper.countTotalPlans(userId);
        Integer completedPlans = reportMapper.countCompletedPlans(userId);
        Integer todoCount = reportMapper.countTodoPlans(userId);
        Integer overdueCount = reportMapper.countOverduePlans(userId);
        
        Double completionRate = 0.0;
        if (totalPlans != null && totalPlans > 0) {
            completionRate = (double) completedPlans / totalPlans;
            completionRate = Math.round(completionRate * 100.0) / 100.0;
        }
        
        List<GanttDataVO> ganttData = reportMapper.getGanttData(userId);
        
        return ReportVO.builder()
                .completionRate(completionRate)
                .todoCount(todoCount)
                .overdueCount(overdueCount)
                .ganttData(ganttData)
                .build();
    }
    
    @Override
    public void exportExcel(OutputStream outputStream) {
        Long userId = BaseContext.getCurrentId();
        log.info("导出用户{}的学习计划报表", userId);
        
        List<Plan> plans = reportMapper.getAllPlansByUserId(userId);
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("学习计划报表");
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"计划ID", "计划名称", "描述", "优先级", "状态", "进度", "开始时间", "结束时间", "创建时间"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        int rowNum = 1;
        for (Plan plan : plans) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(plan.getId());
            row.createCell(1).setCellValue(plan.getTitle());
            row.createCell(2).setCellValue(plan.getDescription());
            row.createCell(3).setCellValue(getPriorityText(plan.getPriority()));
            row.createCell(4).setCellValue(getStatusText(plan.getStatus()));
            row.createCell(5).setCellValue(plan.getProgress() + "%");
            row.createCell(6).setCellValue(plan.getStartTime() != null ? plan.getStartTime().format(formatter) : "");
            row.createCell(7).setCellValue(plan.getEndTime() != null ? plan.getEndTime().format(formatter) : "");
            row.createCell(8).setCellValue(plan.getCreateTime() != null ? plan.getCreateTime().format(formatter) : "");
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            log.error("导出Excel失败", e);
            throw new RuntimeException("导出Excel失败");
        }
    }
    
    private String getPriorityText(Integer priority) {
        if (priority == null) return "";
        switch (priority) {
            case 1: return "高";
            case 2: return "中";
            case 3: return "低";
            default: return "";
        }
    }
    
    private String getStatusText(Integer status) {
        if (status == null) return "";
        switch (status) {
            case 0: return "待办";
            case 1: return "进行中";
            case 2: return "已完成";
            default: return "";
        }
    }
}
