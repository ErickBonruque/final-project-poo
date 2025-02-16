package javali;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javali.database.connection.SQLiteConnection;

// Que vida triste

public class Main extends Application {

    double x, y = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {

        SQLiteConnection.getConnection();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setTitle("ShieldPass");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/logo.png")));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}