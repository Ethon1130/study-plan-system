package com.learning.controller;

import com.learning.model.ApiPlan;
import com.learning.model.ApiResponse;
import com.learning.model.PageResult;
import com.learning.service.ApiService;
import com.learning.utils.HttpClientUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

public class MainController {

    @FXML
    private TextField planTitleField;
    @FXML
    private TextArea planDescriptionArea;
    @FXML
    private DatePicker planStartDatePicker;
    @FXML
    private DatePicker planEndDatePicker;
    @FXML
    private ComboBox<String> planSubjectComboBox;
    @FXML
    private TableView<ApiPlan> plansTable;
    @FXML
    private TableColumn<ApiPlan, String> planTitleColumn;
    @FXML
    private TableColumn<ApiPlan, String> planSubjectColumn;
    @FXML
    private TableColumn<ApiPlan, String> planTotalHoursColumn;
    @FXML
    private TableColumn<ApiPlan, String> planCompletedHoursColumn;
    @FXML
    private TableColumn<ApiPlan, String> planProgressColumn;
    @FXML
    private TableColumn<ApiPlan, String> planStatusColumn;
    @FXML
    private Button addPlanBtn;
    @FXML
    private Button deletePlanBtn;
    @FXML
    private Label selectedPlanLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label priorityLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressText;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button aiCoachButton;
    @FXML
    private Button reportButton;
    @FXML
    private Button logoutButton;

    private ObservableList<ApiPlan> plansList = FXCollections.observableArrayList();
    private ApiPlan selectedPlan;
    private int currentPage = 1;
    private int pageSize = 10;
    private Long totalPlans = 0L;

    @FXML
    public void initialize() {
        // Initialize date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        // Initialize priority options
        planSubjectComboBox.setItems(FXCollections.observableArrayList(
                "高", "普通", "低"
        ));
        planSubjectComboBox.getSelectionModel().select(1); // 默认选择"普通"

        // Initialize table columns for ApiPlan
        planTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        planSubjectColumn.setCellValueFactory(cellData -> {
            ApiPlan plan = cellData.getValue();
            return new SimpleStringProperty(plan.getPriorityText());
        });
        planTotalHoursColumn.setCellValueFactory(cellData -> {
            ApiPlan plan = cellData.getValue();
            return new SimpleStringProperty(plan.getStartTime() != null ? 
                plan.getStartTime().toLocalDate().toString() : "");
        });
        planCompletedHoursColumn.setCellValueFactory(cellData -> {
            ApiPlan plan = cellData.getValue();
            return new SimpleStringProperty(plan.getEndTime() != null ? 
                plan.getEndTime().toLocalDate().toString() : "");
        });
        planProgressColumn.setCellValueFactory(cellData -> {
            ApiPlan plan = cellData.getValue();
            Integer progress = plan.getProgress() != null ? plan.getProgress() : 0;
            return new SimpleStringProperty(progress + "%");
        });
        planStatusColumn.setCellValueFactory(cellData -> {
            ApiPlan plan = cellData.getValue();
            return new SimpleStringProperty(plan.getStatusText());
        });

        // Set date format for date pickers
        StringConverter<LocalDate> dateConverter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };

        planStartDatePicker.setConverter(dateConverter);
        planEndDatePicker.setConverter(dateConverter);
        // Set default dates
        planStartDatePicker.setValue(LocalDate.now());
        planEndDatePicker.setValue(LocalDate.now().plusMonths(1));
        
        // Set table data
        plansTable.setItems(plansList);

        // Load data from API
        loadPlansFromAPI();
        
        // 绑定按钮事件
        addPlanBtn.setOnAction(this::handleAddPlan);
        deletePlanBtn.setOnAction(this::handleDeletePlan);
        aiCoachButton.setOnAction(this::handleAiCoach);
        reportButton.setOnAction(this::handleReport);
        logoutButton.setOnAction(this::handleLogout);
        
        // 绑定表格点击事件
        plansTable.setOnMouseClicked(this::handlePlanTableClick);
        
    }

    @FXML
    void handleAddPlan(ActionEvent event) {
        try {
            String title = planTitleField.getText().trim();
            String description = planDescriptionArea.getText().trim();
            LocalDate startDate = planStartDatePicker.getValue();
            LocalDate endDate = planEndDatePicker.getValue();
            String priorityText = planSubjectComboBox.getValue();
            
            int priority = 2; // 默认普通
            if ("高".equals(priorityText)) priority = 1;
            else if ("低".equals(priorityText)) priority = 3;

            if (title.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "错误", "请输入计划标题");
                return;
            }

            if (startDate == null || endDate == null) {
                showAlert(Alert.AlertType.ERROR, "错误", "请选择开始日期和结束日期");
                return;
            }

            if (startDate.isAfter(endDate)) {
                showAlert(Alert.AlertType.ERROR, "错误", "开始日期不能晚于结束日期");
                return;
            }

            // 创建ApiPlan对象
            ApiPlan newPlan = new ApiPlan(title, description, 
                startDate.atStartOfDay(), endDate.atStartOfDay(), priority);
            
            // 设置当前用户ID（从token中获取或使用默认值）
            newPlan.setUserId(getCurrentUserId());
            
            // 调用API创建计划
            Task<ApiResponse<Long>> createTask = ApiService.createPlan(newPlan);
            createTask.setOnSucceeded(e -> {
                ApiResponse<Long> response = createTask.getValue();
                addPlanBtn.setDisable(false);
                
                if (response.isSuccess()) {
                    clearPlanFields();
                    showAlert(Alert.AlertType.INFORMATION, "成功", "学习计划添加成功");
                    loadPlansFromAPI(); // 重新加载计划列表
                } else {
                    showAlert(Alert.AlertType.ERROR, "错误", 
                        response.getMsg() != null ? response.getMsg() : "添加计划失败");
                }
            });
            
            createTask.setOnFailed(e -> {
                addPlanBtn.setDisable(false);
                showAlert(Alert.AlertType.WARNING, "离线模式", 
                    "当前处于离线模式，无法保存到服务器。\n" +
                    "请检查网络连接后重试。");
            });
            
            // 在后台线程执行
            Thread thread = new Thread(createTask);
            thread.setDaemon(true);
            thread.start();

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "错误", "添加计划时发生错误");
        }
    }

    @FXML
    void handleDeletePlan(ActionEvent event) {
        ApiPlan selected = plansTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("确认删除");
            alert.setHeaderText(null);
            alert.setContentText("确定要删除这个学习计划吗？");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                deletePlanBtn.setDisable(true);
                
                // 调用API删除计划
                Task<ApiResponse<Void>> deleteTask = ApiService.deletePlans(selected.getId().toString());
                deleteTask.setOnSucceeded(e -> {
                    ApiResponse<Void> response = deleteTask.getValue();
                    deletePlanBtn.setDisable(false);
                    
                    if (response.isSuccess()) {
                        if (selected == selectedPlan) {
                            selectedPlan = null;
                            updateSelectedPlanDetails();
                        }
                        showAlert(Alert.AlertType.INFORMATION, "成功", "学习计划删除成功");
                        loadPlansFromAPI(); // 重新加载计划列表
                    } else {
                        showAlert(Alert.AlertType.ERROR, "错误", 
                            response.getMsg() != null ? response.getMsg() : "删除计划失败");
                    }
                });
                
                deleteTask.setOnFailed(e -> {
                    deletePlanBtn.setDisable(false);
                    showAlert(Alert.AlertType.WARNING, "离线模式", 
                        "当前处于离线模式，无法从服务器删除。\n" +
                        "请检查网络连接后重试。");
                });
                
                Thread thread = new Thread(deleteTask);
                thread.setDaemon(true);
                thread.start();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "警告", "请先选择一个学习计划");
        }
    }

    @FXML
    void handlePlanTableClick(MouseEvent event) {
        ApiPlan selected = plansTable.getSelectionModel().getSelectedItem();
        if (selected != null && selected != selectedPlan) {
            selectedPlan = selected;
            selectedPlanLabel.setText(selected.getTitle());
            updateSelectedPlanDetails();
        }
    }

    private void updateSelectedPlanDetails() {
        if (selectedPlan != null) {
            // 更新基本信息
            selectedPlanLabel.setText(selectedPlan.getTitle());
            
            // 更新状态信息
            statusLabel.setText(selectedPlan.getStatusText());
            priorityLabel.setText(selectedPlan.getPriorityText());
            
            // 更新日期信息
            startDateLabel.setText(selectedPlan.getStartTime() != null ? 
                selectedPlan.getStartTime().toLocalDate().toString() : "未设置");
            endDateLabel.setText(selectedPlan.getEndTime() != null ? 
                selectedPlan.getEndTime().toLocalDate().toString() : "未设置");
            
            // 更新进度信息
            double progress = selectedPlan.getProgress() != null 
                    ? selectedPlan.getProgress().doubleValue() / 100.0 
                    : 0.0;
            progressBar.setProgress(progress);
            progressText.setText(selectedPlan.getProgress() + "%");
            
            // 更新描述信息
            descriptionLabel.setText(selectedPlan.getDescription() != null && 
                !selectedPlan.getDescription().trim().isEmpty() ? 
                selectedPlan.getDescription() : "暂无描述");
        } else {
            // 清空所有信息
            selectedPlanLabel.setText("未选择学习计划");
            statusLabel.setText("-");
            priorityLabel.setText("-");
            startDateLabel.setText("-");
            endDateLabel.setText("-");
            progressBar.setProgress(0);
            progressText.setText("0%");
            descriptionLabel.setText("暂无描述");
        }
    }

    private void clearPlanFields() {
        planTitleField.clear();
        planDescriptionArea.clear();
        planStartDatePicker.setValue(LocalDate.now());
        planEndDatePicker.setValue(LocalDate.now().plusMonths(1));
        planSubjectComboBox.getSelectionModel().selectFirst();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleAiCoach(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/learning/view/AiCoachView.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) aiCoachButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/learning/css/styles.css").toExternalForm());
            
            stage.setTitle("AI 助手");
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "打开 AI 助手界面失败");
        }
    }
    
    @FXML
    private void handleReport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/learning/view/ReportView.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) reportButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/learning/css/styles.css").toExternalForm());
            
            stage.setTitle("学习统计报表");
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "打开报表界面失败");
        }
    }
    
    @FXML
    private void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认退出");
        alert.setHeaderText(null);
        alert.setContentText("确定要退出登录吗？");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            // 清除token
            HttpClientUtil.setToken(null);
            
            try {
                // 返回登录界面
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/learning/view/LoginView.fxml"));
                Parent root = loader.load();
                
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/com/learning/css/styles.css").toExternalForm());
                
                stage.setTitle("学习计划管理系统");
                stage.setScene(scene);
                stage.setMinWidth(400);
                stage.setMinHeight(500);
                stage.setWidth(450);
                stage.setHeight(550);
                stage.setResizable(false);
                stage.show();
                
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "错误", "退出登录失败");
            }
        }
    }

    private void loadPlansFromAPI() {
        Task<ApiResponse<PageResult<ApiPlan>>> loadTask = ApiService.getPlans(currentPage, pageSize);
        loadTask.setOnSucceeded(e -> {
            ApiResponse<PageResult<ApiPlan>> response = loadTask.getValue();
            if (response.isSuccess()) {
                PageResult<ApiPlan> pageResult = response.getData();
                plansList.clear();
                if (pageResult != null && pageResult.getRecords() != null) {
                    plansList.addAll(pageResult.getRecords());
                    totalPlans = pageResult.getTotal();
                } else {
                    totalPlans = 0L;
                }
            } else {
                // 后端返回错误，切换到演示模式
                System.out.println("Backend returned error: " + response.getMsg() + ", switching to demo mode");
                System.out.println("Current thread: " + Thread.currentThread().getName());
                
                // 立即加载演示数据
                loadDemoData();
                
                // 显示提示信息
                javafx.application.Platform.runLater(() -> {
                    System.out.println("Showing demo mode alert...");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("演示模式");
                    alert.setHeaderText("后端服务暂时繁忙");
                    alert.setContentText("已自动切换到演示模式，您可以正常使用所有功能。\n\n" +
                                       "演示数据仅用于界面展示，如需使用真实数据请稍后重试。");
                    alert.showAndWait();
                });
            }
        });
        
        loadTask.setOnFailed(e -> {
            Throwable exception = loadTask.getException();
            System.err.println("Load plans task failed: " + (exception != null ? exception.getMessage() : "Unknown error"));
            // 后端服务异常或网络问题时加载示例数据
            loadDemoData();
            showAlert(Alert.AlertType.WARNING, "服务异常", 
                "后端服务暂时不可用，已切换到演示模式。\n" +
                "演示数据仅用于界面展示，请联系管理员检查服务状态。");
        });
        
        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    private void loadDemoData() {
        System.out.println("Loading demo data...");
        
        // 确保在JavaFX UI线程中执行
        javafx.application.Platform.runLater(() -> {
            plansList.clear();
            
            // 创建示例计划数据
            ApiPlan plan1 = new ApiPlan();
            plan1.setId(1L);
            plan1.setTitle("【演示】Java基础学习");
            plan1.setDescription("学习Java基础语法、面向对象编程、集合框架等核心概念（演示数据）");
            plan1.setPriority(1); // 高优先级
            plan1.setStatus(1); // 进行中
            plan1.setProgress(75);
            plan1.setStartTime(LocalDateTime.now().minusDays(10));
            plan1.setEndTime(LocalDateTime.now().plusDays(20));
            
            ApiPlan plan2 = new ApiPlan();
            plan2.setId(2L);
            plan2.setTitle("【演示】Spring框架学习");
            plan2.setDescription("深入学习Spring Framework，包括IoC、AOP、Spring Boot等（演示数据）");
            plan2.setPriority(2); // 普通优先级
            plan2.setStatus(0); // 未开始
            plan2.setProgress(0);
            plan2.setStartTime(LocalDateTime.now().plusDays(5));
            plan2.setEndTime(LocalDateTime.now().plusDays(45));
            
            ApiPlan plan3 = new ApiPlan();
            plan3.setId(3L);
            plan3.setTitle("【演示】数据库设计");
            plan3.setDescription("学习MySQL数据库设计、SQL优化、事务处理（演示数据）");
            plan3.setPriority(1); // 高优先级
            plan3.setStatus(2); // 已完成
            plan3.setProgress(100);
            plan3.setStartTime(LocalDateTime.now().minusDays(30));
            plan3.setEndTime(LocalDateTime.now().minusDays(5));
            
            plansList.addAll(Arrays.asList(plan1, plan2, plan3));
            totalPlans = 3L;
            
            System.out.println("Demo data loaded: " + plansList.size() + " plans");
            System.out.println("Plans in list: ");
            for (ApiPlan p : plansList) {
                System.out.println("- " + p.getTitle());
            }
            
            // 强制刷新表格视图
            plansTable.refresh();
            
            // 如果没有选中计划，选中第一个
            if (selectedPlan == null && !plansList.isEmpty()) {
                plansTable.getSelectionModel().selectFirst();
                selectedPlan = plansList.get(0);
                updateSelectedPlanDetails();
            }
            
            System.out.println("Table items count: " + plansTable.getItems().size());
            System.out.println("Selected plan: " + (selectedPlan != null ? selectedPlan.getTitle() : "null"));
        });
    }
    
    private Long getCurrentUserId() {
        // 从HttpClientUtil获取token并解析用户ID
        // 这里简化处理，实际应该解析JWT token
        String token = HttpClientUtil.getToken();
        if (token != null && !token.isEmpty()) {
            // 简化：假设用户ID为1，实际应该解析JWT
            return 1L;
        }
        return 1L; // 默认用户ID
    }
}
