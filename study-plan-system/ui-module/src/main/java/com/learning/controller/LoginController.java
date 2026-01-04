package com.learning.controller;

import com.learning.model.ApiResponse;
import com.learning.model.LoginResponse;
import com.learning.service.ApiService;
import com.learning.utils.HttpClientUtil;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private CheckBox rememberMeCheckBox;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    public void initialize() {
        // 绑定事件
        loginButton.setOnAction(this::handleLogin);
        registerButton.setOnAction(this::handleRegister);
        
        // 回车键登录
        passwordField.setOnAction(this::handleLogin);
        usernameField.setOnAction(this::handleLogin);
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (username.isEmpty()) {
            showError("请输入用户名");
            return;
        }
        
        if (password.isEmpty()) {
            showError("请输入密码");
            return;
        }
        
        // 禁用登录按钮
        loginButton.setDisable(true);
        errorLabel.setVisible(false);
        
        // 执行登录
        Task<ApiResponse<LoginResponse>> loginTask = ApiService.login(username, password);
        loginTask.setOnSucceeded(e -> {
            ApiResponse<LoginResponse> response = loginTask.getValue();
            loginButton.setDisable(false);
            
            if (response.isSuccess()) {
                LoginResponse loginResponse = response.getData();
                String token = loginResponse.getToken();
                HttpClientUtil.setToken(token);
                System.out.println("Token saved: " + token.substring(0, Math.min(50, token.length())) + "...");
                
                // 登录成功，跳转到主界面
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/learning/view/MainView.fxml"));
                    Parent root = loader.load();
                    
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/com/learning/css/styles.css").toExternalForm());
                    
                    stage.setTitle("学习计划管理系统 - " + loginResponse.getUsername());
                    stage.setScene(scene);
                    stage.setMinWidth(800);
                    stage.setMinHeight(600);
                    stage.setWidth(1000);
                    stage.setHeight(700);
                    stage.setResizable(true);
                    stage.show();
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showError("加载主界面失败");
                }
            } else {
                showError(response.getMsg() != null ? response.getMsg() : "登录失败");
            }
        });
        
        loginTask.setOnFailed(e -> {
            loginButton.setDisable(false);
            
            // 提供离线演示模式选项
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("网络连接失败");
            alert.setHeaderText("无法连接到服务器");
            alert.setContentText("网络连接失败，是否进入演示模式体验界面？\n\n演示模式下的数据不会保存到服务器。");
            
            ButtonType demoMode = new ButtonType("进入演示模式");
            ButtonType retry = new ButtonType("重试", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(demoMode, retry);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == demoMode) {
                    // 进入演示模式
                    enterDemoMode();
                }
            });
        });
        
        // 在后台线程执行
        Thread thread = new Thread(loginTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/learning/view/RegisterView.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/learning/css/styles.css").toExternalForm());
            
            stage.setTitle("用户注册");
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("加载注册界面失败");
        }
    }
    
    private void enterDemoMode() {
        try {
            // 直接进入主界面，不需要真实登录
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/learning/view/MainView.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/learning/css/styles.css").toExternalForm());
            
            stage.setTitle("学习计划管理系统 - 演示模式");
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.setWidth(1000);
            stage.setHeight(700);
            stage.setResizable(true);
            stage.show();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("加载演示模式失败");
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
