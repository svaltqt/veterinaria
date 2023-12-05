package com.app.veterinaria.controller;

import com.app.veterinaria.model.User;
import com.app.veterinaria.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class LoginPanel {

    @FXML
    private TextField emailfield;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField passwordfield;
    private SceneController sceneController;

    @Autowired
    private UserService userService;

    public void setSpringContext(ConfigurableApplicationContext springContext) {
        this.userService = springContext.getBean(UserService.class);
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    public void loginButtonOnAction(ActionEvent e) {
        String email = emailfield.getText();
        String password = passwordfield.getText();

        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                User user = userService.validateUser(email, password);
                // Aquí puedes procesar la respuesta del servidor y realizar acciones según sea necesario
                if (user != null) {
                    LoginMessageLabel.setText("Login successful!");

                    if (sceneController != null) {
                        sceneController.switchToLogin(email);
                        sceneController.showStage();
                    } else {
                        System.err.println("Error: SceneController is null.");
                    }
                } else {
                    LoginMessageLabel.setText("Login failed. Please check your credentials.");
                }
            } catch (Exception ex) {
                LoginMessageLabel.setText("An error occurred. Please try again later.");
                ex.printStackTrace(); // Manejar la excepción de manera adecuada
            }
        } else {
            LoginMessageLabel.setText("Please enter your username and password!");
        }
    }

    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
