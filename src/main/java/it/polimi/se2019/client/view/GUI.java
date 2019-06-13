package it.polimi.se2019.client.view;

import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.map.Square;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;

public class GUI extends Application {

    private String username;
    private GUIController guiController = new GUIController();
    private RemoteView myRemoteView;
    private Stage window;
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
    private VBox leftMenu = new VBox();
    private StackPane stack = new StackPane();
    private VBox rightPane = new VBox();
    private BorderPane borderPane = new BorderPane();
    private TextArea textArea = new TextArea("Welcome to Adrenaline!");
    static final String BUTTON_STYLE = "-fx-background-color: #3c3c3c;-fx-text-fill: #999999;";
    static final String LABEL_STYLE = "-fx-text-fill: #cecece";
    static final String BACKGROUND_STYLE = "-fx-background-color: #505050";
    static final String HIGHLIGHT_BUTTON_STYLE = "-fx-background-color: #bbbbbb;-fx-text-fill: #999999;";
    static final String TEXT_AREA_STYLE = "-fx-control-inner-background:#717171;  -fx-highlight-fill: #f1f7eb; -fx-highlight-text-fill: #717171; -fx-text-fill: #f1f7eb;-fx-border-color: #ffffff ";
    private int mapNumber = 1;
    private Scene scene;


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        setStage(primaryStage);

        setAmmo(mapNumber);

        setDeathTrackSkulls();

        setMapGrid();

        setCabinets();

        Label points = setPoints();

        Button moveButton = setButton("src/main/resources/Images/icons/move_icon.png", "Move");
        Button grabButton = setButton("src/main/resources/Images/icons/grab_icon.png", "Move and grab");
        Button shootButton = setButton("src/main/resources/Images/icons/shoot_icon.png", "Shoot");
        Button passButton = setButton("src/main/resources/Images/icons/pass_icon.png", "Pass turn and reload");
        Button powerUps = setButton("src/main/resources/Images/icons/powerup_icon.png", "");
        Button playersButton = setButton("src/main/resources/Images/icons/players_icon.png", "");
        Button weapons = setButton("src/main/resources/Images/icons/weapon_icon.png", "");

        moveButton.setOnAction(e -> {
            try {
                if(guiController.getRmiStub().getActivePlayer().equals(this.username)){
                    if(guiController.getRmiStub().checkNumberAction(username)){
                        guiController.getRmiStub().useAction(username);
                        if (!guiController.getMyRemoteView().getCanMove()) {
                            guiController.getMyRemoteView().setCanMove(true);
                            guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reacheableSquare(guiController.getMyRemoteView().getPosition(),3));
                            //textArea.setText("\n" + guiController.getMyRemoteView().getReachableSquare() + "\n" + textArea.getText());
                            for (int i = 0; i < 12; i++) {
                                Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
                                for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                                    if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                                        rectangle.setFill(Color.color(1, 1, 0, 0.4));
                                        rectangle.setOnMouseClicked(o -> {
                                            //textArea.setText("\nSquare #: " + rectangle.getId() + "\n" + textArea.getText());
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
                    textArea.setText("It's not your round!\n" + textArea.getText());
                }
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        grabButton.setOnAction(e -> {
            try {
                if(guiController.getRmiStub().getActivePlayer().equals(username)){
                    if(guiController.getRmiStub().checkNumberAction(username)){
                        guiController.getRmiStub().useAction(this.username);
                        if (!guiController.getMyRemoteView().getCanMove()) {
                            guiController.getMyRemoteView().setCanMove(true);
                            guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reacheableSquare(guiController.getMyRemoteView().getPosition(),1));
                            textArea.setText("\n" + guiController.getMyRemoteView().getReachableSquare() + "\n" + textArea.getText());
                            for (int i = 0; i < 12; i++) {
                                Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
                                for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                                    if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                                        rectangle.setFill(Color.color(0, 0.2, 1, 0.4));
                                        rectangle.setOnMouseClicked(o -> {
                                            //textArea.setText("\nSquare #: " + rectangle.getId() + "\n" + textArea.getText());
                                            try {
                                                guiController.getRmiStub().setNewPosition(guiController.getMyRemoteView().getUsername(), Integer.parseInt(rectangle.getId()));
                                                textArea.setText("New position: " + rectangle.getId() + "\n" + textArea.getText());
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
                                                if (!isSpawn) {
                                                    guiController.getRmiStub().pickUpAmmo(myRemoteView.getUsername());
                                                    textArea.setText("You have picked up an ammo\n" + textArea.getText());
                                                }
                                                else {
                                                    //TODO to implement
                                                    //TODO indexToPickUp e indexToSwitch sono due input dell'utente
                                                    int indexToPickUp = 0;
                                                    int indexToSwitch = 0;
                                                    if (myRemoteView.getWeapons().size() < 3) {
                                                        guiController.getRmiStub().pickUpWeapon(username, indexToPickUp);
                                                        textArea.setText("You have picked a weapon: " + myRemoteView.getWeapons().get(myRemoteView.getWeapons().size() - 1) + "\n" + textArea.getText());
                                                    } else if (myRemoteView.getWeapons().size() == 3) {
                                                        textArea.setText("You already have three weapons in you hand.\nChoose one to discard..." + "\n" + textArea.getText());
                                                    } else {
                                                        throw new Exception("You have more than three weapons in your hand\n");
                                                    }
                                                }
                                                guiController.getMyRemoteView().setCanMove(false);
                                            } catch (Exception exc) {
                                                exc.printStackTrace();
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    } else {
                        textArea.setText("You have already used two actions. Pass your turn\n" + textArea.getText());
                    }
                }
                else {
                    textArea.setText("It's not your round!\n" + textArea.getText());
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
        powerUps.setOnAction(e -> {
            HBox box = new HBox();
            ImageView powerUpView = null;
            for (PowerUp powerUp: myRemoteView.getPowerUp()) {

                powerUpView = new ImageView(createImage("src/main/resources/Images/PowerUps/" + powerUp.getColor() +"_"+ powerUp.getType() + ".png"));
                powerUpView.setPreserveRatio(true);
                powerUpView.setFitHeight(200);
                box.getChildren().add(powerUpView);

            }
            display(box, "Power ups");
        });
        playersButton.setOnAction(e -> {
            displayPlayers();
        });
        weapons.setOnAction(e -> {
            HBox box = new HBox();
            ImageView weaponView;
            Image weaponImage;
            for (Weapon obj : myRemoteView.getWeapons()) {
                weaponImage = createImage("src/main/resources/Images/Weapons/" + obj.getType() + ".png");
                if(!obj.getLoad())
                    weaponImage = toGrayScale(weaponImage);
                weaponView = new ImageView(weaponImage);
                weaponView.setPreserveRatio(true);
                weaponView.setFitHeight(300);
                box.getChildren().add(weaponView);

            }
            display(box, "Weapons");
        });

        setCubes();

        ImageView firstPlayer = setFirstPlayer();

        leftMenu.getChildren().addAll(moveButton, grabButton, shootButton, passButton, weapons, powerUps, playersButton, points, cubeBox);

        setTextArea();

        rightPane.getChildren().add(textArea);
        rightPane.setSpacing(10);

        stack.getChildren().addAll(setMap(), ammoSet, firstPlayer, deathTrack, cabinets, grid, pawnsGrid);
        Group root = new Group(stack);
        root.setTranslateY(-375);
        root.setTranslateX(25);
        borderPane.setStyle(BACKGROUND_STYLE);
        borderPane.setCenter(root);
        borderPane.setRight(rightPane);
        borderPane.setLeft(leftMenu);
        scene = new Scene(borderPane, 1300, 700);
        window.show();

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), e-> {

            if(myRemoteView != null){
                setCubes();
                setFigures();
                setWeaponView(redBox, myRemoteView.getCabinetRed().getSlot());
                setWeaponView(yellowBox, myRemoteView.getCabinetYellow().getSlot());
                setWeaponView(blueBox, myRemoteView.getCabinetBlue().getSlot());
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }

    private void setMapGrid(){
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

    private void setWeaponView(HBox cabinet, Weapon[] weaponsArray) {
        cabinet.getChildren().clear();
            for (int i = 0; i < 3; i++) {
                ImageView weaponView = null;
                if(weaponsArray[i] != null){
                    weaponView = new ImageView(createImage("src/main/resources/Images/Weapons/" + weaponsArray[i].getType() + ".png"));
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
                obj.setOnMouseExited(e ->
                    rightPane.getChildren().remove(box)
                );
            }
    }

    private void setDeathTrackSkulls() {

        Image skullImage = createImage("src/main/resources/Images/icons/skull_icon.png");
        ImageView skullView;

        for (int i = 0; i < 8; i++) {
            skullView = new ImageView(skullImage);
            skullView.setFitHeight(40);
            skullView.setSmooth(true);
            skullView.setPreserveRatio(true);
            deathTrack.getChildren().add(skullView);
        }
        deathTrack.setTranslateX(66);
        deathTrack.setTranslateY(40);
        deathTrack.setSpacing(8);
        deathTrack.setPickOnBounds(false);
    }

    private VBox setPlayerBoardsStack(){
        VBox players = new VBox();
        HBox damageBar;
        HBox deathBar;
        HBox markBar;
        StackPane playerStack;
        ImageView img;
        ImageView iconImg;
        Image image = null;
        Image icon = null;

        for(RemoteView view : guiController.getAllViews()){
            image = createImage("src/main/resources/Images/Playerboards/" + view.getCharacter()+ ".png");
            img = new ImageView(image);
            img.setFitHeight(120);
            img.setPreserveRatio(true);

            playerStack = new StackPane();
            damageBar = new HBox();
            deathBar = new HBox();
            markBar = new HBox();
            for(int i = 0; i<12; i++){
                icon = createImage("src/main/resources/Images/icons/blue_damage_icon.png");
                iconImg = new ImageView(icon);
                iconImg.setPreserveRatio(true);
                iconImg.setFitWidth(28);
                damageBar.getChildren().add(iconImg);
            }
            for(int i = 0 ; i<5;i++){
                icon = createImage("src/main/resources/Images/icons/skull_icon.png");
                iconImg = new ImageView(icon);
                iconImg.setPreserveRatio(true);
                iconImg.setFitWidth(27);
                deathBar.getChildren().add(iconImg);

            }
            for(int i = 0; i<4; i++){
                icon = createImage("src/main/resources/Images/icons/blue_damage_icon.png");
                iconImg = new ImageView(icon);
                iconImg.setPreserveRatio(true);
                iconImg.setFitWidth(32);
                markBar.getChildren().add(iconImg);
            }

            playerStack.getChildren().addAll(img,damageBar,deathBar,markBar);

            damageBar.setTranslateY(45);
            damageBar.setTranslateX(40);

            deathBar.setTranslateY(85);
            deathBar.setTranslateX(100);

            markBar.setTranslateX(230);

            players.getChildren().add(playerStack);
            players.setSpacing(5);
        }
        return players;
    }

    private void setAmmo(int map) {

        Image ammoBack;
        ammoBack = createImage("src/main/resources/Images/Ammo/ammoback.png");
        ammoSet.getChildren().clear();
        for (int i = 0; i < 12; i++) {
            ammoSet.getChildren().add(new ImageView(ammoBack));
        }
        for (Node obj : ammoSet.getChildren()) {
            obj.setTranslateX(500);
            obj.setTranslateY(500);
        }
        switch (map) {

            case 1: {
                setAmmoConfiguration1();
                break;
            }
            case 2: {
                setAmmoConfiguration2();
                break;
            }
            case 3: {
                setAmmoConfiguration3();
                break;
            }
            case 4: {
                setAmmoConfiguration4();
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

    private void setCubes() {
        cubeBox.getChildren().clear();
        ImageView cubeImage;
        cubeImage = new ImageView(createImage("src/main/resources/Images/icons/redCube.png"));
        cubeImage.setFitWidth(20);
        cubeImage.setPreserveRatio(true);
        Label redLabel = new Label(" " + getMyReds() + "  ", cubeImage);
        redLabel.setStyle("-fx-text-fill: #ff0000; -fx-background-color: #404040");

        cubeImage = new ImageView(createImage("src/main/resources/Images/icons/yellowCube.png"));
        cubeImage.setFitWidth(20);
        cubeImage.setPreserveRatio(true);
        Label yellowLabel = new Label(" "  + getMyYellows() + "  ", cubeImage);
        yellowLabel.setStyle("-fx-text-fill: #fff000; -fx-background-color: #404040");

        cubeImage = new ImageView(createImage("src/main/resources/Images/icons/blueCube.png"));
        cubeImage.setFitWidth(20);
        cubeImage.setPreserveRatio(true);
        Label blueLabel = new Label(" " + getMyBlues()+"  ", cubeImage);
        blueLabel.setStyle("-fx-text-fill: #0010ff; -fx-background-color: #404040;");

        cubeBox.getChildren().addAll(redLabel, yellowLabel, blueLabel);
        cubeBox.setSpacing(5);
    }

    private int getMyPoints() {
        if(myRemoteView!= null)
            return myRemoteView.getPoints();
        else return 0;
    }

    private int getMyReds(){
        if(myRemoteView != null)
            return myRemoteView.getCubes().getReds();
        else return 1;
    }

    private int getMyYellows(){
        if(myRemoteView != null)
            return myRemoteView.getCubes().getYellows();
        else return 1;
    }

    private int getMyBlues(){
        if(myRemoteView != null)
            return myRemoteView.getCubes().getBlues();
        else return 1;
    }

    private void closeProgram() {
        boolean answer = ConfirmBox.display("Exit Adrenaline", "Are you sure?");
        if (answer) {
            window.close();
        }
    }

    private Button setButton(String path, String text) {
        ImageView buttonView = new ImageView(createImage(path));
        buttonView.setFitWidth(50);
        buttonView.setPreserveRatio(true);
        Button newButton = new Button("", buttonView);
        newButton.setOnAction(e -> textArea.setText(text + "\n" + textArea.getText()));
        newButton.setOnMouseEntered(e -> newButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        newButton.setOnMouseExited(e -> newButton.setStyle(BUTTON_STYLE));
        newButton.setStyle(BUTTON_STYLE);


        return newButton;
    }

    private Scene setLoginScene() {
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
                    exitButton.setOnMouseExited(exit ->
                        exitButton.setStyle(BUTTON_STYLE)
                    );
                    exitButton.setOnMouseEntered(enter ->
                        exitButton.setStyle(HIGHLIGHT_BUTTON_STYLE)
                    );
                    exitButton.setOnAction(o -> {
                        Scene backScene;
                        backScene = setLoginScene();
                        window.setScene(backScene);
                    });
                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(errorLabel, exitButton);
                    vBox.setSpacing(20);
                    vBox.setStyle(BACKGROUND_STYLE);
                    vBox.setAlignment(Pos.CENTER);
                    exitButton.setAlignment(Pos.CENTER);
                    errorLabel.setAlignment(Pos.CENTER);
                    Scene errorScene = new Scene(vBox, 300, 200);
                    window.setScene(errorScene);
                }
                else {
                    guiController.setUsername(usernameTyped);
                    this.username = usernameTyped;
                    guiController.getRmiStub().register(usernameTyped, guiController);
                    myRemoteView = guiController.getMyRemoteView();
                    setWaitScene();
                    textArea.setText(usernameTyped + "\n" + textArea.getText());
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
        Scene newScene = new Scene(pane, 400, 250);
        pane.setStyle(BACKGROUND_STYLE);
        return newScene;

    }

    private void setWaitScene(){
        Label waitLabel = new Label("Please wait.");
        waitLabel.setStyle(LABEL_STYLE);
        BorderPane pane = new BorderPane();
        pane.setStyle(BACKGROUND_STYLE);
        pane.setCenter(waitLabel);
        waitLabel.setAlignment(Pos.CENTER);
        Scene waitScene = new Scene(pane,300,200);
        window.setScene(waitScene);

        if(false){
            Timeline wait = new Timeline(new KeyFrame(Duration.seconds(10), e->
                window.setScene(scene)
            ));
            wait.setCycleCount(Timeline.INDEFINITE);
            wait.play();
        }else window.setScene(scene);

    }

    private VBox setMapSelector() {
        VBox sideBox = new VBox();
        RadioButton button1 = new RadioButton("Map 1");
        button1.setToggleGroup(mapSelector);
        button1.setStyle(LABEL_STYLE);
        button1.setSelected(true);
        RadioButton button2 = new RadioButton("Map 2");
        button2.setStyle(LABEL_STYLE);
        button2.setToggleGroup(mapSelector);
        RadioButton button3 = new RadioButton("Map 3");
        button3.setStyle(LABEL_STYLE);
        button3.setToggleGroup(mapSelector);
        RadioButton button4 = new RadioButton("Map 4");
        button4.setStyle(LABEL_STYLE);
        button4.setToggleGroup(mapSelector);
        Label selectMap = new Label("Select map: ");
        selectMap.setStyle(LABEL_STYLE);
        sideBox.getChildren().addAll(selectMap, button1, button2, button3, button4);
        sideBox.setSpacing(10);
        sideBox.setAlignment(Pos.CENTER);
        sideBox.setStyle(BUTTON_STYLE);
        return sideBox;
    }

    private void setTextArea(){
        textArea.setPrefWidth(225);
        textArea.setPrefHeight(300);
        textArea.setTranslateX(-450);
        textArea.setTranslateY(10);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle(TEXT_AREA_STYLE);
    }

    private void setFigures() {
        FlowPane flowPane;
        pawnsGrid.getChildren().clear();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                flowPane = new FlowPane();
                //for every player, if there are players in this square fills the flowpane with them
                for(RemoteView view : guiController.getAllViews()){
                    if(view.getPosition() == row * 4 + col){
                        flowPane.getChildren().add(setCharacterImage(view.getCharacter()));
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
        pawnsGrid.setMouseTransparent(true);
    }

    private ImageView setCharacterImage(String character) {

        ImageView imageView = new ImageView(createImage("src/main/resources/Images/icons/" + character + "_icon.png"));
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        imageView.setId(character);

        return imageView;
    }

    private static Image toGrayScale(Image sourceImage) {
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

    private void displayPlayers(){

        Stage playersWindow = new Stage();
        VBox layout = setPlayerBoardsStack();
        Label info = new Label("Current players are:");
        BorderPane playersPane = new BorderPane();
        info.setStyle(LABEL_STYLE);
        playersWindow.initModality(Modality.APPLICATION_MODAL);
        playersWindow.setTitle("Players");

        Button closeButton = setHomeButton(playersWindow);
        playersPane.setTop(info);
        playersPane.setCenter(layout);
        playersPane.setBottom(closeButton);
        playersPane.setStyle(BACKGROUND_STYLE);
        playersPane.setAlignment(closeButton,Pos.CENTER);
        Scene playersScene = new Scene(playersPane);
        playersWindow.setScene(playersScene);
        playersWindow.setWidth(500);
        playersWindow.showAndWait();
    }

    private void display(Pane pane, String message){
        Stage window = new Stage();
        Pane layout = null;
        Label info = new Label("Current "+ message + " are:");
        BorderPane borderPane = new BorderPane();

        info.setStyle("-fx-text-fill: #bdbdbd");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(message);

        Button closeButton = setHomeButton(window);
        if(pane instanceof VBox){
            layout = new VBox();
        }else if(pane instanceof HBox){
            layout = new HBox();
        }

        if(layout != null){
            layout.getChildren().addAll(pane.getChildren());
            layout.setStyle(BACKGROUND_STYLE);
        }

        borderPane.setTop(info);
        borderPane.setCenter(layout);
        borderPane.setBottom(closeButton);
        borderPane.setStyle(BACKGROUND_STYLE);
        borderPane.setAlignment(closeButton,Pos.CENTER);
        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.showAndWait();
    }

    private Button setHomeButton(Stage window){
        ImageView img = new ImageView(createImage("src/main/resources/Images/icons/home.png"));
        img.setPreserveRatio(true);
        img.setFitHeight(50);
        Button closeButton = new Button("",img);
        closeButton.setStyle(BUTTON_STYLE);
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(BUTTON_STYLE));
        closeButton.setOnAction(e-> window.close());
        return closeButton;
    }

    private Image createImage(String path){
        Image image = null;

        try{
            image = new Image(new FileInputStream(path));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return image;
    }

    private void setStage(Stage primaryStage){
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
    }

    private ImageView setMap(){
        ImageView imageView = new ImageView(createImage("src/main/resources/Images/Maps/Map" + mapNumber + ".png"));
        imageView.setFitHeight(700);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void setCabinets(){
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
    }

    private Label setPoints(){
        Label points = new Label("Points: " + getMyPoints());
        points.setStyle("-fx-background-color: #404040; -fx-text-fill: #aaaaaa");
        return points;
    }

    private ImageView setFirstPlayer(){
        ImageView firstPlayer = new ImageView(createImage("src/main/resources/Images/Playerboards/first_player.png"));
        firstPlayer.setFitHeight(100);
        firstPlayer.setPreserveRatio(true);
        firstPlayer.setTranslateX(-400);
        firstPlayer.setTranslateY(275);
        return firstPlayer;
    }

    private void setAmmoConfiguration1(){
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
    }
    private void setAmmoConfiguration2(){
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
    }
    private void setAmmoConfiguration3(){
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
    }
    private void setAmmoConfiguration4(){
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
    }
}

