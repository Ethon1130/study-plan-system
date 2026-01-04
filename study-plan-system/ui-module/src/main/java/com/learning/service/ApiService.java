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
                // 这里需要特殊处理文件下载
                // 暂时简化处理
                return null;
            }
        };
    }
}
