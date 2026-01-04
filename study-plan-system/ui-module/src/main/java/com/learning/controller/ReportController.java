package com.learning.controller;

import com.learning.model.ApiResponse;
import com.learning.model.ReportData;
import com.learning.service.ApiService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ReportController {
    
    @FXML
    private Label totalPlansLabel;
    
    @FXML
    private Label completedPlansLabel;
    
    @FXML
    private Label inProgressPlansLabel;
    
    @FXML
    private Label completionRateLabel;
    
    @FXML
    private Label totalHoursLabel;
    
    @FXML
    private Label completedHoursLabel;
    
    @FXML
    private Label efficiencyLabel;
    
    @FXML
    private ProgressBar overallProgressBar;
    
    @FXML
    private Label progressText;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Button exportButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    public void initialize() {
        // 绑定事件
        refreshButton.setOnAction(this::handleRefresh);
        exportButton.setOnAction(this::handleExport);
        backButton.setOnAction(this::handleBack);
        
        // 初始加载数据
        loadStatistics();
    }
    
    @FXML
    private void handleRefresh(ActionEvent event) {
        loadStatistics();
    }
    
    @FXML
    private void handleExport(ActionEvent event) {
        exportButton.setDisable(true);
        
        Task<Void> exportTask = ApiService.exportExcel();
        
        exportTask.setOnSucceeded(e -> {
            exportButton.setDisable(false);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("导出成功");
            alert.setHeaderText(null);
            alert.setContentText("报表已成功导出！\n\n文件已保存到您的下载文件夹。");
            alert.showAndWait();
        });
        
        exportTask.setOnFailed(e -> {
            exportButton.setDisable(false);
            
            Throwable exception = exportTask.getException();
            String errorMsg = exception != null ? exception.getMessage() : "未知错误";
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("导出失败");
            alert.setHeaderText(null);
            alert.setContentText("导出报表时发生错误：\n" + errorMsg);
            alert.showAndWait();
        });
        
        Thread thread = new Thread(exportTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/learning/view/MainView.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/learning/css/styles.css").toExternalForm());
            
            stage.setTitle("学习计划管理系统");
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("返回主页失败");
        }
    }
    
    private void loadStatistics() {
        refreshButton.setDisable(true);
        
        Task<ApiResponse<ReportData>> loadTask = ApiService.getStatistics();
        loadTask.setOnSucceeded(e -> {
            ApiResponse<ReportData> response = loadTask.getValue();
            refreshButton.setDisable(false);
            
            if (response.isSuccess()) {
                ReportData data = response.getData();
                updateUI(data);
            } else {
                showError("加载统计数据失败: " + response.getMsg());
            }
        });
        
        loadTask.setOnFailed(e -> {
            refreshButton.setDisable(false);
            // 加载演示数据
            loadDemoReportData();
            showAlert(Alert.AlertType.WARNING, "离线模式", 
                "无法连接到服务器，显示演示统计数据。\n" +
                "实际数据需要网络连接。");
        });
        
        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    private void updateUI(ReportData data) {
        if (data == null) {
            // 显示默认值
            totalPlansLabel.setText("0");
            completedPlansLabel.setText("0");
            inProgressPlansLabel.setText("0");
            completionRateLabel.setText("0%");
            totalHoursLabel.setText("0 小时");
            completedHoursLabel.setText("0 小时");
            efficiencyLabel.setText("无数据");
            overallProgressBar.setProgress(0);
            progressText.setText("0%");
            return;
        }
        
        // 更新统计数据
        // 计算总计划数（待办 + 逾期）
        int todoCount = data.getTodoCount() != null ? data.getTodoCount() : 0;
        int overdueCount = data.getOverdueCount() != null ? data.getOverdueCount() : 0;
        int totalPlans = todoCount + overdueCount;
        
        totalPlansLabel.setText(String.valueOf(totalPlans));
        completedPlansLabel.setText("0"); // 后端没有返回已完成数量，暂时显示0
        inProgressPlansLabel.setText(String.valueOf(todoCount));
        
        double completionRate = data.getCompletionPercentage();
        completionRateLabel.setText(String.format("%.1f%%", completionRate));
        
        // 暂时显示待办和逾期数量
        totalHoursLabel.setText(String.format("待办: %d", todoCount));
        completedHoursLabel.setText(String.format("逾期: %d", overdueCount));
        
        // 计算学习效率
        String efficiency = "良好";
        if (completionRate >= 80) {
            efficiency = "优秀";
        } else if (completionRate >= 60) {
            efficiency = "良好";
        } else if (completionRate >= 40) {
            efficiency = "一般";
        } else {
            efficiency = "需要改进";
        }
        efficiencyLabel.setText(efficiency);
        
        // 更新进度条
        double progress = completionRate / 100.0;
        overallProgressBar.setProgress(progress);
        progressText.setText(String.format("%.1f%%", completionRate));
    }
    
    private void loadDemoReportData() {
        ReportData demoData = new ReportData();
        demoData.setCompletionRate(0.33);
        demoData.setTodoCount(131);
        demoData.setOverdueCount(16);
        
        updateUI(demoData);
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
