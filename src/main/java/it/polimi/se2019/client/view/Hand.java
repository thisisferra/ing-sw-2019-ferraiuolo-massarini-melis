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

public class Hand {

    public static void display(HBox hand,String message){
        Stage window = new Stage();
        ImageView img = null;
        //l'alert deve essere risolto prima di tornare alla finestra che l'ha chiamata
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(message);
        window.setMinWidth(400);
        window.setMinHeight(250);

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

        HBox layout = new HBox();
        BorderPane borderPane = new BorderPane();
        layout.getChildren().addAll(hand.getChildren());
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        layout.setStyle("-fx-background-color: #3c3c3c");
        borderPane.setCenter(layout);

        borderPane.setStyle("-fx-background-color: #3c3c3c");
        borderPane.setBottom(closeButton);
        closeButton.setTranslateX(170);
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        // una volta aperta la finestra, essa rimane aperta fino a quando non viene chiusa
        // questa azione è bloccante
        window.showAndWait();
    }
}