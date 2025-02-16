package javali.controllers.dashboard;

import java.io.IOException;
import java.sql.Date;

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
import javafx.scene.text.Text;
import javali.controllers.app.AtualizarAppDadosController;
import javali.database.DatabaseManager;
import javali.utils.SessionManager;
import javali.models.Usuario;


public class RightPaneInfoController {

    @FXML
    private Text dateCreateText;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button edictBtn;

    @FXML
    private TextField emailField;

    @FXML
    private Text nameSiteBtn;

    @FXML
    private Text notesText;

    @FXML
    private TextField senhaField;

    @FXML
    private Text siteText;

    private int currentAppId;

    @FXML
    void handleDeleteBtn(ActionEvent event) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Confirmação de Exlusão");
        alert.setHeaderText("Deseja realmente excluir este login? Todos os dados serão perdidos.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (DatabaseManager.removerApp(currentAppId)) {
                    Usuario usuario = SessionManager.getInstance().getUsuarioLogado();
                    usuario.setApps(DatabaseManager.getAppsDoUsuario());
                    
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/mainDashboard.fxml"));
                        Parent mainDashboard = loader.load();
                    
                        Pane currentPane = (Pane) deleteBtn.getScene().getRoot();
                        currentPane.getChildren().clear();
                        currentPane.getChildren().add(mainDashboard);
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    void handleEditButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/atualizarAppDados.fxml"));
            Parent root = loader.load();

            AtualizarAppDadosController controller = loader.getController();
            controller.setInfo(
                nameSiteBtn.getText(), 
                emailField.getText(), 
                senhaField.getText(), 
                siteText.getText(), 
                notesText.getText(),
                currentAppId
            );

            Pane paneInfo = (Pane) edictBtn.getScene().lookup("#paneInfo");

            if (paneInfo != null) {
                paneInfo.getChildren().clear();
                paneInfo.getChildren().add(root); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfo(String name, String email, String senha, String site, String notes, Date date, int appId) {
        nameSiteBtn.setText(name);
        emailField.setText(email);
        senhaField.setText(senha);
        siteText.setText(site);
        notesText.setText(notes);
        dateCreateText.setText(date.toString());
        this.currentAppId = appId;
    }

}
