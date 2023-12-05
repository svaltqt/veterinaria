package com.app.veterinaria.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainPanel {


    @FXML
    public Button cancelButton;
    @FXML
    public Label showUserLabel;
    @FXML
    public Button teamButton;

    private Parent root;
    private Stage stage;
    private Scene scene;
    Stage DevsStage = new Stage();


    public void displayEmail(String email){
        showUserLabel.setText("Email: " +  email);
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void teamButtonOnAction(ActionEvent e){
        callScene("/view/Developers.fxml");


    }

    public void callScene(String ruta){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            root = loader.load();
            scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}


