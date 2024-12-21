package javali.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javali.models.AppModel;

public class MainDashboardController implements Initializable{

    @FXML
    private Button addSenhaBtn;

    @FXML
    private Pane paneInfo;

    @FXML
    private Button perfilBtn;

    @FXML
    private VBox vItems;

    private boolean isSelect[];
    
    @FXML
    void handleAddSenha(ActionEvent event) {
        resetSelect();
        
        perfilBtn.setStyle("-fx-background-color: #353C40; -fx-background-radius: 0;");
        addSenhaBtn.setStyle("-fx-background-color : #6393E7; -fx-background-radius: 1em;");
        
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("/resources/fxml/addLoginPage.fxml"));
            Pane pane = loader.load();
            paneInfo.getChildren().clear();
            paneInfo.getChildren().add(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handlePerfil(ActionEvent event) {
        resetSelect();

        addSenhaBtn.setStyle("-fx-background-color: #353C40; -fx-background-radius: 0;");
        perfilBtn.setStyle("-fx-background-color : #6393E7; -fx-background-radius: 1em;");

        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getResource("/resources/fxml/perfilDados.fxml"));
            Pane pane = loader.load();
            paneInfo.getChildren().clear();
            paneInfo.getChildren().add(pane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void resetSelect() {
        for (Node node : vItems.getChildren()) {
            node.setStyle("-fx-background-color : #182025");
        }
        addSenhaBtn.setStyle("-fx-background-color: #353C40; -fx-background-radius: 0;");
        for (int i = 0; i < isSelect.length; i++) {
            isSelect[i] = false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            // Carregar os dados de APP's
            List<AppModel> appModels = new ArrayList<>();

            appModels.add(new AppModel("Facebook", "example@gmail.com", "123456", "facebook.com", "Site Velho Legal", java.sql.Date.valueOf("2024-12-12")));
            appModels.add(new AppModel("Intagram", "example@gmail.com", "123456", "instagram.com", "Site igual o site velho Legal", java.sql.Date.valueOf("2024-12-13")));
            appModels.add(new AppModel("Twitter", "example@gmail.com", "123456", "x.com", "Site para falar mal dos outros", java.sql.Date.valueOf("2024-12-14")));
            appModels.add(new AppModel("Telegram", "example@gmail.com", "123456", "telegram.com", "Site para conseguir coisas ilegais", java.sql.Date.valueOf("2024-12-15")));
            appModels.add(new AppModel("Macumba", "example@gmail.com", "123456", "macumba.com", "Site para conseguir uma namorada :(", java.sql.Date.valueOf("2024-12-16")));

            Node[] nodes = new Node[appModels.size()];
            isSelect = new boolean[appModels.size()];

            for (int i = 0; i < nodes.length; i++) {

                FXMLLoader loader_items = new FXMLLoader();
                loader_items.setLocation(getClass().getResource("/resources/fxml/item.fxml"));

                nodes[i] = loader_items.load();

                final int j = i;

                ItemController itemController = loader_items.getController();

                itemController.setItemInfo(appModels.get(i).getName(), appModels.get(i).getEmail());

                nodes[i].setOnMouseClicked(evt -> {
                    try {

                        addSenhaBtn.setStyle("-fx-background-color: #353C40; -fx-background-radius: 0;");
                        perfilBtn.setStyle("-fx-background-color: #353C40; -fx-background-radius: 0;");
                        
                        FXMLLoader loader_info = new FXMLLoader();
                        loader_info.setLocation(getClass().getResource("/resources/fxml/rightPaneInfo.fxml"));
                        Pane pane = loader_info.load();
                        RightPaneInfoController rightPaneInfoController = loader_info.getController();
                        rightPaneInfoController.setInfo(appModels.get(j).getName(), appModels.get(j).getEmail(), appModels.get(j).getPassword(), appModels.get(j).getWebsite(), appModels.get(j).getNotes(), appModels.get(j).getDate());
                        paneInfo.getChildren().clear();
                        paneInfo.getChildren().add(pane);

                        for (int k = 0; k < nodes.length; k++) {
                            if (k != j) {
                                nodes[k].setStyle("-fx-background-color : #182025");
                                isSelect[k] = false;
                            }
                        }

                        isSelect[j] = true;
                        if (isSelect[j]) {
                            nodes[j].setStyle("-fx-background-color : #6393E7");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                nodes[i].setOnMouseEntered(evt -> {
                    if (!isSelect[j]) {
                        nodes[j].setStyle("-fx-background-color : #6393E7");
                    }
                });

                nodes[i].setOnMouseExited(evt -> {
                    if (!isSelect[j]) {
                        nodes[j].setStyle("-fx-background-color : #182025");
                    }
                });
                vItems.getChildren().add(nodes[i]);
            }

            // Carregar Informação Base

            FXMLLoader loader_info = new FXMLLoader();
            loader_info.setLocation(getClass().getResource("/resources/fxml/rightPaneInfo.fxml"));
            Pane pane = loader_info.load();
            RightPaneInfoController rightPaneInfoController = loader_info.getController();
            rightPaneInfoController.setInfo(appModels.get(0).getName(), appModels.get(0).getEmail(), appModels.get(0).getPassword(), appModels.get(0).getWebsite(), appModels.get(0).getNotes(), appModels.get(0).getDate());
            paneInfo.getChildren().add(pane);

            // Selecionar o primeiro item por padrão
            nodes[0].setStyle("-fx-background-color : #6393E7");
            isSelect[0] = true;

            addSenhaBtn.setStyle("-fx-background-color: #353C40; -fx-background-radius: 0;");
            perfilBtn.setStyle("-fx-background-color: #353C40; -fx-background-radius: 0;");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
