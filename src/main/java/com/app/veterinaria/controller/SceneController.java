package com.app.veterinaria.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String email;

    public void switchToLogin(String email) throws IOException {
        this.email = email;

         FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
         root = loader.load();
         scene = new Scene(root);
         stage = new Stage();
         stage.setScene(scene);
         stage.initStyle(StageStyle.UNDECORATED);
         MainPanel mainPanel = loader.getController();
         mainPanel.displayEmail(email);

    }

    public void showStage(){
        stage.show();
    }



}
