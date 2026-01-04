package com.learning.service;

import com.learning.model.*;
import com.learning.utils.HttpClientUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.http.HttpResponse;
import javafx.concurrent.Task;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ApiService {
    
    // 用户相关API
    public static Task<ApiResponse<LoginResponse>> login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.post("/user/user/login", request);
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<LoginResponse>>() {});
    }
    
    public static Task<ApiResponse<Void>> register(String username, String password, String email) {
        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setEmail(email);
        
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.post("/user/user/register", request);
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<Void>>() {});
    }
    
    // 计划相关API
    public static Task<ApiResponse<Long>> createPlan(ApiPlan plan) {
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.post("/user/plan/add", plan);
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<Long>>() {});
    }
    
    public static Task<ApiResponse<PageResult<ApiPlan>>> getPlans(int page, int pageSize) {
        // 创建PlanPageQueryDTO对象
        java.util.Map<String, Object> queryParams = new java.util.HashMap<>();
        queryParams.put("page", page);
        queryParams.put("pageSize", pageSize);
        queryParams.put("userId", getCurrentUserId()); // 添加用户ID
        
        System.out.println("Creating plan page request with userId: " + getCurrentUserId());
        
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.post("/user/plan/page", queryParams);
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<PageResult<ApiPlan>>>() {});
    }
    
    private static Long getCurrentUserId() {
        // 从HttpClientUtil获取token并解析用户ID
        String token = HttpClientUtil.getToken();
        if (token != null && !token.isEmpty()) {
            // 简化：假设用户ID为1，实际应该解析JWT
            return 1L;
        }
        return 1L; // 默认用户ID
    }
    
    public static Task<ApiResponse<Void>> updatePlan(ApiPlan plan) {
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.put("/user/plan", plan);
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<Void>>() {});
    }
    
    public static Task<ApiResponse<Void>> deletePlans(String ids) {
        String endpoint = "/user/plan?ids=" + ids;
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.delete(endpoint);
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<Void>>() {});
    }
    
    // 报表相关API
    public static Task<ApiResponse<ReportData>> getStatistics() {
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.get("/user/report/statistics");
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<ReportData>>() {});
    }
    
    public static Task<Void> exportExcel() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // 调用后端导出接口
                    java.util.concurrent.CompletableFuture<java.net.http.HttpResponse<byte[]>> future = 
                        HttpClientUtil.downloadFile("/user/report/export");
                    
                    System.out.println("Waiting for Excel download...");
                    java.net.http.HttpResponse<byte[]> response = future.get(60, java.util.concurrent.TimeUnit.SECONDS);
                    
                    if (response.statusCode() >= 200 && response.statusCode() < 300) {
                        byte[] excelData = response.body();
                        System.out.println("Received Excel file: " + excelData.length + " bytes");
                        
                        // 获取文件名
                        String filename = "学习计划报表_" + java.time.LocalDateTime.now().format(
                            java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xlsx";
                        
                        // 保存文件到用户的下载目录
                        String downloadDir = System.getProperty("user.home") + java.io.File.separator + "Downloads";
                        java.io.File dir = new java.io.File(downloadDir);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        
                        java.io.File file = new java.io.File(downloadDir, filename);
                        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
                            fos.write(excelData);
                            fos.flush();
                        }
                        
                        System.out.println("Excel file saved to: " + file.getAbsolutePath());
                        
                        // 尝试打开文件所在的目录
                        try {
                            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                                Runtime.getRuntime().exec("explorer.exe /select," + file.getAbsolutePath());
                            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                                Runtime.getRuntime().exec(new String[]{"open", "-R", file.getAbsolutePath()});
                            } else {
                                // Linux
                                Runtime.getRuntime().exec(new String[]{"xdg-open", downloadDir});
                            }
                        } catch (Exception e) {
                            System.err.println("Failed to open file explorer: " + e.getMessage());
                        }
                        
                        return null;
                    } else {
                        String errorMsg = "Download failed: " + response.statusCode();
                        System.err.println(errorMsg);
                        throw new RuntimeException(errorMsg);
                    }
                } catch (java.util.concurrent.TimeoutException e) {
                    System.err.println("Download timeout: " + e.getMessage());
                    throw new RuntimeException("Download timeout after 60 seconds", e);
                } catch (Exception e) {
                    System.err.println("Download error: " + e.getMessage());
                    e.printStackTrace();
                    throw new RuntimeException("Failed to download Excel file", e);
                }
            }
        };
    }
    
    // AI 教练相关 API
    public static Task<ApiResponse<AiCoachResponse>> getCoachReply(String question, String context) {
        AiCoachRequest request = new AiCoachRequest();
        request.setQuestion(question);
        request.setContext(context);
        request.setIncludeProgress(true);
        
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.post("/user/ai/coach", request);
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<AiCoachResponse>>() {});
    }
    
    public static Task<ApiResponse<String>> getProgressAnalysis() {
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.get("/user/ai/progress-analysis");
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<String>>() {});
    }
    
    public static Task<ApiResponse<String>> getLearningAdvice() {
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.get("/user/ai/learning-advice");
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<String>>() {});
    }
    
    public static Task<ApiResponse<Integer>> getRemainingRequests() {
        CompletableFuture<HttpResponse<String>> future = HttpClientUtil.get("/user/ai/remaining-requests");
        return HttpClientUtil.createApiTask(future, new TypeReference<ApiResponse<Integer>>() {});
    }
}
