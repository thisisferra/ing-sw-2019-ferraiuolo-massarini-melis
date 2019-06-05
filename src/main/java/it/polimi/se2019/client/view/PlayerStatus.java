package it.polimi.se2019.client.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static it.polimi.se2019.client.view.GUI.BUTTON_STYLE;
import static it.polimi.se2019.client.view.GUI.HIGHLIGHT_BUTTON_STYLE;

public class PlayerStatus{

    public static void display(Pane playerboards, String message){
        Stage window = new Stage();
        ImageView img = null;
        Pane layout = null;
        Label info = new Label("Current "+ message + " are:");
        BorderPane borderPane = new BorderPane();

        info.setStyle("-fx-text-fill: #bdbdbd");

        //l'alert deve essere risolto prima di tornare alla finestra che l'ha chiamata
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(message);

        Button closeButton = setHomeButton(window);
        if(playerboards instanceof VBox){
             layout = new VBox();
        }else if(playerboards instanceof HBox){
             layout = new HBox();
        }

        if(layout != null){
            layout.getChildren().addAll(playerboards.getChildren());
            layout.setStyle("-fx-background-color: #505050");
        }

        borderPane.setTop(info);
        borderPane.setCenter(layout);
        borderPane.setBottom(closeButton);
        borderPane.setStyle("-fx-background-color: #505050");
        borderPane.setAlignment(closeButton,Pos.CENTER);
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        // una volta aperta la finestra, essa rimane aperta fino a quando non viene chiusa
        // questa azione è bloccante
        window.showAndWait();
    }


    public static Button setHomeButton(Stage window){
        ImageView img = null;
        //creo un bottone che una volta premuto chiude la finestra
        try{
            img = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/home.png")));
        } catch (FileNotFoundException e){
            System.out.println("File non trovato");
        }
        if(img != null){
            img.setPreserveRatio(true);
            img.setFitHeight(50);
        }
        Button closeButton = new Button("",img);
        closeButton.setStyle(BUTTON_STYLE);
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(BUTTON_STYLE));
        closeButton.setOnAction(e-> window.close());
        return closeButton;
    }
}
