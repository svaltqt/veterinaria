package com.app.veterinaria.controller;

import com.app.veterinaria.model.Pet;
import com.app.veterinaria.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class MainPanel {

    @Autowired
    private UserService userService;
    @FXML
    public Button cancelButton;
    @FXML
    public Label showUserLabel;
    @FXML
    public Button teamButton;
    @FXML
    public MenuItem menuSearch;
    @FXML
    public ListView<String> petsListView;


    private Parent root;
    private Stage devsStage;
    private Scene scene;
    private String userEmail;
    //Stage DevsStage = new Stage();



    public void displayEmail(String email){
        showUserLabel.setText("Email: " +  email);
        userEmail = email;
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void teamButtonOnAction(ActionEvent e){
        callScene("/view/Developers.fxml");
        this.devsStage = new Stage();


    }

    public void callScene(String ruta){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            root = loader.load();
            scene = new Scene(root);
            devsStage = new Stage();
            devsStage.setScene(scene);
            devsStage.initStyle(StageStyle.UNDECORATED);
            devsStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchMenuOnAction(ActionEvent e)
    {
    }


}


