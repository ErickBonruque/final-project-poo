package javali.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javali.utils.Navigator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javali.models.Usuario;
import javali.database.DatabaseManager;
import javali.utils.SessionManager;

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
        String email = emailFild.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao fazer login");
            alert.setContentText("Email ou senha em branco");
            alert.showAndWait();
            return;
        }

        if (!DatabaseManager.validarLogin(email, password)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao fazer login");
            alert.setContentText("Email ou senha inválidos");
            alert.showAndWait();
            return;
        }
        else {
            Usuario usuario = DatabaseManager.getUsuario(email, password);
            SessionManager.getInstance().setUsuarioLogado(usuario);
            
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Navigator.navigateTo(stage, "/resources/fxml/mainDashboard.fxml");
        }
    }

}