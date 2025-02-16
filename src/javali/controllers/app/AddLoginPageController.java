package javali.controllers.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javali.models.AppModel;
import javali.database.DatabaseManager;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javali.models.Usuario;
import javali.utils.SessionManager;

public class AddLoginPageController {

    @FXML
    private Button cadastrarBtn;

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
    void handleLoginBtn(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = senhaField.getText();
        String site = webSiteField.getText();
        String notes = notasField.getText();

        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        if (DatabaseManager.verificarApp(name, email, password, site)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao adicionar app");
            alert.setContentText("App já existe");
            alert.showAndWait();
        }
        else {
            AppModel app = new AppModel(name, email, password, site, notes, sqlDate);
            if (DatabaseManager.adicionarApp(app)) {
                // Atualizar o usuário logado com a nova lista de apps
                Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
                usuario.setApps(DatabaseManager.getAppsDoUsuario());
                
                // Recarregar o MainDashboard
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/mainDashboard.fxml"));
                    Pane mainDashboard = loader.load();
                    
                    // Obter o pane pai atual e substituir seu conteúdo
                    Pane currentPane = (Pane) cadastrarBtn.getScene().getRoot();
                    currentPane.getChildren().clear();
                    currentPane.getChildren().add(mainDashboard);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
