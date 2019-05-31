package it.polimi.se2019.client.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title,String message){
        Stage window = new Stage();
        String style = "-fx-background-color: #3c3c3c;-fx-text-fill: #999999;-fx-border-color: #000000;-fx-border-radius: 30;-fx-background-radius: 30";
        String highlightStyle = "-fx-background-color: #d0c6ce;-fx-text-fill: #999999;-fx-border-color: #000000;-fx-border-radius: 30;-fx-background-radius: 30";
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);
        Label label = new Label();
        label.setText(message);
        label.setStyle(style);

        //create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setStyle(style);
        yesButton.setOnMouseEntered(e -> yesButton.setStyle(highlightStyle));
        yesButton.setOnMouseExited(e -> yesButton.setStyle(style));
        yesButton.setOnAction(e-> {
            answer = true;
            window.close();
        });

        noButton.setStyle(style);
        noButton.setOnMouseEntered(e -> noButton.setStyle(highlightStyle));
        noButton.setOnMouseExited(e -> noButton.setStyle(style));
        noButton.setOnAction(e-> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: #696969;-fx-text-fill: #999999");
        HBox hBox = new HBox();
        hBox.setSpacing(100);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(yesButton,noButton);
        layout.getChildren().addAll(label,hBox);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout,250,150);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}