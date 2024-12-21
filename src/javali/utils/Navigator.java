package javali.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigator {

    public static void navigateTo(Stage stage, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(fxmlFile));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}