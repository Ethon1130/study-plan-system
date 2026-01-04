package com.learning.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * 前端计划模型，兼容后端PlanPageVO和PlanDTO
 */
public class ApiPlan {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    // 后端PlanDTO使用的字段名（注意拼写错误）
    @JsonProperty("descrition")
    private String descrition;
    
    @JsonProperty("priority")
    private Integer priority;
    
    @JsonProperty("status")
    private Integer status;
    
    @JsonProperty("progress") 
    private Integer progress;
    
    @JsonProperty("startTime")
    private LocalDateTime startTime;
    
    @JsonProperty("endTime")
    private LocalDateTime endTime;
    
    @JsonProperty("userId")
    private Long userId;
    
    @JsonProperty("version")
    private Long version;
    
    public ApiPlan() {}
    
    public ApiPlan(String title, String description, LocalDateTime startTime, LocalDateTime endTime, Integer priority) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.status = 0; // 默认进行中
        this.progress = 0; // 默认进度0
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        // 优先返回description，如果为空则返回descrition（后端拼写错误字段）
        return description != null ? description : descrition;
    }
    
    public void setDescription(String description) {
        this.description = description;
        this.descrition = description; // 同时设置后端字段
    }
    
    public String getDescrition() {
        return descrition;
    }
    
    public void setDescrition(String descrition) {
        this.descrition = descrition;
        if (this.description == null) {
            this.description = descrition;
        }
    }
    
    public Integer getPriority() {
        return priority;
    }
    
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getProgress() {
        return progress;
    }
    
    public void setProgress(Integer progress) {
        this.progress = progress;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getVersion() {
        return version;
    }
    
    public void setVersion(Long version) {
        this.version = version;
    }
    
    public String getStatusText() {
        if (status == null) return "进行中";
        switch (status) {
            case 0: return "进行中";
            case 1: return "已完成";
            case 2: return "已暂停";
            case 3: return "已取消";
            default: return "未知";
        }
    }
    
    public String getPriorityText() {
        if (priority == null) return "普通";
        switch (priority) {
            case 1: return "高";
            case 2: return "普通";
            case 3: return "低";
            default: return "普通";
        }
    }
}
