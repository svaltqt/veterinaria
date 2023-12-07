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
import org.springframework.http.HttpStatus;
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
    @FXML
    public TextField petNameField;
    @FXML
    public TextField petTypeField;
    @FXML
    public TextField petBreedField;
    @FXML
    public TextField petAgeField;
    @FXML
    public Button petAddButton;
    @FXML
    public Button petDeleteButton;
    public MenuItem menuAdd;
    @FXML
    public Label petNameLabel;
    @FXML
    public Label petTypeLabel;
    @FXML
    public Label petBreedlabel;
    @FXML
    public Label petAgeLabel;
    @FXML
    public Button petUpdateButton;
    @FXML
    public MenuItem menuDelete;
    public MenuItem menuUpdate;

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
            petAddButton.setVisible(false);
            petDeleteButton.setVisible(false);
            petNameField.setVisible(false);
            petBreedField.setVisible(false);
            petTypeField.setVisible(false);
            petAgeField.setVisible(false);
            petNameLabel.setVisible(false);
            petTypeLabel.setVisible(false);
            petBreedlabel.setVisible(false);
             petAgeLabel.setVisible(false);



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
    @FXML
    public void petsTableViewOnAction(SortEvent<TableView> tableViewSortEvent) {
    }

    @FXML
    public void menuAddOnAction(ActionEvent actionEvent) {
                //Visibilidad de componentes
                petNameField.setVisible(true);
                petBreedField.setVisible(true);
                petTypeField.setVisible(true);
                petAgeField.setVisible(true);
                petNameLabel.setVisible(true);
                petTypeLabel.setVisible(true);
                petBreedlabel.setVisible(true);
                petAgeLabel.setVisible(true);
                petAddButton.setVisible(true);
                loadButton.setVisible(false);
                cleanButton.setVisible(false);
                petsTableView.setVisible(false);
    }

    @FXML
    public void petAddButtonOnAction(ActionEvent actionEvent) {
        try {
            // Vista
            petsTableView.setVisible(true);
            // Obtener los valores de los campos de texto
            String name = petNameField.getText();
            String type = petTypeField.getText();
            String breed = petBreedField.getText();
            int age = Integer.parseInt(petAgeField.getText());

            // Verificar que name, type y breed no sean numéricos
            if (name.matches(".*\\d.*") || type.matches(".*\\d.*") || breed.matches(".*\\d.*")) {
                // Mostrar un popup de error si los campos no se ingresan correctamente
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error en la entrada de datos");
                alert.setContentText("Los campos Name, Type y Breed no deben contener números.");
                alert.showAndWait();
            } else {
                // Crear una nueva instancia de Pet
                Pet pet = new Pet();
                pet.setName(name);
                pet.setType(type);
                pet.setBreed(breed);
                pet.setAge(age);

                // Agregar la nueva mascota
                userService.addPetToUserByEmail(userEmail, pet);

                // Limpiar los campos de texto
                petNameField.clear();
                petTypeField.clear();
                petBreedField.clear();
                petAgeField.clear();

                // Actualizar la lista de mascotas
                loadButtonOnAction(actionEvent);
            }
        } catch (NumberFormatException e) {
            // Mostrar un popup de error si los campos no se ingresan correctamente
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la entrada de datos");
            alert.setContentText("Por favor ingrese una edad válida para la mascota.");
            alert.showAndWait();
        }
    }



    @FXML
    public void menuDeleteOnAction(ActionEvent actionEvent) {
                //Visibilidad de componentes
                petNameField.setVisible(true);
                petBreedField.setVisible(false);
                petTypeField.setVisible(false);
                petAgeField.setVisible(false);
                petNameLabel.setVisible(true);
                petTypeLabel.setVisible(false);
                petBreedlabel.setVisible(false);
                petAgeLabel.setVisible(false);
                petAddButton.setVisible(false);
                loadButton.setVisible(true);
                cleanButton.setVisible(true);
                petsTableView.setVisible(true);
                petDeleteButton.setVisible(true);
    }

    @FXML
    public void petDeleteButtonOnAction(ActionEvent actionEvent) {
        try {
            String petName = petNameField.getText(); // Obtener el nombre de la mascota del campo de texto
            String userEmail = this.userEmail; // Obtener el correo electrónico del usuario actual

            if (petName != null && userEmail != null) {
                // Llamar al método del UserService para eliminar la mascota
                userService.removePetFromUserByEmailAndName(userEmail, petName);

                // Manejar la eliminación exitosa de la mascota
                System.out.println("La mascota se eliminó correctamente.");
            } else {
                // Manejar el caso de petName o userEmail nulos
                System.out.println("Error: El nombre de la mascota o el correo electrónico del usuario no están disponibles.");
            }
            // Actualizar la lista de mascotas
            loadButtonOnAction(actionEvent);
        } catch (Exception e) {
            // Manejar cualquier excepción inesperada
            System.out.println("Error inesperado al intentar eliminar la mascota.");
            e.printStackTrace();
        }
    }
    @FXML
    public void menuUpdateOnAction(ActionEvent actionEvent) {
        //Visibilidad de componentes
        petNameField.setVisible(true);
        petBreedField.setVisible(true);
        petTypeField.setVisible(true);
        petAgeField.setVisible(true);
        petNameLabel.setVisible(true);
        petTypeLabel.setVisible(true);
        petBreedlabel.setVisible(true);
        petAgeLabel.setVisible(true);
        petAddButton.setVisible(false);
        loadButton.setVisible(true);
        cleanButton.setVisible(true);
        petsTableView.setVisible(true);
        petUpdateButton.setVisible(true);
        petDeleteButton.setVisible(false);

    }
    @FXML
    public void petUpdateButtonOnAction(ActionEvent actionEvent) {
    }
}