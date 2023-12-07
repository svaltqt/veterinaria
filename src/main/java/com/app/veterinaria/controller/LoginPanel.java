package com.app.veterinaria.controller;

import com.app.veterinaria.model.User;
import com.app.veterinaria.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private Stage stage;

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigurableApplicationContext springContext;

    public void setSpringContext(ConfigurableApplicationContext springContext) {
        this.userService = springContext.getBean(UserService.class);    }



    public void loginButtonOnAction(ActionEvent e) {
        String email = emailfield.getText();
        String password = passwordfield.getText();

        if (!email.isEmpty() && !password.isEmpty()) {
            try {
                User user = userService.validateUser(email, password);
                // Aquí puedes procesar la respuesta del servidor y realizar acciones según sea necesario

                if (user != null) {
                    LoginMessageLabel.setText("Login successful!");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
                    loader.setControllerFactory(springContext::getBean); // Usar el contexto de Spring para crear el controlador
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    // Obtén el Stage actual desde el evento
                    this.stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    this.stage.setScene(scene);

                    MainPanel mainPanel = loader.getController(); // Obtén el controlador de FXML del cargador
                    mainPanel.displayEmail(email); // Configurar MainPanel


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
