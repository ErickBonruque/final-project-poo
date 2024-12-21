package javali.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class AtualizarAppDadosController {
    @FXML
    private Button atualizarBtn;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField notasField;

    @FXML
    private TextField senhaField;

    @FXML
    private TextField webSiteField;

    @FXML
    void handleAtualizarBtn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Alteração");
        alert.setHeaderText("Deseja alterar os dados?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
            } else {
            }
        });
    }

    public void setInfo(String name, String email, String senha, String site, String notes) {
        nameField.setText(name);
        emailField.setText(email);
        senhaField.setText(senha);
        webSiteField.setText(site);
        notasField.setText(notes);
    }

}

