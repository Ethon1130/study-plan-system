package com.group3.service;

import com.group3.common.vo.ReportVO;

import java.io.OutputStream;

public interface ReportService {
    
    ReportVO getStatistics();
    
    void exportExcel(OutputStream outputStream);
}
