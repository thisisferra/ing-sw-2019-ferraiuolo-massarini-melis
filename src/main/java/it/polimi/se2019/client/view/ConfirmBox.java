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

import static it.polimi.se2019.client.view.GUI.BUTTON_STYLE;
import static it.polimi.se2019.client.view.GUI.HIGHLIGHT_BUTTON_STYLE;

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title,String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-text-fill: #cecece");

        //create two buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setStyle(BUTTON_STYLE);
        yesButton.setOnMouseEntered(e -> yesButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        yesButton.setOnMouseExited(e -> yesButton.setStyle(BUTTON_STYLE));
        yesButton.setOnAction(e-> {
            answer = true;
            window.close();
        });

        noButton.setStyle(BUTTON_STYLE);
        noButton.setOnMouseEntered(e -> noButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        noButton.setOnMouseExited(e -> noButton.setStyle(BUTTON_STYLE));
        noButton.setOnAction(e-> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox();
        layout.setSpacing(20);
        layout.setStyle("-fx-background-color: #505050;-fx-text-fill: #999999");
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