package com.app.veterinaria;

import com.app.veterinaria.controller.LoginPanel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


import java.io.IOException;


@SpringBootApplication
public class VetApp extends Application {

	private ConfigurableApplicationContext springContext;
	private Parent rootNode;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		// Inicializa el contexto de Spring Boot antes de iniciar la aplicación JavaFX
		springContext = SpringApplication.run(VetApp.class);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
		rootNode = loader.load();

		// Obtén el controlador y configura el contexto de Spring manualmente
		LoginPanel loginPanel = loader.getController();
		loginPanel.setSpringContext(springContext);

		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(new Scene(rootNode, 600, 400));
		primaryStage.show();
	}

	@Override
	public void stop() {
		// Detiene el contexto de Spring Boot al cerrar la aplicación JavaFX
		springContext.close();
	}
}
