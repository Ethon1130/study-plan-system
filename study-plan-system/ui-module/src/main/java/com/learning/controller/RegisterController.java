package com.learning.controller;

import com.learning.model.ApiResponse;
import com.learning.service.ApiService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    public void initialize() {
        // 绑定事件
        registerButton.setOnAction(this::handleRegister);
        cancelButton.setOnAction(this::handleCancel);
        
        // 回车键注册
        confirmPasswordField.setOnAction(this::handleRegister);
    }
    
    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // 验证输入
        if (username.isEmpty()) {
            showError("请输入用户名");
            return;
        }
        
        if (email.isEmpty()) {
            showError("请输入邮箱地址");
            return;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("请输入有效的邮箱地址");
            return;
        }
        
        if (password.isEmpty()) {
            showError("请输入密码");
            return;
        }
        
        if (password.length() < 6) {
            showError("密码长度至少6位");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showError("两次输入的密码不一致");
            return;
        }
        
        // 禁用注册按钮
        registerButton.setDisable(true);
        errorLabel.setVisible(false);
        
        // 执行注册
        Task<ApiResponse<Void>> registerTask = ApiService.register(username, password, email);
        registerTask.setOnSucceeded(e -> {
            ApiResponse<Void> response = registerTask.getValue();
            registerButton.setDisable(false);
            
            if (response.isSuccess()) {
                // 注册成功，显示成功信息并关闭窗口
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("注册成功");
                alert.setHeaderText(null);
                alert.setContentText("注册成功！请使用新账户登录。");
                alert.showAndWait();
                
                // 关闭注册窗口
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            } else {
                showError(response.getMsg() != null ? response.getMsg() : "注册失败");
            }
        });
        
        registerTask.setOnFailed(e -> {
            registerButton.setDisable(false);
            showError("网络连接失败，请检查网络设置");
        });
        
        // 在后台线程执行
        Thread thread = new Thread(registerTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    @FXML
    private void handleCancel(ActionEvent event) {
        // 关闭注册窗口
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
