package com.group3.controller.report;

import com.group3.common.result.Result;
import com.group3.common.vo.ReportVO;
import com.group3.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user/report")
@Tag(name = "报表管理模块")
@Slf4j
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 首页数据统计
     * @return
     */
    @GetMapping("/statistics")
    @Operation(summary = "首页数据统计")
    public Result<ReportVO> getReport(){
        log.info("获取首页数据统计");
        ReportVO reportVO = reportService.getStatistics();
        return Result.success(reportVO);
    }
    
    /**
     * 导出Excel
     * @param response
     */
    @GetMapping("/export")
    @Operation(summary = "导出Excel")
    public void export(HttpServletResponse response){
        log.info("导出学习计划报表");
        try {
            String fileName = URLEncoder.encode("学习计划报表", StandardCharsets.UTF_8);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            
            OutputStream outputStream = response.getOutputStream();
            reportService.exportExcel(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("导出Excel失败", e);
            throw new RuntimeException("导出Excel失败");
        }
    }
}
