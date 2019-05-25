package it.polimi.se2019.client.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
public class PlayerStatus{

    public static void display(VBox playerboards,String message){
        Stage window = new Stage();
        ImageView img = null;
        Label info = new Label("Current "+ message + " are:");
        info.setStyle("-fx-text-fill: #bdbdbd");
        //l'alert deve essere risolto prima di tornare alla finestra che l'ha chiamata
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(message);
        window.setMinWidth(800);
        window.setMinHeight(500);

        //creo un bottone che una volta premuto chiude la finestra
        try{
            img = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/home.png")));
        } catch (FileNotFoundException e){
            System.out.println("File non trovato");
        }
        img.setPreserveRatio(true);
        img.setFitHeight(50);
        Button closeButton = new Button("",img);
        closeButton.setStyle("-fx-background-color: #505050;-fx-text-fill: #999999;");
        closeButton.setOnAction(e-> window.close());

        VBox layout = new VBox();
        BorderPane borderPane = new BorderPane();
        layout.getChildren().addAll(playerboards.getChildren());
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setStyle("-fx-background-color: #3c3c3c");
        borderPane.setCenter(layout);

        borderPane.setStyle("-fx-background-color: #3c3c3c");
        borderPane.setTop(info);
        borderPane.setBottom(closeButton);
        closeButton.setTranslateX(350);
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        // una volta aperta la finestra, essa rimane aperta fino a quando non viene chiusa
        // questa azione è bloccante
        window.showAndWait();
    }
}
