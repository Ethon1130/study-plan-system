package com.learning.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LearningPlan implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String subject;
    private int totalHours;
    private int completedHours;
    private String status;
    private List<LearningRecord> records;

    public LearningPlan() {
        this.records = new ArrayList<>();
        this.completedHours = 0;
        this.status = "进行中";
    }

    public LearningPlan(String title, String description, LocalDate startDate, LocalDate endDate, String subject, int totalHours) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subject = subject;
        this.totalHours = totalHours;
        this.completedHours = 0;
        this.status = "进行中";
        this.records = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public int getCompletedHours() {
        return completedHours;
    }

    public void setCompletedHours(int completedHours) {
        this.completedHours = completedHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LearningRecord> getRecords() {
        return records;
    }

    public void setRecords(List<LearningRecord> records) {
        this.records = records;
    }

    public void addRecord(LearningRecord record) {
        this.records.add(record);
        this.completedHours += record.getHours();
        updateStatus();
    }

    public void removeRecord(LearningRecord record) {
        this.records.remove(record);
        this.completedHours -= record.getHours();
        updateStatus();
    }

    private void updateStatus() {
        if (completedHours >= totalHours) {
            this.status = "已完成";
        } else if (LocalDate.now().isAfter(endDate)) {
            this.status = "已逾期";
        } else {
            this.status = "进行中";
        }
    }

    @Override
    public String toString() {
        return "LearningPlan{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", totalHours=" + totalHours +
                ", completedHours=" + completedHours +
                ", status='" + status + '\'' +
                '}';
    }
}
