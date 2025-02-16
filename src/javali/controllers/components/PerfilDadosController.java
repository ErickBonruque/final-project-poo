package javali.controllers.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javali.utils.Navigator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javali.utils.SessionManager;
import javali.models.Usuario;
import javali.database.DatabaseManager;

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
    public void initialize() {
        // Carregar os dados do usuário logado
        Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
        emailField.setText(usuario.getEmail());
        senhaField.setText(usuario.getSenha());
    }

    @FXML
    void handleAltBtn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Alteração");
        alert.setHeaderText("Deseja alterar os dados?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
                String novoEmail = emailField.getText();
                String novaSenha = senhaField.getText();
                
                // Verificação de senha forte
                if (novaSenha.length() < 8 || !novaSenha.matches(".*[A-Z].*") || !novaSenha.matches(".*[0-9].*")) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Erro");
                    errorAlert.setHeaderText("Erro ao alterar dados");
                    errorAlert.setContentText("A senha deve ter pelo menos 8 caracteres, uma letra maiúscula e um número");
                    errorAlert.showAndWait();
                    return;
                }

                if (DatabaseManager.editarUsuario(usuario.getId(), usuario.getNome(), novoEmail, novaSenha)) {
                    usuario.setEmail(novoEmail);
                    usuario.setSenha(novaSenha);
                    
                    Alert successAlert = new Alert(AlertType.INFORMATION);
                    successAlert.setTitle("Sucesso");
                    successAlert.setHeaderText("Dados alterados com sucesso!");
                    successAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("Erro");
                    errorAlert.setHeaderText("Erro ao alterar dados");
                    errorAlert.setContentText("Não foi possível alterar os dados. Tente novamente.");
                    errorAlert.showAndWait();
                }
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
                SessionManager.getInstance().clearSession();
                Stage stage = (Stage) logOutButton.getScene().getWindow();
                Navigator.navigateTo(stage, "/resources/fxml/login.fxml");
            } else {
            }
        });
    }

}

