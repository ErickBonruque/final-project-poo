package javali.controllers.components;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

public class ItemController implements Initializable {
    @FXML
    private Text mailText;

    @FXML
    private Text nameAppText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setItemInfo(String nameApp, String mail) {
        nameAppText.setText(nameApp);
        mailText.setText(mail);
    }

    public void setMailText(String mail) {
        mailText.setText(mail);
    }

    public void setNameAppText(String nameApp) {
        nameAppText.setText(nameApp);
    }

    public String getMailText() {
        return mailText.getText();
    }

    public String getNameAppText() {
        return nameAppText.getText();
    }
}
