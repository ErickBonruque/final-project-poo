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
import javali.database.DatabaseManager;
import javali.utils.SessionManager;
import javali.models.Usuario;
public class RegisterController {

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailFild;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField nomeFild;
    @FXML
    void handleCadastroBtn(ActionEvent event) {
        String nome = nomeFild.getText();
        String email = emailFild.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao fazer cadastro");
            alert.setContentText("Email ou senha em branco");
            alert.showAndWait();
        }
        else if (DatabaseManager.emailJaExiste(email)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao fazer cadastro");
            alert.setContentText("Este email já está cadastrado");
            alert.showAndWait();
        }
        else if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao fazer cadastro");
            alert.setContentText("As senhas não conferem");
            alert.showAndWait();
        }
        else if (password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao fazer cadastro");
            alert.setContentText("A senha deve ter pelo menos 8 caracteres, uma letra maiúscula e um número");
            alert.showAndWait();
        }
        else if (DatabaseManager.criarUsuario(nome, email, password)) {
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            Usuario usuario = DatabaseManager.getUsuario(email, password);
            SessionManager.getInstance().setUsuarioLogado(usuario);
            
            Navigator.navigateTo(stage, "/resources/fxml/mainDashBoard.fxml");
        }
    }

    @FXML
    void handleLoginBtn(ActionEvent event) {
        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Navigator.navigateTo(stage, "/resources/fxml/login.fxml");
    }

}
