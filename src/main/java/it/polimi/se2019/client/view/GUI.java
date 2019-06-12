package it.polimi.se2019.client.view;

import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.map.Square;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.*;

public class GUI extends Application {


    String username;
    private GUIController guiController = new GUIController();
    private RemoteView myRemoteView;


    private Stage window;
    private Group root = new Group();
    private Image image;
    private ImageView imageView = new ImageView();
    private ToggleGroup mapSelector = new ToggleGroup();
    private GridPane grid = new GridPane();
    private GridPane pawnsGrid = new GridPane();
    private HBox redBox = new HBox();
    private HBox blueBox = new HBox();
    private HBox yellowBox = new HBox();
    private StackPane cabinets = new StackPane();
    private HBox deathTrack = new HBox();
    private VBox cubeBox = new VBox();
    private StackPane ammoSet = new StackPane();
    private VBox playerBoards = new VBox();
    private HBox weaponHand = new HBox();
    private HBox powerUpHand = new HBox();
    private VBox leftMenu = new VBox();
    private StackPane stack = new StackPane();
    private VBox rightPane = new VBox();
    private BorderPane borderPane = new BorderPane();
    private ArrayList<String> weaponsName = new ArrayList<>();
    private ArrayList<String> powerUpsName = new ArrayList<>();
    private TextArea textArea = new TextArea("\nWelcome to the game!");
    private Image iconImage = null;
    private ImageView iconView = null;
    static final String BUTTON_STYLE = "-fx-background-color: #3c3c3c;-fx-text-fill: #999999;";
    static final String HIGHLIGHT_BUTTON_STYLE = "-fx-background-color: #bbbbbb;-fx-text-fill: #999999;";
    private int mapNumber = 1;
    private Scene scene, loginScene;


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(myRemoteView != null){
                    setFigures();
                    setWeaponView(redBox, myRemoteView.getCabinetRed().getSlot());
                    setWeaponView(yellowBox, myRemoteView.getCabinetYellow().getSlot());
                    setWeaponView(blueBox, myRemoteView.getCabinetBlue().getSlot());
                    setCubes();
                }
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        window = primaryStage;
        window.setScene(setLoginScene());
        window.setTitle("Adrenaline");
        window.setResizable(true);
        window.setFullScreen(false);
        window.alwaysOnTopProperty();
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });


        // map background
        image = new Image(new FileInputStream("src/main/resources/Images/Maps/Map" + mapNumber + ".png"));
        imageView = new ImageView(image);
        imageView.setFitHeight(700);
        imageView.setPreserveRatio(true);

        //ammos
        setAmmo(mapNumber);

        //deathtrack
        setDeathTrackSkulls();

        //map grid and pawnsgrid
        setMapGrid();

        //cabinets
        if(myRemoteView != null){
            setWeaponView(redBox, myRemoteView.getCabinetRed().getSlot());
            setWeaponView(yellowBox, myRemoteView.getCabinetYellow().getSlot());
            setWeaponView(blueBox, myRemoteView.getCabinetBlue().getSlot());
        }

        //blue cabinet
        blueBox.setTranslateX(490);
        blueBox.setTranslateY(-10);
        blueBox.setSpacing(10);

        //red cabinet
        redBox.setTranslateX(-125);
        redBox.setTranslateY(-265);
        redBox.setRotate(-90);
        redBox.setSpacing(10);

        //yellow cabinet
        yellowBox.setSpacing(10);
        yellowBox.setRotate(90);
        yellowBox.setTranslateX(122);
        yellowBox.setTranslateY(510);


        cabinets.setPickOnBounds(false);
        cabinets.getChildren().addAll(redBox, blueBox, yellowBox);

        //powerups

        //playerBoards
        setPlayerboards();

        //points
        Label points = new Label("Points: " + getMyPoints());
        points.setStyle("-fx-background-color: #404040; -fx-text-fill: #aaaaaa");


        //buttons
        Button moveButton = setButton("src/main/resources/Images/icons/move_icon.png", 50, "Move");
        Button grabButton = setButton("src/main/resources/Images/icons/grab_icon.png", 50, "Move and grab");
        Button shootButton = setButton("src/main/resources/Images/icons/shoot_icon.png", 50, "Shoot");
        Button passButton = setButton("src/main/resources/Images/icons/pass_icon.png", 50, "Pass turn and reload");
        Button powerUps = setButton("src/main/resources/Images/icons/powerup_icon.png", 50, "");
        Button playersButton = setButton("src/main/resources/Images/icons/players_icon.png", 50, "");
        Button weapons = setButton("src/main/resources/Images/icons/weapon_icon.png", 50, "");
        moveButton.setOnAction(e -> {
            try {
                if (guiController.getRmiStub().getActivePlayer().equals(this.username)) {
                    if (guiController.getRmiStub().checkNumberAction(username)) {
                        guiController.getRmiStub().useAction(username);
                        if (!guiController.getMyRemoteView().getCanMove()) {
                            guiController.getMyRemoteView().setCanMove(true);
                            guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reacheableSquare(guiController.getMyRemoteView().getPosition(), 3));
                            textArea.setText("\n" + guiController.getMyRemoteView().getReachableSquare() + "\n" + textArea.getText());
                            for (int i = 0; i < 12; i++) {
                                Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
                                for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                                    if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                                        rectangle.setFill(Color.color(1, 1, 0, 0.4));
                                        rectangle.setOnMouseClicked(o -> {
                                            textArea.setText("\nSquare #: " + rectangle.getId() + "\n" + textArea.getText());
                                            try {
                                                guiController.getRmiStub().setNewPosition(guiController.getMyRemoteView().getUsername(), Integer.parseInt(rectangle.getId()));
                                                textArea.setText("\nNew position: " + rectangle.getId() + textArea.getText());
                                                setFigures();
                                                for (int j = 0; j < 12; j++) {
                                                    Rectangle rect = (Rectangle) grid.getChildren().get(j);
                                                    rect.setFill(Color.color(1, 1, 1, 0.1));
                                                    rect.setOnMouseClicked(g -> {

                                                    });
                                                    guiController.getMyRemoteView().setCanMove(false);
                                                }
                                            } catch (Exception exc) {
                                                exc.printStackTrace();
                                            }
                                        });
                                    }
                                }
                            }
                        } else {
                            textArea.setText("\nYou have already selected this option" + textArea.getText());
                        }
                    } else {
                        textArea.setText("You have already used two actions. Pass the turn.\n" + textArea.getText());
                    }
                }
                else {
                    textArea.setText("It's not your round!!!\n" + textArea.getText());
                }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        powerUps.setOnAction(e -> {
            HBox box = new HBox();
            setPowerUpHand();
            for (Node obj : powerUpHand.getChildren()) {

                ImageView copy = (ImageView) obj;
                ImageView copiedPowerUp = new ImageView(copy.getImage());
                copiedPowerUp.setPreserveRatio(true);
                copiedPowerUp.setFitHeight(200);
                box.getChildren().add(copiedPowerUp);

            }
            PlayerStatus.display(box, "Power ups");
        });
        playersButton.setOnAction(e -> {
            VBox box = new VBox();
            for (Node obj : playerBoards.getChildren()) {

                ImageView copy = (ImageView) obj;
                ImageView copiedPlayerboard = new ImageView(copy.getImage());
                copiedPlayerboard.setPreserveRatio(true);
                copiedPlayerboard.setFitHeight(100);
                box.getChildren().add(copiedPlayerboard);

            }
            PlayerStatus.display(box, "Players");
        });
        weapons.setOnAction(e -> {
            HBox box = new HBox();
            ImageView imageView = null;
            for (Weapon obj : myRemoteView.getWeapons()) {
                try {
                    imageView = null;
                    Image image = new Image(new FileInputStream("src/main/resources/Images/Weapons/" + obj.getType() + ".png"));
                    if(!obj.getLoad())
                        image = toGrayScale(image);
                    imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(300);
                } catch (FileNotFoundException o) {
                    o.printStackTrace();
                }
                box.getChildren().add(imageView);
            }
            PlayerStatus.display(box, "Weapons");
        });
        grabButton.setOnMouseEntered(e -> grabButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        grabButton.setOnMouseExited(e -> grabButton.setStyle(BUTTON_STYLE));
        grabButton.setOnAction(e -> {
            //boolean itsMyRound = guiController.getRmiStub().isTurn(String username);
            try {
                //if(guiController.getRmiStub().checkActivePlayer(username) {
                    if (guiController.getRmiStub().checkNumberAction(username)) {
                        guiController.getRmiStub().useAction(this.username);
                        if (!guiController.getMyRemoteView().getCanMove()) {
                            guiController.getMyRemoteView().setCanMove(true);
                            guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reacheableSquare(guiController.getMyRemoteView().getPosition(), 1));
                            textArea.setText("\n" + guiController.getMyRemoteView().getReachableSquare() + "\n" + textArea.getText());
                            for (int i = 0; i < 12; i++) {
                                Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
                                for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                                    if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                                        rectangle.setFill(Color.color(0, 0.2, 1, 0.4));
                                        rectangle.setOnMouseClicked(o -> {
                                            textArea.setText("\nSquare #: " + rectangle.getId() + "\n" + textArea.getText());
                                            try {
                                                guiController.getRmiStub().setNewPosition(guiController.getMyRemoteView().getUsername(), Integer.parseInt(rectangle.getId()));
                                                textArea.setText("\nNew position: " + rectangle.getId() + textArea.getText());
                                                setFigures();
                                                ammoSet.getChildren().get(Integer.parseInt(rectangle.getId())).setTranslateX(500);
                                                ammoSet.getChildren().get(Integer.parseInt(rectangle.getId())).setTranslateY(500);
                                                for (int j = 0; j < 12; j++) {
                                                    Rectangle rect = (Rectangle) grid.getChildren().get(j);
                                                    rect.setFill(Color.color(1, 1, 1, 0.1));
                                                    rect.setOnMouseClicked(g -> {
                                                    });
                                                }
                                                boolean isSpawn = guiController.getRmiStub().isSpawnPoint(myRemoteView.getPosition());
                                                if (!isSpawn)
                                                    guiController.getRmiStub().pickUpAmmo(myRemoteView.getUsername());
                                                else {
                                                    //TODO to implement
                                                    //TODO indexToPickUp e indexToSwitch sono due input dell'utente
                                                    int indexToPickUp = 0;
                                                    int indexToSwitch = 0;
                                                    if (myRemoteView.getWeapons().size() < 3) {
                                                        guiController.getRmiStub().pickUpWeapon(username, indexToPickUp);
                                                        textArea.setText("You have picked up: " + myRemoteView.getWeapons().get(myRemoteView.getWeapons().size() - 1) + "\n" + textArea.getText());
                                                    } else if (myRemoteView.getWeapons().size() == 3) {
                                                        textArea.setText("You already have three weapons in you hand.\nChoose one to discard..." + "\n" + textArea.getText());
                                                    } else {
                                                        throw new Exception("You have more than three weapons in your hand");
                                                    }
                                                }
                                                guiController.getMyRemoteView().setCanMove(false);
                                            }
                                            catch (Exception exc) {
                                                exc.printStackTrace();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                    else {
                       textArea.setText("You have already used two actions. Pass your turn" + textArea.getText());
                    }
            } catch (Exception e1) {
                    e1.printStackTrace();
                }
        });
        shootButton.setOnAction(e -> {
            try {
                if (guiController.getRmiStub().checkNumberAction(username)) {
                    guiController.getRmiStub().useAction(username);
                    //INIZIO STAMPA DI CONTROLLO
                    System.out.println("SPAR(T)A");
                } else {
                    textArea.setText("You have already used two actions. Pass your turn\n" + textArea.getText());
                }
            }
            catch(RemoteException exc) {
                exc.printStackTrace();
            }
        });
        passButton.setOnAction(e -> {
            //TODO maschere per loadare un'arma
            try {
                if (guiController.getRmiStub().getActivePlayer().equals(username)) {
                    guiController.getRmiStub().restoreMap();
                    setAmmo(mapNumber);
                    guiController.getRmiStub().resetActionNumber(username);
                    guiController.getRmiStub().setActivePlayer(username);
                }
                else {
                    System.out.println("It's not your round!!!");
                }
            }
            catch(Exception exc) {
                exc.printStackTrace();
            }
        });

        Button button = new Button("x");
        button.setStyle(BUTTON_STYLE);
        button.setOnAction(e -> {
            setWeaponView(redBox, myRemoteView.getCabinetRed().getSlot());
            setWeaponView(blueBox, myRemoteView.getCabinetBlue().getSlot());
            setWeaponView(yellowBox, myRemoteView.getCabinetYellow().getSlot());
            window.show();
        });

        Button button2 = new Button("y");
        button2.setStyle(BUTTON_STYLE);
        button2.setOnAction(e -> {
            setAmmo(mapNumber);
        });

        //deathtracker
        deathTrack.setTranslateX(65);
        deathTrack.setTranslateY(40);
        deathTrack.setSpacing(-2);

        //cubes
        setCubes();

        //first player
        //add this imageview if the current player is the first player
        iconImage = new Image(new FileInputStream("src/main/resources/Images/Playerboards/FirstPlayer.png"));
        iconView = new ImageView(iconImage);
        ImageView firstPlayer = iconView;
        firstPlayer.setFitHeight(100);
        firstPlayer.setPreserveRatio(true);
        firstPlayer.setTranslateX(-400);
        firstPlayer.setTranslateY(275);

        //left boarderpane
        leftMenu.getChildren().addAll(moveButton, grabButton, shootButton, passButton, weapons, powerUps, playersButton, points, cubeBox, button, button2);

        //right borderpane
        //textarea
        textArea.setPrefWidth(225);
        textArea.setPrefHeight(300);
        textArea.setTranslateX(-450);
        textArea.setTranslateY(10);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle("-fx-control-inner-background:#717171;  -fx-highlight-fill: #f1f7eb; -fx-highlight-text-fill: #717171; -fx-text-fill: #f1f7eb;-fx-border-color: #ffffff ");

        rightPane.getChildren().add(textArea);
        rightPane.setSpacing(10);

        stack.getChildren().addAll(imageView, ammoSet, firstPlayer, deathTrack, cabinets, pawnsGrid, grid);
        root = new Group(stack);
        root.setTranslateY(-375);
        root.setTranslateX(25);
        borderPane.setStyle("-fx-background-color: #505050");
        borderPane.setCenter(root);
        borderPane.setRight(rightPane);
        borderPane.setLeft(leftMenu);
        scene = new Scene(borderPane, 1300, 700);

        window.show();
    }

    public void setMapGrid(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Rectangle box = new Rectangle(160, 160);
                box.setFill(Color.color(1, 1, 1, 0.1));
                box.setId(Integer.toString(row * 4 + col));
                grid.add(box, col, row);
            }
        }
        grid.setTranslateX(149);
        grid.setTranslateY(145);
        grid.setGridLinesVisible(true);
        grid.setPickOnBounds(false);
    }

    public void setWeaponView(HBox cabinet, Weapon[] weaponsArray) {
        cabinet.getChildren().clear();
        String weaponPath = null;
            for (int i = 0; i < 3; i++) {

                Image imageWeapon = null;
                ImageView weaponView = null;
                if(weaponsArray[i] != null){
                    weaponPath = "src/main/resources/Images/Weapons/" + weaponsArray[i].getType() + ".png";

                    try {
                        imageWeapon = new Image(new FileInputStream(weaponPath));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    weaponView = new ImageView(imageWeapon);
                }
                else
                    weaponView = new ImageView();

                weaponView.setFitWidth(90);
                weaponView.setPreserveRatio(true);
                cabinet.getChildren().add(weaponView);

            }
            for (Node obj : cabinet.getChildren()) {
                VBox box = new VBox();
                obj.setOnMouseEntered(e -> {
                    box.getChildren().clear();
                    if (rightPane.getChildren().size() > 1)
                        rightPane.getChildren().remove(rightPane.getChildren().size() - 1);

                    ImageView boxView = (ImageView) obj;
                    ImageView view = new ImageView(boxView.getImage());
                    view.setPreserveRatio(true);
                    view.setFitHeight(350);
                    box.getChildren().add(view);
                    rightPane.getChildren().add(box);
                    box.setTranslateX(-450);
                });
                obj.setOnMouseExited(e -> {
                    rightPane.getChildren().remove(box);
                });
            }
    }

    public void setPowerUpHand(){
        powerUpHand.getChildren().clear();
        Image image = null;
        ImageView imageView = null;
        String path;
        for(PowerUp powerUp : myRemoteView.getPowerUp()){
            try{
                image = new Image(new FileInputStream("src/main/resources/Images/PowerUps/" + powerUp.getColor() +"_"+ powerUp.getType() + ".png"));
                imageView = new ImageView(image);
                powerUpHand.getChildren().add(imageView);
            }catch (FileNotFoundException exc){
                exc.printStackTrace();
            }
        }
    }

    public void setDeathTrackSkulls() {
        Image skullImage = null;
        ImageView skullView;
        try {
            skullImage = new Image(new FileInputStream("src/main/resources/Images/icons/skull_icon.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 8; i++) {
            skullView = new ImageView(skullImage);
            skullView.setFitHeight(40);
            skullView.setSmooth(true);
            skullView.setPreserveRatio(true);
            deathTrack.getChildren().add(skullView);
        }
        deathTrack.setPickOnBounds(false);
    }

    public void addDeathTrackDamage(String color) {
        Image tearImage = null;
        try {
            tearImage = new Image(new FileInputStream("src/main/resources/Images/icons/" + color + "_damage_icon.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView tear = new ImageView(tearImage);
        tear.setFitHeight(40);
        tear.setSmooth(true);
        tear.setPreserveRatio(true);
        deathTrack.getChildren().add(tear);
    }

    public void setPlayerboards() {
        playerBoards = new VBox();
        ImageView img;
        Image image = null;
        for(RemoteView view : guiController.getAllViews()){
            try {
                image = new Image(new FileInputStream("src/main/resources/Images/Playerboards/" + ".png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            img = new ImageView(image);
            img.setPreserveRatio(true);
            img.setFitWidth(400);
            playerBoards.getChildren().add(img);
        }

    }

    public void setAmmo(int map) {

        Image ammoBack = null;

        try {
            ammoBack = new Image(new FileInputStream("src/main/resources/Images/Ammo/ammoback.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ammoSet.getChildren().clear();
        for (int i = 0; i < 12; i++) {
            ammoSet.getChildren().add(new ImageView(ammoBack));
        }
        for (Node obj : ammoSet.getChildren()) {
            obj.setTranslateX(500);
            obj.setTranslateY(500);
        }
        switch (map) {
            case 2: {


                //square 0
                ammoSet.getChildren().get(0).setTranslateX(-260);
                ammoSet.getChildren().get(0).setTranslateY(-160);

                //square 1
                ammoSet.getChildren().get(1).setTranslateX(-115);
                ammoSet.getChildren().get(1).setTranslateY(-90);

                //square 5
                ammoSet.getChildren().get(5).setTranslateX(-125);
                ammoSet.getChildren().get(5).setTranslateY(30);

                //square 6
                ammoSet.getChildren().get(6).setTranslateX(55);
                ammoSet.getChildren().get(6).setTranslateY(50);

                //square 7
                ammoSet.getChildren().get(7).setTranslateX(265);
                ammoSet.getChildren().get(7).setTranslateY(55);

                //square 9
                ammoSet.getChildren().get(9).setTranslateX(-120);
                ammoSet.getChildren().get(9).setTranslateY(210);

                //square 10
                ammoSet.getChildren().get(10).setTranslateX(35);
                ammoSet.getChildren().get(10).setTranslateY(215);


                break;
            }
            case 1: {


                //square 0
                ammoSet.getChildren().get(0).setTranslateX(-255);
                ammoSet.getChildren().get(0).setTranslateY(-80);

                //square 1
                ammoSet.getChildren().get(1).setTranslateX(-120);
                ammoSet.getChildren().get(1).setTranslateY(-160);

                //square 5
                ammoSet.getChildren().get(5).setTranslateX(-130);
                ammoSet.getChildren().get(5).setTranslateY(40);

                //square 6
                ammoSet.getChildren().get(6).setTranslateX(55);
                ammoSet.getChildren().get(6).setTranslateY(50);

                //square 7
                ammoSet.getChildren().get(7).setTranslateX(265);
                ammoSet.getChildren().get(7).setTranslateY(55);

                //square 8
                ammoSet.getChildren().get(8).setTranslateX(-250);
                ammoSet.getChildren().get(8).setTranslateY(210);

                //square 9
                ammoSet.getChildren().get(9).setTranslateX(-120);
                ammoSet.getChildren().get(9).setTranslateY(210);

                //square 10
                ammoSet.getChildren().get(10).setTranslateX(35);
                ammoSet.getChildren().get(10).setTranslateY(215);


                break;
            }
            case 3: {


                //square 0
                ammoSet.getChildren().get(0).setTranslateX(-260);
                ammoSet.getChildren().get(0).setTranslateY(-160);

                //square 1
                ammoSet.getChildren().get(1).setTranslateX(-115);
                ammoSet.getChildren().get(1).setTranslateY(-90);

                //square 3
                ammoSet.getChildren().get(3).setTranslateX(265);
                ammoSet.getChildren().get(3).setTranslateY(-90);

                //square 5
                ammoSet.getChildren().get(5).setTranslateX(-125);
                ammoSet.getChildren().get(5).setTranslateY(30);

                //square 6
                ammoSet.getChildren().get(6).setTranslateX(50);
                ammoSet.getChildren().get(6).setTranslateY(80);

                //square 7
                ammoSet.getChildren().get(7).setTranslateX(190);
                ammoSet.getChildren().get(7).setTranslateY(80);

                //square 9
                ammoSet.getChildren().get(9).setTranslateX(-120);
                ammoSet.getChildren().get(9).setTranslateY(210);

                //square 10
                ammoSet.getChildren().get(10).setTranslateX(60);
                ammoSet.getChildren().get(10).setTranslateY(210);

                break;
            }
            case 4: {

                //square 0
                ammoSet.getChildren().get(0).setTranslateX(-255);
                ammoSet.getChildren().get(0).setTranslateY(-80);

                //square 1
                ammoSet.getChildren().get(1).setTranslateX(-120);
                ammoSet.getChildren().get(1).setTranslateY(-160);

                //square 3
                ammoSet.getChildren().get(3).setTranslateX(265);
                ammoSet.getChildren().get(3).setTranslateY(-90);


                //square 5
                ammoSet.getChildren().get(5).setTranslateX(-130);
                ammoSet.getChildren().get(5).setTranslateY(40);

                //square 6
                ammoSet.getChildren().get(6).setTranslateX(50);
                ammoSet.getChildren().get(6).setTranslateY(80);

                //square 7
                ammoSet.getChildren().get(7).setTranslateX(190);
                ammoSet.getChildren().get(7).setTranslateY(80);

                //square 8
                ammoSet.getChildren().get(8).setTranslateX(-250);
                ammoSet.getChildren().get(8).setTranslateY(210);

                //square 9
                ammoSet.getChildren().get(9).setTranslateX(-120);
                ammoSet.getChildren().get(9).setTranslateY(210);

                //square 10
                ammoSet.getChildren().get(10).setTranslateX(60);
                ammoSet.getChildren().get(10).setTranslateY(210);


                break;
            }
            default:
                System.out.println("Error");
        }
        for (Node obj : ammoSet.getChildren()) {
            obj.setScaleX(0.3);
            obj.setScaleY(0.3);
        }
    }

    public void setCubes() {
        cubeBox.getChildren().clear();
        ImageView cubeImage = null;
        try {
            cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/redCube.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (cubeImage != null) {
            cubeImage.setFitWidth(20);
            cubeImage.setPreserveRatio(true);
        }

        Label redLabel = new Label(" " + getMyReds() + "  ", cubeImage);
        redLabel.setStyle("-fx-text-fill: #ff0000; -fx-background-color: #404040");

        try {
            cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/yellowCube.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (cubeImage != null) {
            cubeImage.setFitWidth(20);
            cubeImage.setPreserveRatio(true);
        }
        Label yellowLabel = new Label(" "  + getMyYellows() + "  ", cubeImage);
        yellowLabel.setStyle("-fx-text-fill: #fff000; -fx-background-color: #404040");

        try {
            cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/blueCube.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (cubeImage != null) {
            cubeImage.setFitWidth(20);
            cubeImage.setPreserveRatio(true);
        }
        Label blueLabel = new Label(" " + getMyBlues()+"  ", cubeImage);
        blueLabel.setStyle("-fx-text-fill: #0010ff; -fx-background-color: #404040;");

        cubeBox.getChildren().addAll(redLabel, yellowLabel, blueLabel);
        cubeBox.setSpacing(5);
    }

    public int getMyPoints() {
        if(myRemoteView!= null)
            return myRemoteView.getPoints();
        else return 0;
    }

    public int getMyReds(){
        if(myRemoteView != null)
            return myRemoteView.getCubes().getReds();
        else return 1;
    }

    public int getMyYellows(){
        if(myRemoteView != null)
            return myRemoteView.getCubes().getYellows();
        else return 1;
    }

    public int getMyBlues(){
        if(myRemoteView != null)
            return myRemoteView.getCubes().getBlues();
        else return 1;
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Exit Adrenaline", "Are you sure?");
        if (answer) {
            window.close();
        }
    }

    public Button setButton(String path, int width, String text) {
        Image iconImage = null;
        try {
            iconImage = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView iconView2 = new ImageView(iconImage);
        iconView2.setFitWidth(width);
        iconView2.setPreserveRatio(true);
        Button newButton = new Button("", iconView2);
        newButton.setOnAction(e -> textArea.setText(text + "\n" + textArea.getText()));
        newButton.setOnMouseEntered(e -> newButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        newButton.setOnMouseExited(e -> newButton.setStyle(BUTTON_STYLE));
        newButton.setStyle(BUTTON_STYLE);


        return newButton;
    }

    public Scene setLoginScene() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(50, 75, 50, 75));
        layout.setVgap(10);

        //Name input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");
        nameInput.setStyle("-fx-background-color: #726B72");
        GridPane.setConstraints(nameInput, 1, 0);

        //Password input
        PasswordField passInput = new PasswordField();
        passInput.setStyle("-fx-background-color: #726B72");
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 1);

        //IP input
        TextField IPInput = new TextField();
        IPInput.setPromptText("Server IP");
        IPInput.setStyle("-fx-background-color: #726B72");
        GridPane.setConstraints(IPInput, 1, 2);

        //login button
        Button loginButton = new Button("Log in");
        GridPane.setConstraints(loginButton, 1, 3);
        loginButton.setOnAction(e -> {
            try {
                //guiController = new GUIController();
                String usernameTyped = nameInput.getText();
                boolean check = guiController.getRmiStub().checkUsername(usernameTyped);
                if (!check) {
                    Label errorLabel = new Label("Selected name already taken, please retry.");
                    errorLabel.setStyle("-fx-text-fill: #ff0000");
                    Button exitButton = new Button("Back");
                    exitButton.setStyle(BUTTON_STYLE);
                    exitButton.setOnMouseExited(exit -> {
                        exitButton.setStyle(BUTTON_STYLE);
                    });
                    exitButton.setOnMouseEntered(enter -> {
                        exitButton.setStyle(HIGHLIGHT_BUTTON_STYLE);
                    });
                    exitButton.setOnAction(o -> {
                        Scene backScene;
                        backScene = setLoginScene();
                        window.setScene(backScene);
                    });
                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(errorLabel, exitButton);
                    vBox.setSpacing(20);
                    vBox.setStyle("-fx-background-color: #505050");
                    vBox.setAlignment(Pos.CENTER);
                    exitButton.setAlignment(Pos.CENTER);
                    errorLabel.setAlignment(Pos.CENTER);
                    Scene errorScene = new Scene(vBox, 300, 200);
                    window.setScene(errorScene);
                }
                //TODO implementare una finestra di attesa, di durata definita (es. 20 s)
                // per aspettare che tutti i giocatori siano connessi
                else {
                    guiController.setUsername(usernameTyped);
                    this.username = usernameTyped;
                    guiController.getRmiStub().register(usernameTyped, guiController);
                    myRemoteView = guiController.getMyRemoteView();
                    window.setScene(scene);
                    window.setFullScreen(true);         //Enlarge to fullscreen the game window
                    textArea.setText(usernameTyped + "\n" + textArea.getText());
                /*
                    for (RemoteView remoteView : guiController.getAllViews()) {
                        textArea.setText(remoteView.getUsername() + "\n" + textArea.getText());
                    }
                */

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        BorderPane pane = new BorderPane();
        VBox sideBox = setMapSelector();
        pane.setLeft(sideBox);
        pane.setCenter(layout);
        pane.setPadding(new Insets(10, 10, 10, 10));
        loginButton.setStyle(BUTTON_STYLE);
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(BUTTON_STYLE));
        loginButton.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(nameInput, passInput, IPInput, loginButton);
        Scene scene = new Scene(pane, 400, 250);
        pane.setStyle("-fx-background-color: #505050");
        return scene;

    }

    public VBox setMapSelector() {
        VBox sideBox = new VBox();
        RadioButton button1 = new RadioButton("Map 1");
        button1.setToggleGroup(mapSelector);
        button1.setStyle("-fx-text-fill: #b0b0b0");
        button1.setSelected(true);
        RadioButton button2 = new RadioButton("Map 2");
        button2.setStyle("-fx-text-fill: #b0b0b0");
        button2.setToggleGroup(mapSelector);
        RadioButton button3 = new RadioButton("Map 3");
        button3.setStyle("-fx-text-fill: #b0b0b0");
        button3.setToggleGroup(mapSelector);
        RadioButton button4 = new RadioButton("Map 4");
        button4.setStyle("-fx-text-fill: #b0b0b0");
        button4.setToggleGroup(mapSelector);
        Label selectMap = new Label("Select map: ");
        selectMap.setStyle("-fx-text-fill: #b0b0b0");
        sideBox.getChildren().addAll(selectMap, button1, button2, button3, button4);
        sideBox.setSpacing(10);
        sideBox.setAlignment(Pos.CENTER);
        sideBox.setStyle(BUTTON_STYLE);
        return sideBox;
    }


    public void setFigures() {
        FlowPane flowPane;
        String[] colors = {"banshee","dozer","violet","distructor","sprog"};
        int i = 0;
        pawnsGrid.getChildren().clear();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                flowPane = new FlowPane();
                //for every player, if there are players in this square fills the flowpane with them
                for(RemoteView view : guiController.getAllViews()){
                    if(view.getPosition() == row * 4 + col){
                        flowPane.getChildren().add(setCharacterImage(colors[i]));
                        i++;
                    }
                }
                flowPane.setPrefWidth(160);
                flowPane.setPrefHeight(160);
                flowPane.setId(Integer.toString(row * 4 + col));
                pawnsGrid.add(flowPane, col, row);
            }
        }
        pawnsGrid.setTranslateX(149);
        pawnsGrid.setTranslateY(145);
        pawnsGrid.setGridLinesVisible(true);
        pawnsGrid.setPickOnBounds(false);
    }

    private ImageView setCharacterImage(String character) {
        Image img = null;
        ImageView imageView = null;
        try {
            img = new Image(new FileInputStream("src/main/resources/Images/icons/" + character + "_icon.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (img != null) {
            imageView = new ImageView(img);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            imageView.setId(character);
        }
        return imageView;
    }

    public static Image toGrayScale(Image sourceImage) {
        PixelReader pixelReader = sourceImage.getPixelReader();

        int width = (int) sourceImage.getWidth();
        int height = (int) sourceImage.getHeight();

        WritableImage grayImage = new WritableImage(width, height);
        PixelWriter pixelWriter = grayImage.getPixelWriter();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = pixelReader.getArgb(x, y);

                int red = ((pixel >> 16) & 0xff);
                int green = ((pixel >> 8) & 0xff);
                int blue = (pixel & 0xff);

                int grayLevel = (int) (0.6162 * (double) red + 0.9152 * (double) green + 0.4722 * (double) blue) / 3;
                grayLevel = 255 - grayLevel; // Inverted the grayLevel value here.
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;

                pixelWriter.setArgb(x, y, -gray); // AMENDED TO -gray here.
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixelReader.getArgb(x, y);

                int red = ((pixel >> 16) & 0xff);
                int green = ((pixel >> 8) & 0xff);
                int blue = (pixel & 0xff);

                int grayLevel = (int) (0.2162 * red + 0.7152 * green + 0.0722 * blue) / 3;
                int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;

                grayImage.getPixelWriter().setArgb(x, y, gray);

            }
            return grayImage;
        }
        return grayImage;
    }
}

