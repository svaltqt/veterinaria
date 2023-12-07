package com.app.veterinaria.controller;


import com.app.veterinaria.model.Pet;
import com.app.veterinaria.service.UserService;
import jakarta.annotation.PostConstruct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Controller
@Service
public class MainPanel {

    @Autowired
    private UserService userService;

    public MainPanel() {
    }

    @PostConstruct
    public void init() {
        this.userService = userService;
    }

    @FXML
    public Button loadButton;
    @FXML
    public Button cleanButton;
    @FXML
    private TableView<Pet> petsTableView;

    @FXML
    public Button cancelButton;
    @FXML
    public Label showUserLabel;
    @FXML
    public Button teamButton;
    @FXML
    public MenuItem menuSearch;
    private Parent root;
    private Stage devsStage;
    private Scene scene;
    private String userEmail;

    private List<String> pets;



    @FXML
    public void displayEmail(String email){
        showUserLabel.setText("Email: " +  email);
        userEmail = email;
    }
    @FXML
    public void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    public void teamButtonOnAction(ActionEvent e){
        callScene("/view/Developers.fxml");
        this.devsStage = new Stage();


    }
    @FXML
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
    @FXML
    public void searchMenuOnAction(ActionEvent e) {
        try {


            // Hacer la ListView visible

            loadButton.setVisible(true);
            cleanButton.setVisible(true);
            petsTableView.setVisible(true);

        } catch (Exception ex) {
            // Manejar la excepción de manera adecuada
            System.out.println("An error occurred. Please try again later.");
            ex.printStackTrace();
        }
    }
    public void initialize() {
        // Configurar columnas y encabezados para la TableView
        TableColumn<Pet, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pet, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Pet, String> breedColumn = new TableColumn<>("Breed");
        breedColumn.setCellValueFactory(new PropertyValueFactory<>("breed"));

        TableColumn<Pet, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        List<TableColumn<Pet, ?>> columns = new ArrayList<>();
        columns.add(nameColumn);
        columns.add(typeColumn);
        columns.add(breedColumn);
        columns.add(ageColumn);

        // Agregar las columnas a la TableView
        petsTableView.getColumns().setAll(columns);
    }

    /** public void loadButtonOnAction(ActionEvent event) {
     // Obtener el correo electrónico del usuario actual usando el valor almacenado en userEmail
     String userEmail = this.userEmail;

     List<Pet> pets = new ArrayList<>();

     Pet pet1 = new Pet();
     pet1.setName("Max");
     pet1.setType("Dog");
     pet1.setBreed("Labrador");
     pet1.setAge(3);

     Pet pet2 = new Pet();
     pet2.setName("Rocky");
     pet2.setType("Cat");
     pet2.setBreed("Siamese");
     pet2.setAge(2);

     Pet pet3 = new Pet();
     pet3.setName("Cooper");
     pet3.setType("Dog");
     pet3.setBreed("Golden Retriever");
     pet3.setAge(4);

     pets.add(pet1);
     pets.add(pet2);
     pets.add(pet3);

     if (userEmail != null && !userEmail.isEmpty()) {
     // Crear lista de prueba de objetos Pet

     ObservableList<Pet> petsList = FXCollections.observableArrayList(pets);

     // Wrap en ObservableList para usar en JavaFX
     //ObservableList<Pet> petsList = FXCollections.observableArrayList(pets);

     // Asignar al TableView
     petsTableView.setItems(petsList);
     } else {
     // Manejar caso de correo electrónico nulo o vacío
     System.out.println("Error: El correo electrónico del usuario no está disponible.");
     }
     }
     **/

    @FXML
    public void loadButtonOnAction(ActionEvent event) {
        // Obtener el correo electrónico del usuario actual usando el valor almacenado en userEmail
        String userEmail = this.userEmail;

        if (userEmail != null && !userEmail.isEmpty()) {
            // Llamar al servicio para obtener la información de las mascotas
            List<Pet> pets = userService.getAllPetsInfoByEmail(userEmail);

            // Wrap en ObservableList para usar en JavaFX
            ObservableList<Pet> petsList = FXCollections.observableArrayList(pets);

            // Asignar al TableView
            petsTableView.setItems(petsList);
        } else {
            // Manejar caso de correo electrónico nulo o vacío
            System.out.println("Error: El correo electrónico del usuario no está disponible.");
        }
    }



    @FXML
    public void cleanButtonOnAction(ActionEvent e) {
        petsTableView.getItems().clear();
    }

    public void petsTableViewOnAction(SortEvent<TableView> tableViewSortEvent) {
    }
}