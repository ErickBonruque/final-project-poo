package javali.controllers.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javali.utils.SessionManager;
import javali.models.Usuario;
import javali.models.AppModel;
import java.sql.Date;
import java.util.Calendar;
import javali.database.DatabaseManager;

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

    private int currentAppId;

    @FXML
    void handleAtualizarBtn(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Alteração");
        alert.setHeaderText("Deseja alterar os dados?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String name = nameField.getText();
                String email = emailField.getText();
                String senha = senhaField.getText();
                String site = webSiteField.getText();
                String notes = notasField.getText();
                
                // Criar objeto AppModel com os dados atualizados
                Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
                AppModel appAtualizado = new AppModel(name, email, senha, site, notes, currentDate);
                appAtualizado.setId(currentAppId);
                
                if (DatabaseManager.editarApp(appAtualizado)) {
                    // Atualizar a lista de apps do usuário logado
                    Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
                    usuario.setApps(DatabaseManager.getAppsDoUsuario());
                    
                    try {
                        // Recarregar o MainDashboard
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/mainDashboard.fxml"));
                        Parent mainDashboard = loader.load();
                        
                        Pane currentPane = (Pane) atualizarBtn.getScene().getRoot();
                        currentPane.getChildren().clear();
                        currentPane.getChildren().add(mainDashboard);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setInfo(String name, String email, String senha, String site, String notes, int appId) {
        nameField.setText(name);
        emailField.setText(email);
        senhaField.setText(senha);
        webSiteField.setText(site);
        notasField.setText(notes);
        this.currentAppId = appId;
    }

}

