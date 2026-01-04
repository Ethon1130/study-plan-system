package com.learning.model;

import java.io.Serializable;
import java.time.LocalDate;

public class LearningRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int planId;
    private LocalDate date;
    private int hours;
    private String content;
    private String notes;

    public LearningRecord() {
    }

    public LearningRecord(int planId, LocalDate date, int hours, String content, String notes) {
        this.planId = planId;
        this.date = date;
        this.hours = hours;
        this.content = content;
        this.notes = notes;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "LearningRecord{" +
                "id=" + id +
                ", planId=" + planId +
                ", date=" + date +
                ", hours=" + hours +
                ", content='" + content + '\'' +
                '}';
    }
}
