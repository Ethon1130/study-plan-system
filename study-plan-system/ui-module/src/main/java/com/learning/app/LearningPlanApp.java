package com.learning.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LearningPlanApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // 先显示登录界面
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learning/view/LoginView.fxml")));
        
        // 创建场景并设置CSS样式
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/learning/css/styles.css")).toExternalForm());
        
        // 设置主舞台
        primaryStage.setTitle("学习计划管理系统");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(500);
        primaryStage.setWidth(450);
        primaryStage.setHeight(550);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

