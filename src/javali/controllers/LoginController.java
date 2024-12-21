package javali.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javali.utils.Navigator;

public class LoginController {

    @FXML
    private Button cadastroBtn;

    @FXML
    private TextField emailFild;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    void handleCadastroBtn(ActionEvent event) {
        Stage stage = (Stage) cadastroBtn.getScene().getWindow();
        Navigator.navigateTo(stage, "/resources/fxml/register.fxml");
    }

    @FXML
    void handleLoginBtn(ActionEvent event) {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Navigator.navigateTo(stage, "/resources/fxml/mainDashboard.fxml");
    }

}