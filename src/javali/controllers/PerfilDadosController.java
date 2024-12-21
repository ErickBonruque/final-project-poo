package javali.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javali.utils.Navigator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class PerfilDadosController {
    
    @FXML
    private Button alterarBtn;

    @FXML
    private TextField emailField;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField senhaField;

    @FXML
    void handleAltBtn(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Alteração");
        alert.setHeaderText("Deseja alterar os dados?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            } else {
            }
        });
    }

    @FXML
    void handleLogOutBtn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Saida");
        alert.setHeaderText("Deseja realmente sair?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Stage stage = (Stage) logOutButton.getScene().getWindow();
                Navigator.navigateTo(stage, "/resources/fxml/login.fxml");
            } else {
            }
        });
    }

}

