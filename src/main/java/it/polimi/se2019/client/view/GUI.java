package it.polimi.se2019.client.view;

import it.polimi.se2019.client.controller.GUIController;
import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI extends Application {

    private Logger logger = Logger.getAnonymousLogger();

    private String username;
    private RemoteView myRemoteView;
    private Stage window;
    private GUIController guiController;
    private ToggleGroup mapSelector = new ToggleGroup();
    private GridPane grid = new GridPane();
    private GridPane pawnsGrid = new GridPane();
    private HBox redBox = new HBox();
    private HBox blueBox = new HBox();
    //private boolean firstSpawn = true;
    private HBox yellowBox = new HBox();
    private StackPane cabinets = new StackPane();
    private HBox killShotTrack = new HBox();
    private VBox cubeBox = new VBox();
    private StackPane ammoSet = new StackPane();
    private VBox leftMenu = new VBox();
    private VBox userInfoBox = new VBox();
    private StackPane stack = new StackPane();
    private VBox rightPane = new VBox();
    private int indexToPickUp = -1;
    private int indexToDrop = -1;
    private Pane firstPlayer = new Pane();
    private BorderPane borderPane = new BorderPane();
    private TextArea textArea = new TextArea("Welcome to Adrenaline!");
    static final String BUTTON_STYLE = "-fx-background-color: #3c3c3c;-fx-text-fill: #999999;";
    private static final String LABEL_STYLE = "-fx-text-fill: #cecece";
    private static final String BACKGROUND_STYLE = "-fx-background-color: #505050";
    private static final String TEXT_FIELD_STYLE = "-fx-background-color: #726B72";
    private static final String distructor = "-fx-text-fill: #fff117";
    private static final String banshee = "-fx-text-fill: #1879FF";
    private static final String dozer = "-fx-text-fill: #726B72";
    private static final String sprog = "-fx-text-fill: #077200";
    private static final String violet = "-fx-text-fill: #E81AFF";
    private static final String FIRST_SPAWN_TEXT = "Choose one of the two power ups to discard.\nIt determines your spawn location, based on its color.";
    static final String HIGHLIGHT_BUTTON_STYLE = "-fx-background-color: #bbbbbb;-fx-text-fill: #999999;";
    private static final String TEXT_AREA_STYLE = "-fx-control-inner-background:#717171;  -fx-highlight-fill: #f1f7eb; -fx-highlight-text-fill: #717171; -fx-text-fill: #f1f7eb;-fx-border-color: #ffffff ";
    private static final String WEAPONS_PATH = "src/main/resources/images/weapons/";
    private static final String POWERUPS_PATH = "src/main/resources/images/powerUps/";
    private static final String ICONS_PATH = "src/main/resources/images/icons/";
    private int mapNumber;
    private Scene scene;

    @Override
    public void start(Stage primaryStage){

        setStage(primaryStage);
        window.show();

    }

    /**
     * It displays the grid showing coloured rectangles
     * during the game progress.
     */
    private void setMapGrid(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Rectangle box = new Rectangle(160, 160);
                box.setFill(Color.color(1, 1, 1, 0));
                box.setId(Integer.toString(row * 4 + col));
                grid.add(box, col, row);
            }
        }
        grid.setTranslateX(149);
        grid.setTranslateY(145);
        grid.setPickOnBounds(false);
    }

    /**
     * It shows an ImageView on the game scene while hovering on
     * a weapon. Its purpose is to magnify the cards.
     * @param cabinet the cabinet containing the weapon the player wants to zoom.
     * @param weaponsArray the weapon array.
     */
    private void setWeaponView(HBox cabinet, Weapon[] weaponsArray) {
        cabinet.getChildren().clear();
            for (int i = 0; i < 3; i++) {
                ImageView weaponView = null;
                if(weaponsArray[i] != null){
                    weaponView = new ImageView(createImage(WEAPONS_PATH + weaponsArray[i].getType() + ".png"));
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

    /**
     * It creates the HBox containing skulls or tears, based on
     * the number of death occured during the game.
     */
    private void setKillShotTrack() {
        killShotTrack.getChildren().clear();
        Image skullImage = createImage("src/main/resources/images/icons/skull_icon.png");
        Image characterImage;
        ImageView characterImageView;
        ImageView skullView;
        ArrayList<Player> killShotTrackList;
        try{
            killShotTrackList = guiController.getMyRemoteView().getKillShotTrack();

            for(Player player : killShotTrackList){
                characterImage = createImage(ICONS_PATH + player.getCharacter()+"_damage_icon.png");
                characterImageView = new ImageView(characterImage);
                characterImageView.setFitWidth(40);
                characterImageView.setSmooth(true);
                characterImageView.setPreserveRatio(true);
                killShotTrack.getChildren().add(characterImageView);
            }

            for (int i = 0; i < 8-killShotTrackList.size(); i++) {
                skullView = new ImageView(skullImage);
                skullView.setFitWidth(40);
                skullView.setSmooth(true);
                skullView.setPreserveRatio(true);
                killShotTrack.getChildren().add(skullView);
            }
        }catch(Exception e){

            logger.log(Level.INFO,"Killshot track failed to load",e);
        }


        killShotTrack.setTranslateX(64);
        killShotTrack.setTranslateY(40);
        killShotTrack.setSpacing(-1);
        killShotTrack.setPickOnBounds(false);
    }

    /**
     * It creates the Playerboards StackPanes.
     * @return a VBox which include all the players boards.
     */
    private VBox setPlayerBoardsStack(){
        VBox players = new VBox();
        HBox damageBar;
        HBox deathBar;
        HBox markBar;
        StackPane playerStack;
        ImageView img;
        ImageView iconImg;
        Image image = null;
        Image icon;
        Label playerName;
        for(RemoteView view : guiController.getAllViews()){

            playerName = new Label(view.getUsername());
            playerStack = new StackPane();
            damageBar = new HBox();
            deathBar = new HBox();
            markBar = new HBox();
            for(int i = 0; i<view.getDamagePlayerBoard().size(); i++){
                icon = createImage(ICONS_PATH +view.getDamagePlayerBoard().get(i).getCharacter()+"_damage_icon.png");
                iconImg = new ImageView(icon);
                iconImg.setPreserveRatio(true);
                iconImg.setFitWidth(28);
                damageBar.getChildren().add(iconImg);
            }
            for(int i = 0 ; i<view.getDeathsPlayerBoard();i++){
                icon = createImage("src/main/resources/images/icons/skull_icon.png");
                iconImg = new ImageView(icon);
                iconImg.setPreserveRatio(true);
                iconImg.setFitWidth(35);
                deathBar.getChildren().add(iconImg);

            }
            for(int i = 0; i<view.getMarkPlayerBoard().size(); i++){
                icon = createImage(ICONS_PATH +view.getMarkPlayerBoard().get(i).getAggressorPlayer().getCharacter()+"_"+ view.getMarkPlayerBoard().get(i).getMarks()+"_mark_icon.png");
                iconImg = new ImageView(icon);
                iconImg.setPreserveRatio(true);
                iconImg.setFitWidth(32);
                markBar.getChildren().add(iconImg);
            }

            if(view.getTypePlayerBoard() == 0){
                image = createImage("src/main/resources/images/playerboards/"+view.getCharacter()+".png");
                damageBar.setTranslateX(40);
                deathBar.setTranslateX(100);
            }
            else if(view.getTypePlayerBoard() == 1) {
                image = createImage("src/main/resources/images/playerboards/"+view.getCharacter()+"_frenzy.png");
                damageBar.setTranslateX(47);
                deathBar.setTranslateX(125);
            }

            damageBar.setTranslateY(45);
            deathBar.setTranslateY(87);
            img = new ImageView(image);
            img.setFitHeight(120);
            img.setPreserveRatio(true);
            markBar.setTranslateX(230);
            players.getChildren().add(playerName);
            players.getChildren().add(playerStack);
            playerName.setStyle(LABEL_STYLE);
            playerName.setAlignment(Pos.CENTER);

            playerStack.getChildren().addAll(img,damageBar,deathBar,markBar);

        }
        players.setSpacing(2);
        players.setAlignment(Pos.CENTER);
        return players;
    }

    /**
     * It arranges the ammo tile images on the background map.
     * @param map the number code of the map.
     */
    private void setAmmo(int map) {

        Image ammoBack;

        ammoBack = createImage("src/main/resources/images/ammo/ammoback.png");
        ammoSet.getChildren().clear();
        for(int i=0; i<12;i++){
            ammoSet.getChildren().add(new ImageView(ammoBack));
        }
        switch (map) {

            case 1: {
                setAmmoA1();
                setAmmoA2();
                break;
            }
            case 2: {
                setAmmoB1();
                setAmmoA2();
                break;
            }
            case 3: {
                setAmmoB1();
                setAmmoB2();
                break;
            }
            case 4: {
                setAmmoA1();
                setAmmoB2();
                break;
            }
            default:
                logger.log(Level.INFO,"Failed to load ammo tiles");
        }
        for (Node obj : ammoSet.getChildren()) {
            obj.setScaleX(0.3);
            obj.setScaleY(0.3);
        }

        try{
            Square[] realSquares = guiController.getRmiStub().getAllSquares();
            for(int i=0;i<12;i++){
                if(realSquares[i].isSpawnPoint() || !realSquares[i].isFull() || realSquares[i].getColor().equals("")){
                    ammoSet.getChildren().get(i).setTranslateX(-350);
                    ammoSet.getChildren().get(i).setTranslateY(250);
                }
            }
        }catch (Exception e){
            logger.log(Level.INFO,"setAmmo() Error",e);
        }
    }

    /**
     * It sets three labels showing the number of cubes
     * the player has.
     */
    private void setCubes() {
        cubeBox.getChildren().clear();
        ImageView cubeImage;
        cubeImage = new ImageView(createImage("src/main/resources/images/icons/redCube.png"));
        cubeImage.setFitWidth(30);
        cubeImage.setPreserveRatio(true);
        Label redLabel = new Label(" " + getMyReds() + "  ", cubeImage);
        redLabel.setStyle("-fx-text-fill: #ff0000; -fx-background-color: #202020");

        cubeImage = new ImageView(createImage("src/main/resources/images/icons/yellowCube.png"));
        cubeImage.setFitWidth(30);
        cubeImage.setPreserveRatio(true);
        Label yellowLabel = new Label(" "  + getMyYellows() + "  ", cubeImage);
        yellowLabel.setStyle("-fx-text-fill: #fff000; -fx-background-color: #202020");

        cubeImage = new ImageView(createImage("src/main/resources/images/icons/blueCube.png"));
        cubeImage.setFitWidth(30);
        cubeImage.setPreserveRatio(true);
        Label blueLabel = new Label(" " + getMyBlues()+"  ", cubeImage);
        blueLabel.setStyle("-fx-text-fill: #1879ff; -fx-background-color: #202020;");

        cubeBox.getChildren().addAll(redLabel, yellowLabel, blueLabel);
        cubeBox.setSpacing(2);
    }
    /**
     * It return the number of red cubes the player has.
     * It's used to display the owner's cubes.
     * @return the number of red cubes.
     */
    private int getMyReds(){
        return myRemoteView.getCubes().getReds();
    }
    /**
     * It return the number of yellow cubes the player has.
     * It's used to display the owner's cubes.
     * @return the number of yellow cubes.
     */
    private int getMyYellows(){
        return myRemoteView.getCubes().getYellows();
    }

    /**
     * It return the number of blue cubes the player has.
     * It's used to display the owner's cubes.
     * @return the number of blue cubes.
     */
    private int getMyBlues(){
        return myRemoteView.getCubes().getBlues();
    }

    /**
     * After attempting to close the main window, asks the user to
     * confirm his choice.
     */
    private void closeProgram() {
        boolean closeAnswer = ConfirmBox.display("Exit Adrenaline", "Are you sure?");
        if (closeAnswer) {
            Platform.exit();
            System.exit(0);
            //window.close();
        }
    }

    /**
     * It creates a button with an image showing on it.
     * @param path the image path.
     * @return it return the button.
     */
    private Button setImageButton(String path) {
        ImageView buttonView = new ImageView(createImage(path));
        buttonView.setFitWidth(50);
        buttonView.setPreserveRatio(true);
        Button newButton = new Button("", buttonView);
        setResponsiveButton(newButton);
        return newButton;
    }

    /**
     * It set the button to be color responsive on mouse hovering.
     *
     * @param button the button to be styled.
     */
    private void setResponsiveButton(Button button){
        button.setStyle(BUTTON_STYLE);
        button.setOnMouseEntered(e -> button.setStyle(HIGHLIGHT_BUTTON_STYLE));
        button.setOnMouseExited(e -> button.setStyle(BUTTON_STYLE));
    }

    /**
     * It creates the login scene window including:
     * -Username box
     * -Password box
     * -IP box
     * -Map selector
     * -Maps preview
     * @return
     */
    private Scene setLoginScene() {

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(50, 75, 50, 25));
        layout.setVgap(10);

        //Name input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Username");
        nameInput.setStyle(TEXT_FIELD_STYLE);
        GridPane.setConstraints(nameInput, 0, 0);

        //Password input
        PasswordField passInput = new PasswordField();
        passInput.setStyle(TEXT_FIELD_STYLE);
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 0, 1);

        //IP input
        TextField ipInput = new TextField();
        ipInput.setPromptText("Server IP");
        ipInput.setStyle(TEXT_FIELD_STYLE);
        ipInput.setPromptText("Server IP");
        GridPane.setConstraints(ipInput, 0, 2);

        //login button
        Button loginButton = new Button("Log in");
        GridPane.setConstraints(loginButton, 0, 3);
        loginButton.setOnAction(e -> {
            try {
                guiController = new GUIController(ipInput.getText(), this);
                String usernameTyped = nameInput.getText();
                String check = guiController.getRmiStub().checkUsername2(usernameTyped);
                if (check.equals("AlreadyUsed")) {
                    Label errorLabel = new Label("Selected name already taken, please retry.");
                    errorLabel.setStyle("-fx-text-fill: #ff0000");
                    Button exitButton = new Button("Back");
                    setResponsiveButton(exitButton);
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
                else if(check.equals("NotUsed")){

                    RadioButton mapChoice = (RadioButton) mapSelector.getSelectedToggle();
                    guiController.setUsername(usernameTyped);
                    this.username = usernameTyped;
                    guiController.getRmiStub().register(usernameTyped, guiController,Integer.parseInt(mapChoice.getText()));
                    myRemoteView = guiController.getMyRemoteView();
                    textArea.setText(usernameTyped + "\n" + textArea.getText());
                    setWaitScene();
                }
                else if(check.equals("Reconnect")) {
                    guiController.setUsername(usernameTyped);
                    this.username = usernameTyped;
                    guiController.getRmiStub().reconnect(usernameTyped, guiController);
                    myRemoteView = guiController.getMyRemoteView();
                    setGameScene();
                    window.setScene(scene);

                }
                else if (check.equals("CantConnect")) {
                    Scene backScene;
                    backScene = setLoginScene();
                    window.setScene(backScene);
                }
            } catch (Exception ex) {
                logger.log(Level.INFO,"Failed to load login window",ex);
            }

        });
        BorderPane pane = new BorderPane();
        HBox sideBox = setMapSelector();
        pane.setLeft(sideBox);
        pane.setCenter(layout);
        pane.setPadding(new Insets(10, 10, 10, 10));
        setResponsiveButton(loginButton);
        loginButton.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(nameInput, passInput, ipInput, loginButton);
        layout.setAlignment(Pos.CENTER_LEFT);
        Scene newScene = new Scene(pane, 500, 400);
        pane.setStyle(BACKGROUND_STYLE);
        return newScene;
    }

    /**
     * It creates a waiting scene, showing the number of players
     * waiting for the game to start.
     */
    public void setWaitScene(){
        Label waitLabel;
        BorderPane waitPane;
        waitLabel = new Label("Number of players in the lobby: " + guiController.getAllViews().size() + "\nPlease wait...");
        waitLabel.setStyle(LABEL_STYLE);
        waitPane = new BorderPane();
        waitPane.setStyle(BACKGROUND_STYLE);
        waitPane.setCenter(waitLabel);
        waitLabel.setAlignment(Pos.CENTER);
        Scene waitScene = new Scene(waitPane,300,200);
        window.setScene(waitScene);

/*
        Timeline waitMinimumPlayers = new Timeline(new KeyFrame(Duration.seconds(1), e-> {
            try{

                //if(guiController.getRmiStub().getMatch().getAllPlayers().size()<3){
                if(false){

                    Timeline waitAdditionalPlayers = new Timeline(new KeyFrame(Duration.seconds(1), h-> {
                        setWaitScene();
                        window.setScene(waitScene);
                    }
                    ));
                    waitAdditionalPlayers.setCycleCount(1);
                    waitAdditionalPlayers.play();

                } else{
                    window.close();
                    setGameScene();
                    window.setScene(scene);
                    window.show();
                }

            }catch (Exception o){
                logger.log(Level.INFO,"TimeLine error",o);
            }

        }
        ));

        waitMinimumPlayers.setCycleCount(1);
        waitMinimumPlayers.play();
*/

    }

    /**
     * It shows a preview of the maps inside of the login window.
     * @return the HBox containing the ImageViews.
     */
    private HBox setMapSelector() {
        VBox sideBox = new VBox();
        VBox mapsBox = new VBox();
        HBox box1 = new HBox();
        ImageView map1 = new ImageView(createImage("src/main/resources/images/maps/map1_preview.png"));
        ImageView map2 = new ImageView(createImage("src/main/resources/images/maps/map2_preview.png"));
        ImageView map3 = new ImageView(createImage("src/main/resources/images/maps/map3_preview.png"));
        ImageView map4 = new ImageView(createImage("src/main/resources/images/maps/map4_preview.png"));
        map1.setPreserveRatio(true);
        map2.setPreserveRatio(true);
        map3.setPreserveRatio(true);
        map4.setPreserveRatio(true);
        map1.setFitWidth(100);
        map2.setFitWidth(100);
        map3.setFitWidth(100);
        map4.setFitWidth(100);
        mapsBox.getChildren().addAll(map1,map2,map3,map4);
        box1.prefHeightProperty().bind(box1.heightProperty());
        box1.setPrefWidth(200);
        box1.setTranslateX(-110);
        TranslateTransition menuTranslation = new TranslateTransition(Duration.millis(500), box1);

        menuTranslation.setFromX(-110);
        menuTranslation.setToX(0);

        box1.setOnMouseEntered(evt -> {
            menuTranslation.setRate(1);
            menuTranslation.play();
        });
        box1.setOnMouseExited(evt -> {
            menuTranslation.setRate(-1);
            menuTranslation.play();
        });


        RadioButton button1 = new RadioButton("1");
        button1.setToggleGroup(mapSelector);
        button1.setStyle(LABEL_STYLE);
        button1.setSelected(true);

        RadioButton button2 = new RadioButton("2");
        button2.setStyle(LABEL_STYLE);
        button2.setToggleGroup(mapSelector);

        RadioButton button3 = new RadioButton("3");
        button3.setStyle(LABEL_STYLE);
        button3.setToggleGroup(mapSelector);

        RadioButton button4 = new RadioButton("4");
        button4.setStyle(LABEL_STYLE);
        button4.setToggleGroup(mapSelector);

        Label selectMap = new Label("Select map\npreference : ");
        selectMap.setStyle(LABEL_STYLE);
        sideBox.getChildren().addAll(selectMap,button1, button2, button3, button4);
        box1.getChildren().addAll(mapsBox,sideBox);
        box1.setSpacing(10);
        mapsBox.setAlignment(Pos.CENTER);
        mapsBox.setStyle(BUTTON_STYLE);
        mapsBox.setSpacing(10);
        sideBox.setSpacing(20);
        sideBox.setAlignment(Pos.CENTER);
        sideBox.setStyle(BUTTON_STYLE);
        return box1;
    }

    /**
     * It creates the TextArea element used to display information
     * during the game progress.
     */
    private void setTextArea(){
        textArea.setPrefWidth(225);
        textArea.setPrefHeight(300);
        textArea.setTranslateX(-450);
        textArea.setTranslateY(10);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle(TEXT_AREA_STYLE);
    }

    /**
     * It places each player figure on the board, depending on their position.
     */
    private void setFigures() {
        FlowPane flowPane;
        pawnsGrid.getChildren().clear();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                flowPane = new FlowPane();
                //for every player, if there are players in this square fills the flowpane with them
                for(RemoteView view : guiController.getAllViews()){
                    if(view.getPosition() == row * 4 + col  && view.getPosition()!= -1){
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
        pawnsGrid.setPickOnBounds(false);
        pawnsGrid.setMouseTransparent(true);
    }

    /**
     * The character icon image.
     * @param character the name of the character (dozer,banshee,sprog,distructor,violet).
     * @return the ImageView created.
     */
    private ImageView setCharacterImage(String character) {

        ImageView imageView = new ImageView(createImage(ICONS_PATH + character + "_icon.png"));
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        imageView.setId(character);

        return imageView;
    }

    /**
     * Transform the image in a gray scale image.
     * Used when a weapon is unloaded.
     * @param sourceImage the image to be transformed.
     * @return the gray-scaled image.
     */
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

    /**
     * It displays the players' boards.
     * Those boards are updated when player receives damage, marks
     * or are killed.
     */
    private void displayPlayers(){

        Stage playersWindow = new Stage();
        VBox layout = setPlayerBoardsStack();
        BorderPane playersPane = new BorderPane();
        playersWindow.initModality(Modality.APPLICATION_MODAL);
        playersWindow.setTitle("Players");
        Button closeButton = setHomeButton(playersWindow);
        playersPane.setCenter(layout);
        playersPane.setBottom(closeButton);
        playersPane.setStyle(BACKGROUND_STYLE);
        playersPane.setAlignment(closeButton,Pos.CENTER);
        Scene playersScene = new Scene(playersPane);
        playersWindow.setScene(playersScene);
        playersWindow.setWidth(500);
        playersWindow.setResizable(false);
        playersWindow.showAndWait();
    }

    /**
     * A button used to close a window.
     * @param window window to be closed clicking on the button.
     * @return the button created.
     */
    private Button setHomeButton(Stage window){
        ImageView img = new ImageView(createImage("src/main/resources/images/icons/home.png"));
        img.setPreserveRatio(true);
        img.setFitHeight(50);
        Button closeButton = new Button("",img);
        setResponsiveButton(closeButton);
        closeButton.setOnAction(e-> window.close());
        return closeButton;
    }

    /**
     * It creates an Image object.
     * @param path the path used for producing the image.
     * @return the Image object created.
     */
    private Image createImage(String path){
        Image image = null;

        try{
            image = new Image(new FileInputStream(path));
        }catch(FileNotFoundException e){
            logger.log(Level.INFO,"createImage failed",e);        }
        return image;
    }

    /**
     * It sets the window's properties.
     * @param primaryStage first stage.
     */
    private void setStage(Stage primaryStage){
        window = primaryStage;
        window.setScene(setLoginScene());
        window.setTitle("Adrenaline");
        window.setResizable(false);
        window.setFullScreen(false);
        window.alwaysOnTopProperty();
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    /**
     * Set the background map
     * @return the ImageView containing the background map.
     */
    private ImageView setMap(){
        ImageView imageView = new ImageView(createImage("src/main/resources/images/maps/Map" + mapNumber + ".png"));
        imageView.setFitHeight(700);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Arrange the HBoxes around the game scene to match the background map.
     */
    private void setCabinets(){
        setWeaponView(redBox, myRemoteView.getCabinetRed().getSlot());
        setWeaponView(yellowBox, myRemoteView.getCabinetYellow().getSlot());
        setWeaponView(blueBox, myRemoteView.getCabinetBlue().getSlot());

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

    /**
     * Set the First Player token on the main game scene.
     */
    private void setFirstPlayer(){
        try{
            if(guiController.getRmiStub().isFirstPlayer(this.username)){
                firstPlayer.getChildren().clear();
                ImageView firstPlayerView = new ImageView(createImage("src/main/resources/images/playerboards/first_player.png"));
                firstPlayerView.setFitHeight(100);
                firstPlayerView.setPreserveRatio(true);
                firstPlayerView.setTranslateX(30);
                firstPlayerView.setTranslateY(580);
                firstPlayer.getChildren().add(firstPlayerView);
            }
        } catch (RemoteException exc){
            logger.log(Level.INFO,"FirstPlayer error",exc);
        }
    }

    /**
     * Set the first half of the map ammo configuration.
     */
    private void setAmmoA1(){
        //square 0
        ammoSet.getChildren().get(0).setTranslateX(-255);
        ammoSet.getChildren().get(0).setTranslateY(-80);

        //square 1
        ammoSet.getChildren().get(1).setTranslateX(-120);
        ammoSet.getChildren().get(1).setTranslateY(-160);

        //square 5
        ammoSet.getChildren().get(5).setTranslateX(-130);
        ammoSet.getChildren().get(5).setTranslateY(40);

        //square 8
        ammoSet.getChildren().get(8).setTranslateX(-250);
        ammoSet.getChildren().get(8).setTranslateY(210);

        //square 9
        ammoSet.getChildren().get(9).setTranslateX(-120);
        ammoSet.getChildren().get(9).setTranslateY(210);
    }

    /**
     * Set the second half of the map ammo configuration.
     */
    private void setAmmoA2(){

        //square 6
        ammoSet.getChildren().get(6).setTranslateX(55);
        ammoSet.getChildren().get(6).setTranslateY(50);

        //square 7
        ammoSet.getChildren().get(7).setTranslateX(265);
        ammoSet.getChildren().get(7).setTranslateY(55);

        //square 10
        ammoSet.getChildren().get(10).setTranslateX(35);
        ammoSet.getChildren().get(10).setTranslateY(215);
    }

    /**
     * Set the first half of the map ammo configuration.
     */
    private void setAmmoB1(){
        //square 0
        ammoSet.getChildren().get(0).setTranslateX(-260);
        ammoSet.getChildren().get(0).setTranslateY(-160);

        //square 1
        ammoSet.getChildren().get(1).setTranslateX(-115);
        ammoSet.getChildren().get(1).setTranslateY(-90);

        //square 5
        ammoSet.getChildren().get(5).setTranslateX(-125);
        ammoSet.getChildren().get(5).setTranslateY(30);

        //square 9
        ammoSet.getChildren().get(9).setTranslateX(-120);
        ammoSet.getChildren().get(9).setTranslateY(210);

    }

    /**
     * Set the second half of the map ammo configuration.
     */
    private void setAmmoB2(){

        //square 3
        ammoSet.getChildren().get(3).setTranslateX(265);
        ammoSet.getChildren().get(3).setTranslateY(-90);

        //square 6
        ammoSet.getChildren().get(6).setTranslateX(50);
        ammoSet.getChildren().get(6).setTranslateY(80);

        //square 7
        ammoSet.getChildren().get(7).setTranslateX(190);
        ammoSet.getChildren().get(7).setTranslateY(77);

        //square 9
        ammoSet.getChildren().get(9).setTranslateX(-120);
        ammoSet.getChildren().get(9).setTranslateY(210);

        //square 10
        ammoSet.getChildren().get(10).setTranslateX(60);
        ammoSet.getChildren().get(10).setTranslateY(210);
    }

    /**
     * Reset the squares back to normal.
     */
    private void restoreSquares(){
        for (int j = 0; j < 12; j++) {
            Rectangle rect = (Rectangle) grid.getChildren().get(j);
            rect.setFill(Color.color(1, 1, 1, 0.0));
            rect.setOnMouseClicked(g -> {
            });
            rect.setOnMouseEntered( enter ->
                    setInvisible(rect));
            rect.setOnMouseExited( exit ->
                    setInvisible(rect));
        }
    }

    /**
     * It displays the player's infos:
     * -Player's name
     * -Number of cubes owned
     * -Points
     * -Number of moves left
     * -Current active player
     */
    private void setUserInfos(){
        Label userName = new Label(" "+ myRemoteView.getUsername());
        Label moves = new Label("Moves: "+ myRemoteView.getNumberOfActions());
        Label points = new Label("Points " + myRemoteView.getPoints());
        Label activePlayerLabel = null;
        Label active = new Label("Active player:\n");
        try{
            activePlayerLabel = new Label( guiController.getRmiStub().getActivePlayer());
            String color = guiController.getRmiStub().getMatch().searchPlayerByClientName(guiController.getRmiStub().getActivePlayer()).getCharacter();
            String color2 = null;
            switch(color){
                case "distructor" : {
                    color = distructor;
                    break;
                }
                case "banshee" : {
                    color = banshee;
                    break;
                }
                case "dozer" : {
                    color = dozer;
                    break;
                }
                case "violet" : {
                    color = violet;
                    break;
                }
                case "sprog" : {
                    color = sprog;
                    break;
                }
            }

            switch(myRemoteView.getCharacter()){
                case "distructor" : {
                    color2 = distructor;
                    break;
                }
                case "banshee" : {
                    color2 = banshee;
                    break;
                }
                case "dozer" : {
                    color2 = dozer;
                    break;
                }
                case "violet" : {
                    color2 = violet;
                    break;
                }
                case "sprog" : {
                    color2 = sprog;
                    break;
                }
            }

            activePlayerLabel.setStyle(color);
            active.setStyle(LABEL_STYLE);
            userName.setStyle(color2);
        }catch (RemoteException e){
            logger.log(Level.INFO,"Setuserinfo error",e);
        }

        Image userImage = createImage(ICONS_PATH + myRemoteView.getCharacter() +"_icon.png");
        ImageView userView = new ImageView(userImage);
        userInfoBox.getChildren().clear();

        userView.setPreserveRatio(true);
        userView.setFitWidth(50);
        moves.setStyle(LABEL_STYLE);
        points.setStyle(LABEL_STYLE);

        userInfoBox.getChildren().addAll(userView,userName,moves,points,active, activePlayerLabel);
        userInfoBox.setSpacing(5);
    }

    /**
     * It display the weapons inside a cabinet.
     */
    private void displayCabinet(){
        Stage cabinetWindow = new Stage();
        String color = null;
        HBox locker = new HBox();
        Image weaponImage;
        ImageView weaponView;

        if(myRemoteView.getPosition() == 4){
            color = "Red";
            for(int i = 0; i<myRemoteView.getCabinetRed().getSlot().length; i++){
                if(myRemoteView.getCabinetRed().getSlot()[i] != null){
                    weaponImage = createImage(WEAPONS_PATH + myRemoteView.getCabinetRed().getSlot()[i].getType() +".png");
                    weaponView = new ImageView(weaponImage);
                    weaponView.setFitWidth(200);
                    weaponView.setPreserveRatio(true);
                    weaponView.setId(Integer.toString(i));
                    locker.getChildren().add(weaponView);
                    locker.setSpacing(5);
                }

            }
        }else if(myRemoteView.getPosition() == 11){
            color = "Yellow";
            for(int i = 0; i<myRemoteView.getCabinetYellow().getSlot().length; i++){
                if(myRemoteView.getCabinetYellow().getSlot()[i] != null){
                    weaponImage = createImage(WEAPONS_PATH + myRemoteView.getCabinetYellow().getSlot()[i].getType() +".png");
                    weaponView = new ImageView(weaponImage);
                    weaponView.setFitWidth(200);
                    weaponView.setPreserveRatio(true);
                    weaponView.setId(Integer.toString(i));
                    locker.getChildren().add(weaponView);
                    locker.setSpacing(5);
                }

            }

        } else if(myRemoteView.getPosition() == 2){
            color = "Blue";
            for(int i = 0; i<myRemoteView.getCabinetBlue().getSlot().length; i++){
                if(myRemoteView.getCabinetBlue().getSlot()[i] != null){
                    weaponImage = createImage(WEAPONS_PATH + myRemoteView.getCabinetBlue().getSlot()[i].getType() +".png");
                    weaponView = new ImageView(weaponImage);
                    weaponView.setFitWidth(200);
                    weaponView.setPreserveRatio(true);
                    weaponView.setId(Integer.toString(i));
                    locker.getChildren().add(weaponView);
                    locker.setSpacing(5);
                }
            }
        }

        for(Node obj : locker.getChildren()){
            ImageView view = (ImageView) obj;
            obj.setOnMouseClicked(e->{
                indexToPickUp = Integer.parseInt(view.getId());
                if(myRemoteView.getWeapons().size()==3)
                    displayWeaponHand();
                cabinetWindow.close();
            });

        }
        Label info = new Label("Choose a weapon to pick up\nRemember: you don't have to pay for the first cube.");
        info.setStyle(LABEL_STYLE);
        BorderPane weaponPane = new BorderPane();
        cabinetWindow.initModality(Modality.APPLICATION_MODAL);
        cabinetWindow.setTitle( color + " cabinet");
        weaponPane.setCenter(locker);
        weaponPane.setStyle(BACKGROUND_STYLE);
        weaponPane.setTop(info);
        cabinetWindow.setResizable(false);
        Scene cabinetScene = new Scene(weaponPane);
        cabinetWindow.setOnCloseRequest( e -> {
            indexToDrop = -1;
            cabinetWindow.close();
        });
        cabinetWindow.setScene(cabinetScene);
        cabinetWindow.showAndWait();
    }

    /**
     * It display the weapons the player has while discarding a weapon.
     */
    private void displayWeaponHand(){
        Stage weaponHandWindow = new Stage();
        HBox hand = new HBox();
        Image weaponImage;
        ImageView weaponView;

        for(int i = 0; i<myRemoteView.getWeapons().size(); i++){
            weaponImage = createImage(WEAPONS_PATH + myRemoteView.getWeapons().get(i).getType() +".png");
            weaponView = new ImageView(weaponImage);
            weaponView.setFitWidth(200);
            weaponView.setPreserveRatio(true);
            weaponView.setId(Integer.toString(i));
            hand.getChildren().add(weaponView);
            hand.setSpacing(5);

        }

        for(Node obj : hand.getChildren()){
            ImageView view = (ImageView) obj;
            view.setOnMouseClicked(e-> {
                indexToDrop = Integer.parseInt(view.getId());
                weaponHandWindow.close();
            });
        }

        Label info = new Label("Choose a weapon to discard\n It goes back inside the locker, loaded.");
        info.setStyle(LABEL_STYLE);
        BorderPane weaponPane = new BorderPane();
        weaponHandWindow.initModality(Modality.APPLICATION_MODAL);
        weaponHandWindow.setTitle("Your hand");
        weaponPane.setCenter(hand);
        hand.setSpacing(10);
        weaponPane.setStyle(BACKGROUND_STYLE);
        weaponPane.setTop(info);
        Scene cabinetScene = new Scene(weaponPane);
        weaponHandWindow.setOnCloseRequest( e -> {
            indexToDrop = -1;
            weaponHandWindow.close();
        });
        weaponHandWindow.setScene(cabinetScene);
        weaponHandWindow.setResizable(false);
        weaponHandWindow.showAndWait();
    }

    /**
     * It creates the main GUI scene
     */
    private void setGameScene(){

        try{
            mapNumber = guiController.getRmiStub().getMatch().getMap().getMapID();

        } catch(Exception e){
            logger.log(Level.INFO,"Error retrieving mapNumber",e);
        }

        setAmmo(mapNumber);

        setKillShotTrack();

        setMapGrid();

        setCabinets();

        Button moveButton = setImageButton("src/main/resources/images/icons/move_icon.png");
        Button grabButton = setImageButton("src/main/resources/images/icons/grab_icon.png");
        Button shootButton = setImageButton("src/main/resources/images/icons/shoot_icon.png");
        Button passButton = setImageButton("src/main/resources/images/icons/pass_icon.png");
        Button powerUps = setImageButton("src/main/resources/images/icons/powerup_icon.png");
        Button playersButton = setImageButton("src/main/resources/images/icons/players_icon.png");
        Button weapons = setImageButton("src/main/resources/images/icons/weapon_icon.png");

        moveButton.setOnAction(e ->{
            if(myRemoteView.getFinalFrenzy() == 2)
                moveAction(4);
            else if(myRemoteView.getFinalFrenzy() == 1){
                textArea.setText("This action is disabled\n" + textArea.getText());
            }
            else moveAction(3);
            });
        grabButton.setOnAction(e -> {
            if (myRemoteView.getPhaseAction() ==  0 && myRemoteView.getFinalFrenzy()== 0)
                moveGrabAction(1);
            else if ((myRemoteView.getPhaseAction() >0 && myRemoteView.getFinalFrenzy()== 0) || myRemoteView.getFinalFrenzy() == 2)
                moveGrabAction(2);
            else if(myRemoteView.getFinalFrenzy() == 1)
                moveGrabAction(3);

        });
        shootButton.setOnAction(e -> {
            try {
                if (guiController.getRmiStub().getActivePlayer().equals(this.username)) {
                    if (guiController.getRmiStub().checkNumberAction(username) && guiController.getRmiStub().checkSizeWeapon(this.username)) {
                        ArrayList<Weapon> usableWeapons = guiController.getRmiStub().verifyWeapons(this.username);
                        if (!usableWeapons.isEmpty() && myRemoteView.getPhaseAction() != 2 ) {
                            displayUsableWeapons(usableWeapons);
                        } else
                            if(myRemoteView.getPhaseAction() == 2){
                                shootAction(1,usableWeapons);
                            } else
                                if(myRemoteView.getFinalFrenzy()==1){
                                    reloadShootAction(2);
                                } else if(myRemoteView.getFinalFrenzy() == 2){
                                    reloadShootAction(1);
                                }
                            else {
                                textArea.setText("You can't use any weapon\n" + textArea.getText());
                        }
                    } else {
                        textArea.setText("You have already used two actions or you dont't have any weapons. Pass your turn\n" + textArea.getText());
                    }
                } else {
                    textArea.setText("It's not your round\n" + textArea.getText());
                }
            }
            catch(RemoteException exc) {
                logger.log(Level.INFO,"shootButtonError",exc);
            }
        });
        passButton.setOnAction(e -> {
            try {
                if (guiController.getRmiStub().getActivePlayer().equals(this.username)) {
                    ArrayList<Weapon> reloadableWeapons = guiController.getRmiStub().getReloadableWeapons(this.username);
                    if(!reloadableWeapons.isEmpty())
                        reloadAlert(true);
                    else {
                        endTurnActionsRoutine();
                    }

                }
                else {
                    textArea.setText("It's not your round!!!" + textArea.getText());
                }
            }
            catch(Exception exc) {
                logger.log(Level.INFO,"passButton Error",exc);
            }
        });
        powerUps.setOnAction(e -> {
            HBox box = new HBox();
            ImageView powerUpView = null;
            for (PowerUp powerUp: myRemoteView.getPowerUps()) {
                powerUpView = new ImageView(createImage(POWERUPS_PATH + powerUp.getColor() +"_"+ powerUp.getType() + ".png"));
                powerUpView.setPreserveRatio(true);
                powerUpView.setFitHeight(200);
                box.getChildren().add(powerUpView);
            }
            displayPowerUpHand();
        });
        playersButton.setOnAction(e ->
                displayPlayers()
        );
        weapons.setOnAction(e ->
            displayWeapons()
        );

        setFirstPlayer();

        setUserInfos();

        setFirstPlayer();

        setCubes();

        leftMenu.getChildren().addAll(moveButton, grabButton, shootButton, passButton, weapons, powerUps, playersButton, cubeBox,userInfoBox);
        leftMenu.setSpacing(3);
        leftMenu.setPadding(new Insets(10,0,10,10));
        setTextArea();

        rightPane.getChildren().add(textArea);
        rightPane.setSpacing(10);

        stack.getChildren().addAll(setMap(), ammoSet, firstPlayer, killShotTrack, cabinets, grid, pawnsGrid);
        Group root = new Group(stack);
        root.setTranslateY(-375);
        root.setTranslateX(25);
        borderPane.setStyle("-fx-background-color: #202020");
        borderPane.setCenter(root);
        borderPane.setRight(rightPane);
        borderPane.setLeft(leftMenu);
        scene = new Scene(borderPane, 1310, 700);
        window.show();
        logger.log(Level.INFO,"Fine Creazione Scena di gioco");

        ///////
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), o-> {
            try{
                if(guiController.getRmiStub().getActivePlayer().equals(username) && guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).getFirstSpawn()){
                    startingDraw(FIRST_SPAWN_TEXT);
                    guiController.getRmiStub().setFirstSpawnPlayer(this.username);
                }
            } catch (Exception exception){
                logger.log(Level.INFO,"startingDraw Error",exception);
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        ////////

    }

    /**
     * Asks the player how he wants to use the selected power up.
     *
     * @param index the index of the power up selected.
     */
    private void powerUpAlert(int index){
        Stage powerUpAlert = new Stage();
        GridPane gridPane = new GridPane();
        BorderPane choicePane = new BorderPane();
        choicePane.setStyle(BACKGROUND_STYLE);

        Button powerUpButton = new Button("As a powerUp");
        Button tradeButton = new Button("For cubes");
        setResponsiveButton(powerUpButton);
        setResponsiveButton(tradeButton);


        powerUpButton.setOnAction(ind -> {
            powerUpAction(index);
            powerUpAlert.close();
        });
        tradeButton.setOnAction(trade -> {
            try {
                guiController.getRmiStub().tradeCube(index);
                setCubes();
                powerUpAlert.close();
            }catch (Exception e){
                logger.log(Level.INFO,"tradeButton Error",e);
            }
        });
        gridPane.setConstraints(powerUpButton,0,1);
        gridPane.setConstraints(tradeButton,2,1);
        gridPane.getChildren().addAll(powerUpButton,tradeButton);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setStyle(BACKGROUND_STYLE);

        Label info = new Label("How do you want to use it?");
        info.setStyle(LABEL_STYLE);

        powerUpAlert.initModality(Modality.APPLICATION_MODAL);

        choicePane.setCenter(gridPane);
        choicePane.setAlignment(gridPane,Pos.CENTER);
        choicePane.setStyle(BACKGROUND_STYLE);
        choicePane.setTop(info);
        choicePane.setAlignment(info,Pos.TOP_CENTER);
        Scene cabinetScene = new Scene(choicePane);
        powerUpAlert.setScene(cabinetScene);
        powerUpAlert.setWidth(300);
        powerUpAlert.setHeight(200);
        powerUpAlert.setResizable(false);
        powerUpAlert.showAndWait();
    }

    /**
     * It display the power up the player has.
     */
    private void displayPowerUpHand(){
        Stage powerUpWindow = new Stage();
        HBox powerUpBox = new HBox();
        Label info = new Label("Those are your power ups.\n You can use them as intended,\n or trade it for a cube of the same color.\n");
        info.setWrapText(true);
        BorderPane powerUpPane = new BorderPane();
        Image powerUpImage;
        ImageView powerUpView;

        info.setStyle(LABEL_STYLE);
        powerUpWindow.initModality(Modality.APPLICATION_MODAL);
        powerUpWindow.setTitle("Power Ups");

        ArrayList<PowerUp> powerUps = myRemoteView.getPowerUps();
        for(int i=0; i<powerUps.size();i++){
            powerUpImage = createImage(POWERUPS_PATH + powerUps.get(i).getColor()+"_" + powerUps.get(i).getType() + ".png");
            powerUpView = new ImageView(powerUpImage);
            powerUpBox.getChildren().add(powerUpView);
            powerUpView.setId(Integer.toString(i));
        }

        for(Node obj : powerUpBox.getChildren()){
            ImageView view = (ImageView) obj;
            view.setOnMouseClicked(e->{
                try{
                    if(guiController.getRmiStub().getActivePlayer().equals(this.username) && !myRemoteView.getCanMove()){
                        powerUpAlert(Integer.parseInt(view.getId()));
                        powerUpWindow.close();
                    }
                }catch (RemoteException g){
                    logger.log(Level.INFO,"PowerUpHand error",g);
                }



            });
        }

        powerUpBox.setSpacing(5);
        Button closeButton = setHomeButton(powerUpWindow);
        powerUpPane.setTop(info);
        powerUpPane.setCenter(powerUpBox);
        powerUpPane.setBottom(closeButton);
        powerUpPane.setStyle(BACKGROUND_STYLE);
        powerUpPane.setAlignment(closeButton,Pos.CENTER);
        powerUpBox.setAlignment(Pos.CENTER);
        info.setAlignment(Pos.CENTER);
        Scene scene = new Scene(powerUpPane);
        powerUpWindow.setScene(scene);
        powerUpWindow.setResizable(false);
        powerUpWindow.showAndWait();
    }

    /**
     * It display the weapons the player has.
     */
    private void displayWeapons(){
        Stage displayWindow = new Stage();
        Label info = new Label("These are your weapons.\nGrey ones are unloaded and you can't use them.");
        HBox layout = new HBox();
        BorderPane weaponsPane = new BorderPane();
        Image greyImage;
        ImageView weaponView = new ImageView();
        Image weaponImage;
        for (Weapon obj : myRemoteView.getWeapons()) {
            weaponImage = createImage(WEAPONS_PATH + obj.getType() + ".png");
            if(!obj.getLoad() && weaponImage!= null){
                greyImage = toGrayScale(weaponImage);
                weaponView = new ImageView(greyImage);
            } else if(weaponImage!= null){
                weaponView = new ImageView(weaponImage);
            }
            weaponView.setPreserveRatio(true);
            weaponView.setFitHeight(300);
            layout.getChildren().add(weaponView);
            layout.setSpacing(5);
        }


        info.setStyle(LABEL_STYLE);
        info.setWrapText(true);
        displayWindow.initModality(Modality.APPLICATION_MODAL);
        displayWindow.setTitle("Weapons");

        Button closeButton = setHomeButton(displayWindow);
        layout.setStyle(BACKGROUND_STYLE);
        layout.setAlignment(Pos.CENTER);
        info.setAlignment(Pos.CENTER);
        weaponsPane.setTop(info);
        weaponsPane.setCenter(layout);
        weaponsPane.setBottom(closeButton);
        weaponsPane.setStyle(BACKGROUND_STYLE);
        weaponsPane.setAlignment(info,Pos.CENTER);
        weaponsPane.setAlignment(layout,Pos.CENTER);
        weaponsPane.setAlignment(closeButton,Pos.CENTER);
        Scene scene = new Scene(weaponsPane);
        displayWindow.setScene(scene);
        displayWindow.setResizable(false);
        displayWindow.showAndWait();
    }

    /**
     * Display the Power ups the player has.
     * By clicking on one of them makes the player spawn
     * on the selected color.
     * @param message window message.
     */
    public void startingDraw(String message){
        Stage startingDrawWindow = new Stage();

        Label info = new Label(message);
        info.setStyle(LABEL_STYLE);
        info.setPadding(new Insets (10,10,10,10));
        BorderPane spawnPane = new BorderPane();
        startingDrawWindow.initModality(Modality.APPLICATION_MODAL);
        startingDrawWindow.setAlwaysOnTop(true);
        startingDrawWindow.setTitle("Spawn");

        borderPane.setTop(info);
        HBox powerUpBox = new HBox();
        powerUpBox.setSpacing(10);
        powerUpBox.setPadding(new Insets(10,10,10,10));
        ArrayList<PowerUp> powerUps = myRemoteView.getPowerUps();
        for(int i=0; i<powerUps.size();i++){
            Image powerUpImage = createImage(POWERUPS_PATH + powerUps.get(i).getColor()+"_" + powerUps.get(i).getType() + ".png");
            ImageView powerUpView = new ImageView(powerUpImage);
            powerUpBox.getChildren().add(powerUpView);
            powerUpView.setId(Integer.toString(i));
        }
        for(Node obj : powerUpBox.getChildren()) {
            ImageView view = (ImageView) obj;
            view.setOnMouseClicked(e -> {
                //discard the selected powerup, respawn based on the color
                try{
                    guiController.getRmiStub().discardAndSpawn(username,Integer.parseInt(view.getId()));
                    startingDrawWindow.close();
                }catch (Exception exc){
                    logger.log(Level.INFO,"startingDraw() Error",exc);
                }
            });
        }

        startingDrawWindow.setOnCloseRequest(e->{
            startingDraw(FIRST_SPAWN_TEXT);
            startingDrawWindow.close();
        });
        spawnPane.setTop(info);
        spawnPane.setCenter(powerUpBox);
        spawnPane.setStyle(BACKGROUND_STYLE);
        Scene startingDrawScene = new Scene(spawnPane);
        startingDrawWindow.setScene(startingDrawScene);
        startingDrawWindow.setResizable(false);
        startingDrawWindow.show();
        }

    /**
     * Method who makes a Rectangle object invisible.
     * It sets the color value to (0,0,0,0).
     * @param rectangle Rectangle object involved.
     */
    private void setInvisible(Rectangle rectangle){
            rectangle.setFill(Color.color(0,0,0,0));
        }

    /**
     * Asks the player if he wants to reload the weapons.
     * @param endTurn indicates if this method is performed
     *                at the end of the turn or during the
     *                ReloadShoot Action.
     */
    private void reloadAlert(boolean endTurn){
        Stage reloadAlert = new Stage();
        GridPane gridPane = new GridPane();
        BorderPane reloadPane = new BorderPane();
        reloadPane.setStyle(BACKGROUND_STYLE);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        setResponsiveButton(yesButton);
        setResponsiveButton(noButton);

        yesButton.setOnAction(trade -> {
            try {
                ArrayList<Weapon> newReloadableWeapons = guiController.getRmiStub().getReloadableWeapons(this.username);
                if(!newReloadableWeapons.isEmpty()){
                    displayReloadableWeapons(endTurn);
                    reloadAlert.close();
                }
            }catch (Exception e){
                logger.log(Level.INFO,"reloadAlert() Error",e);
            }
        });

        noButton.setOnAction( exit -> {
            try{
                if(endTurn){
                    endTurnActionsRoutine();
                    reloadAlert.close();
                }
                else{
                    shootAction(0,new ArrayList<>());
                    reloadAlert.close();
                }
            }catch (Exception e){
                logger.log(Level.INFO,"noButton Error",e);
            }
        });

        gridPane.setConstraints(yesButton,0,1);
        gridPane.setConstraints(noButton,2,1);
        gridPane.getChildren().addAll(yesButton,noButton);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(30);
        gridPane.setStyle(BACKGROUND_STYLE);

        Label info = new Label("Do you want to reload your weapons?");
        info.setStyle(LABEL_STYLE);
        reloadAlert.initModality(Modality.APPLICATION_MODAL);

        reloadPane.setCenter(gridPane);
        reloadPane.setAlignment(gridPane,Pos.CENTER);
        reloadPane.setStyle(BACKGROUND_STYLE);
        reloadPane.setTop(info);
        reloadPane.setAlignment(info,Pos.TOP_CENTER);
        Scene reloadScene = new Scene(reloadPane);
        reloadAlert.setScene(reloadScene);
        reloadAlert.setAlwaysOnTop(true);
        reloadAlert.setTitle("Reloading weapons");
        reloadAlert.setWidth(300);
        reloadAlert.setHeight(200);
        reloadAlert.setResizable(false);
        reloadAlert.show();
    }

    /**
     * It display the weapons the player can reload.
     * @param endTurn indicates if this method is performed
     *                at the end of the turn or during the
     *                ReloadShoot Action.
     */
    private void displayReloadableWeapons(boolean endTurn){
        Stage weaponToReloadWindow = new Stage();
        HBox weaponsBox = new HBox();
        BorderPane reloadableWeaponPane = new BorderPane();
        reloadableWeaponPane.setStyle(BACKGROUND_STYLE);
        ImageView weaponImageView;
        Image image;
        try{
            ArrayList<Weapon> newReloadableWeapons = guiController.getRmiStub().getReloadableWeapons(username);
            for(int i = 0; i < myRemoteView.getWeapons().size(); i++) {
                for (Weapon weaponToReload : newReloadableWeapons) {
                    if (weaponToReload.getType().equals(myRemoteView.getWeapons().get(i).getType())) {
                        image = createImage(WEAPONS_PATH + weaponToReload.getType() + ".png");
                        weaponImageView = new ImageView(image);
                        weaponImageView.setId(Integer.toString(i));
                        weaponImageView.setPreserveRatio(true);
                        weaponImageView.setFitHeight(300);
                        weaponsBox.getChildren().add(weaponImageView);
                    }
                }
            }
        } catch (RemoteException o){
                logger.log(Level.INFO,"displayReloadableWeapons error",o);
        }
        for(Node weaponView : weaponsBox.getChildren()){
            ImageView imageView = (ImageView) weaponView;
            if(Integer.parseInt(imageView.getId())!= -1){
                imageView.setOnMouseClicked(reload -> {
                    try{
                        guiController.getRmiStub().reloadWeapon(this.username,Integer.parseInt(weaponView.getId()));
                        setCubes();
                        ArrayList<Weapon> newReloadableWeapons = guiController.getRmiStub().getReloadableWeapons(this.username);
                        if(!newReloadableWeapons.isEmpty()){
                            reloadAlert(endTurn);
                            weaponToReloadWindow.close();
                        } else {
                            if(endTurn){
                                endTurnActionsRoutine();
                                weaponToReloadWindow.close();

                            } else {
                                shootAction(0,new ArrayList<>());
                            }
                        }
                    }catch (Exception e) {
                        logger.log(Level.INFO,"displayReloadableWeapons Error",e);
                    }
                });
            }
        }

        weaponToReloadWindow.initModality(Modality.APPLICATION_MODAL);

        weaponToReloadWindow.setOnCloseRequest(close -> {
            try{
                endTurnActionsRoutine();
            }catch(Exception e){
                logger.log(Level.INFO,"displayReloadableWeapons Close Error",e);
            }
        });
        Label info = new Label("Which weapon do you want to reload?");
        info.setStyle(LABEL_STYLE);

        weaponToReloadWindow.initModality(Modality.APPLICATION_MODAL);

        reloadableWeaponPane.setCenter(weaponsBox);
        reloadableWeaponPane.setAlignment(weaponsBox,Pos.CENTER);
        weaponsBox.setAlignment(Pos.CENTER);
        weaponsBox.setSpacing(10);
        weaponsBox.setPadding(new Insets(10,10,10,10));
        reloadableWeaponPane.setStyle(BACKGROUND_STYLE);
        reloadableWeaponPane.setTop(info);
        reloadableWeaponPane.setAlignment(info,Pos.TOP_CENTER);
        weaponToReloadWindow.setResizable(false);
        Scene reloadableScene = new Scene(reloadableWeaponPane);
        weaponToReloadWindow.setScene(reloadableScene);
        weaponToReloadWindow.show();
    }

    /**
     * It displays the weapons the player can actually use.
     *
     * @param usableWeapons the collection of weapons the player can use.
     */
    private void displayUsableWeapons(ArrayList<Weapon> usableWeapons){
        Stage usableWeaponsWindow = new Stage();
        HBox weaponsBox = new HBox();
        BorderPane usableWeaponsPane = new BorderPane();
        usableWeaponsPane.setStyle(BACKGROUND_STYLE);

        for(int i = 0; i < usableWeapons.size(); i++){
            textArea.setText("\n" + usableWeapons.get(i).getType() + textArea.getText());
            ImageView weaponView = new ImageView(createImage(WEAPONS_PATH + usableWeapons.get(i).getType()+".png"));
            weaponView.setId(Integer.toString(i));
            weaponView.setFitHeight(300);
            weaponView.setPreserveRatio(true);
            weaponsBox.getChildren().add(weaponView);
        }

        for(Node weaponView : weaponsBox.getChildren()){
            weaponView.setOnMouseClicked(reload -> {
                try{
                    displayEffects(usableWeapons.get(Integer.parseInt(weaponView.getId())));
                    usableWeaponsWindow.close();

                }catch (Exception e) {
                    logger.log(Level.INFO,"displayUsableWeapons Error",e);
                }
            });
        }
        weaponsBox.setAlignment(Pos.CENTER);
        weaponsBox.setSpacing(30);
        weaponsBox.setStyle(BACKGROUND_STYLE);

        Label info = new Label("Which weapon do you want to use?");
        info.setStyle(LABEL_STYLE);

        usableWeaponsWindow.initModality(Modality.APPLICATION_MODAL);

        usableWeaponsPane.setCenter(weaponsBox);
        usableWeaponsPane.setAlignment(weaponsBox,Pos.CENTER);
        usableWeaponsPane.setStyle(BACKGROUND_STYLE);
        usableWeaponsPane.setTop(info);
        usableWeaponsPane.setAlignment(info,Pos.TOP_CENTER);
        Scene usableScene = new Scene(usableWeaponsPane);
        usableWeaponsWindow.setScene(usableScene);
        usableWeaponsWindow.setMinWidth(400);
        usableWeaponsWindow.setMinHeight(300);
        usableWeaponsWindow.setResizable(false);
        usableWeaponsWindow.showAndWait();
    }

    /**
     * It displays all the effects the player can use while shooting.
     * @param chosenWeapon is the selected weapon.
     */
    private void displayEffects(Weapon chosenWeapon){
        Stage availableEffectsWindow = new Stage();
        ComboBox comboBox = new ComboBox();
        BorderPane availableEffectPane = new BorderPane();
        availableEffectPane.setStyle(BACKGROUND_STYLE);
        String effect;
        for(int i = 0; i < chosenWeapon.getWeaponShots().size(); i++){

            effect = chosenWeapon.getWeaponShots().get(i).getNameEffect();
            comboBox.getItems().add(effect);

        }

        Label info = new Label("Which effect do you want to use?");
        info.setStyle(LABEL_STYLE);

        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(BUTTON_STYLE);
        comboBox.setPromptText("Select effect");
        comboBox.setPadding(new Insets(10,10,10,10));
        confirmButton.setOnAction(e-> {
            for(WeaponShot weaponShot : chosenWeapon.getWeaponShots()){
                if(weaponShot.getNameEffect().equals(comboBox.getValue())) {
                    if(weaponShot.getChosenEffect().getTypeVisibility().equals("Cardinal2"))
                        displayOrientationChoice(weaponShot);
                    else if(weaponShot.getChosenEffect().getTypeVisibility().equals("Vortex")){
                        displayVortexSquares(weaponShot);
                    }
                    else
                        displayTargets(weaponShot);

                }
            }

            availableEffectsWindow.close();
        });

        availableEffectsWindow.initModality(Modality.APPLICATION_MODAL);
        availableEffectPane.setCenter(comboBox);
        availableEffectPane.setAlignment(comboBox,Pos.CENTER);
        availableEffectPane.setStyle(BACKGROUND_STYLE);
        availableEffectPane.setTop(info);
        availableEffectPane.setBottom(confirmButton);
        availableEffectPane.setAlignment(confirmButton,Pos.CENTER);
        availableEffectPane.setAlignment(info,Pos.TOP_CENTER);

        Scene usableScene = new Scene(availableEffectPane);
        availableEffectsWindow.setScene(usableScene);
        availableEffectsWindow.setWidth(300);
        availableEffectsWindow.setHeight(200);
        availableEffectsWindow.setResizable(false);
        availableEffectsWindow.showAndWait();

    }

    /**
     * It displays targets the player can shoot at.
     * @param weaponShot contains the information needed to perform this action.
     */
    private void displayTargets(WeaponShot weaponShot){
        Stage weaponTargetWindow = new Stage();
        ComboBox comboBox = new ComboBox();
        BorderPane weaponTargetPane = new BorderPane();
        weaponTargetPane.setStyle(BACKGROUND_STYLE);
        Label info = new Label("Select target");
        info.setStyle(LABEL_STYLE);
        Button confirmButton = new Button("Confirm");
        setResponsiveButton(confirmButton);

        comboBox.setPromptText("Select target");
        comboBox.setPadding(new Insets(10,10,10,10));

        try{
            //per ogni giocatore bersagliabile aggiungo il target se diverso da quello che sta attaccando
            for(Player target : weaponShot.getTargetablePlayer()){
                if(!target.getClientName().equals(this.username))
                    comboBox.getItems().add(target.getClientName());
            }

        }catch (Exception exc){
            logger.log(Level.INFO,"displayTargets error",exc);
        }

        confirmButton.setOnAction(e-> {
            if(comboBox.getValue()!=null){
                try{
                    weaponShot.getTargetPlayer().add(guiController.getRmiStub().getMatch().searchPlayerByClientName((String) comboBox.getValue()));
                    Player justHitTarget = null;
                    for(Player targetablePlayer : weaponShot.getTargetablePlayer()){
                        if(targetablePlayer.getClientName().equals(guiController.getRmiStub().getMatch().searchPlayerByClientName((String) comboBox.getValue()).getClientName()))
                            justHitTarget = targetablePlayer;
                    }
                    if(justHitTarget!= null){
                        weaponShot.getAlreadyTarget().add(justHitTarget);
                        weaponShot.getTargetablePlayer().remove(justHitTarget);
                    }
                    weaponShot.getChosenEffect().setMaxTarget(weaponShot.getChosenEffect().getMaxTarget()-1);

                    if(weaponShot.getChosenEffect().getMaxTarget()>0 && (!weaponShot.getTargetablePlayer().isEmpty() || weaponShot.getChosenEffect().getTypeVisibility().equals("Cascade"))){
                        if(weaponShot.getChosenEffect().getTypeVisibility().equals("Cascade")){
                            WeaponShot weaponShot1;
                            weaponShot1 = guiController.getRmiStub().getThorTargets(weaponShot,weaponShot.getTargetPlayer().size()-1);

                            if(!weaponShot1.getTargetablePlayer().isEmpty())
                                displayTargets(weaponShot1);
                            else {
                                guiController.getRmiStub().applyEffectWeapon(weaponShot1);
                                guiController.getRmiStub().useAction(this.username);
                                weaponTargetWindow.close();
                            }
                        } else{
                            displayTargets(weaponShot);
                        }

                        weaponTargetWindow.close();
                    } else if(weaponShot.getChosenEffect().getMaxMovementTarget()>0) {
                        guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).setCanMove(true);
                        setPushSquares(weaponShot);
                        weaponTargetWindow.close();
                    } else {
                        guiController.getRmiStub().applyEffectWeapon(weaponShot);
                        guiController.getRmiStub().useAction(this.username);
                        if(weaponShot.getChosenEffect().getMaxMovementPlayer()>0){
                            guiController.getRmiStub().giftAction(this.username);
                            moveAction(weaponShot.getChosenEffect().getMaxMovementPlayer());
                        }
                        weaponTargetWindow.close();
                    }

                }catch (Exception err){
                    logger.log(Level.INFO,"TargetingScope method error",err);
                }

            }
        });
        weaponTargetWindow.initModality(Modality.APPLICATION_MODAL);
        weaponTargetPane.setCenter(comboBox);
        weaponTargetPane.setAlignment(comboBox,Pos.CENTER);
        weaponTargetPane.setStyle(BACKGROUND_STYLE);
        weaponTargetPane.setTop(info);
        weaponTargetPane.setBottom(confirmButton);
        weaponTargetPane.setAlignment(confirmButton,Pos.CENTER);
        weaponTargetPane.setAlignment(info,Pos.TOP_CENTER);

        Scene usableScene = new Scene(weaponTargetPane);
        weaponTargetWindow.setScene(usableScene);
        weaponTargetWindow.setWidth(300);
        weaponTargetWindow.setHeight(200);
        weaponTargetWindow.setResizable(false);
        weaponTargetWindow.show();
    }

    /**
     * Action performed while using a powerUp.
     * @param index the index of the Power up used.
     */
    private void powerUpAction(int index){
        try {
            switch(myRemoteView.getPowerUps().get(index).getType()){
                case "teleporter" : {
                    teleporterAction(index);
                    break;
                }
                case "tagback_grenade" : {/*
                        myRemoteView.getPowerUpShot().setDamagingPlayer(damagingPlayer);
                        myRemoteView.getPowerUpShot().setTargetingPlayer(targetingPlayer);
                        guiController.getRmiStub().usePowerUp(this,username, index, myRemoteView.getPowerUpShot);
                        break;
                        */
                }
                case "targeting_scope" : {
                        try{
                            if(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).getPlayerBoard().getAmmoCubes().checkAtLeastOne() &&
                                    !guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).getHitThisTurnPlayers().isEmpty()){
                                displayTargetingScopeTargets(index);
                            }
                        }catch (RemoteException e){
                            logger.log(Level.INFO,"TargetingScope PowerUpAction error");
                        }
                        break;
                }
                case "newton" : {
                        displayNewtonTargets(index);
                    break;

                }
            }

        }
        catch (Exception e) {
            logger.log(Level.INFO,"Error powerUpButton");
        }
    }

    /**
     * It display the squares the player can move on while using the Teleporter Power up.
     * @param index the index of the Power up used.
     */
    private void teleporterAction(int index){
        try{
            PowerUpShot powerUpShot= new PowerUpShot();
            Square[] walkableSquares = guiController.getRmiStub().getAllSquares();
            for (int i = 0; i < 12; i++) {
                Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
                if(!walkableSquares[i].getColor().equals("")){
                    rectangle.setFill(Color.color(0, 1, 0.6, 0.4));
                    rectangle.setOnMouseClicked(o -> {
                        try {
                            powerUpShot.setDamagingPlayer(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username));
                            powerUpShot.setTargetingPlayer(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username));
                            powerUpShot.setNewPosition(Integer.parseInt(rectangle.getId()));
                            guiController.getRmiStub().usePowerUp(this.username,index,powerUpShot);
                        } catch (Exception exc) {
                            logger.log(Level.INFO,"setMovementSquare() Error",exc);
                        }
                        restoreSquares();
                    });
                    rectangle.setOnMouseEntered( enter ->
                            rectangle.setFill(Color.color(0,1,0.6,0.6)));
                    rectangle.setOnMouseExited( exit ->
                            rectangle.setFill(Color.color(0,1,0.6,0.4)));
                }


            }
        }catch (Exception err){
            logger.log(Level.INFO,"TargetingScope method error",err);
        }

    }
    /**
     * Display the targets the player can target using Targeting Scope Power up.
     * @param index the index of the Power up used.
     */
    private void displayTargetingScopeTargets(int index){
        Stage targetingScopeWindow = new Stage();
        ComboBox comboBox = new ComboBox();
        ComboBox cubesBox = new ComboBox();
        BorderPane targetingScopePane = new BorderPane();
        targetingScopePane.setStyle(BACKGROUND_STYLE);
        ArrayList<Player> allTargets = new ArrayList<>();
        try{
            allTargets.addAll(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).getHitThisTurnPlayers());
            for(Player target : allTargets){
                if(!target.getClientName().equals(this.username))
                    comboBox.getItems().add(target.getClientName());
            }
            if(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).getPlayerBoard().getAmmoCubes().getReds() > 0)
                cubesBox.getItems().add("Red cube");

            if(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).getPlayerBoard().getAmmoCubes().getYellows()> 0)
                cubesBox.getItems().add("Yellow cube");

            if(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).getPlayerBoard().getAmmoCubes().getBlues() > 0)
                cubesBox.getItems().add("Blue cube");

        }catch (Exception exc){
            logger.log(Level.INFO,"Targets targetingScope error",exc);
        }

        Label info = new Label("Who is the target?");
        info.setStyle(LABEL_STYLE);
        Button confirmButton = new Button("Confirm");
        setResponsiveButton(confirmButton);
        cubesBox.setPromptText("Select cube");
        comboBox.setPromptText("Select target");
        comboBox.setPadding(new Insets(10,10,10,10));
        cubesBox.setPadding(new Insets(10,10,10,10));

        confirmButton.setOnAction(e-> {
            if(comboBox.getValue()!=null && cubesBox.getValue()!= null){
                PowerUpShot powerUpShot = guiController.getMyRemoteView().getPowerUpShot();
                try{
                    powerUpShot.setDamagingPlayer(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username));
                    Player targetingPlayer = guiController.getRmiStub().getMatch().searchPlayerByClientName((String) comboBox.getValue());
                    powerUpShot.setTargetingPlayer(targetingPlayer);
                    guiController.getRmiStub().usePowerUp(this.username,index,powerUpShot);
                    if(cubesBox.getValue().equals("Red cube"))
                        guiController.getRmiStub().payCubes(this.username,1,0,0);
                    if(cubesBox.getValue().equals("Yellow cube"))
                        guiController.getRmiStub().payCubes(this.username,0,1,0);
                    if(cubesBox.getValue().equals("Blue cube"))
                        guiController.getRmiStub().payCubes(this.username,0,0,1);
                    targetingScopeWindow.close();
                }catch (Exception err){
                    logger.log(Level.INFO,"TargetingScope method error",err);
                }

            }
        });
        targetingScopeWindow.initModality(Modality.APPLICATION_MODAL);
        targetingScopePane.setCenter(comboBox);
        targetingScopePane.setRight(cubesBox);
        targetingScopePane.setAlignment(cubesBox,Pos.CENTER_LEFT);
        targetingScopePane.setAlignment(comboBox,Pos.CENTER);
        targetingScopePane.setStyle(BACKGROUND_STYLE);
        targetingScopePane.setTop(info);
        targetingScopePane.setBottom(confirmButton);
        targetingScopePane.setAlignment(confirmButton,Pos.CENTER);
        targetingScopePane.setAlignment(info,Pos.TOP_CENTER);

        Scene usableScene = new Scene(targetingScopePane);
        targetingScopeWindow.setScene(usableScene);
        targetingScopeWindow.setWidth(300);
        targetingScopeWindow.setHeight(200);
        targetingScopeWindow.setResizable(false);
        targetingScopeWindow.showAndWait();
    }

    /**
     * Display the targets the player can target using Newton Power up.
     * @param index the index of the Power up used.
     */
    private void displayNewtonTargets(int index){

        Stage newtonWindow = new Stage();
        ComboBox comboBox = new ComboBox();
        BorderPane newtonPane = new BorderPane();
        newtonPane.setStyle(BACKGROUND_STYLE);
        ArrayList<Player> allTargets = new ArrayList<>();
        PowerUpShot powerUpShot = new PowerUpShot();

        try{
            allTargets.addAll(guiController.getRmiStub().getMatch().getAllPlayers());
            for(Player target : allTargets){
                if(!target.getClientName().equals(this.username))
                    comboBox.getItems().add(target.getClientName());
            }

        }catch (Exception exc){
            logger.log(Level.INFO,"Newton Targets error",exc);
        }
        Label info = new Label("Who is the target?");
        info.setStyle(LABEL_STYLE);

        Button confirmButton = new Button("Confirm");
        setResponsiveButton(confirmButton);

        comboBox.setPromptText("Select target");
        comboBox.setPadding(new Insets(10,10,10,10));

        confirmButton.setOnAction(e-> {
            newtonWindow.close();
            if(comboBox.getValue()!=null){
                try{
                    ArrayList<Square> squares = new ArrayList<>();
                    squares.addAll(guiController.getRmiStub().getCardinalDirectionsSquares(2,guiController.getRmiStub().getMatch().searchPlayerByClientName((String) comboBox.getValue()).getPosition()));

                    for (int i = 0; i < 12; i++) {
                        Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
                        for (Square square : squares) {
                            if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                                rectangle.setFill(Color.color(0.7, 1, 1, 0.4));
                                rectangle.setOnMouseClicked(o -> {
                                    try {
                                        powerUpShot.setDamagingPlayer(guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username));
                                        Player targetingPlayer = guiController.getRmiStub().getMatch().searchPlayerByClientName((String) comboBox.getValue());
                                        powerUpShot.setTargetingPlayer(targetingPlayer);
                                        powerUpShot.setNewPosition(Integer.parseInt(rectangle.getId()));
                                        guiController.getRmiStub().usePowerUp(this.username,index,powerUpShot);
                                    } catch (Exception exc) {
                                        logger.log(Level.INFO,"setMovementSquare() Error",exc);
                                    }
                                    restoreSquares();
                                });
                                rectangle.setOnMouseEntered( enter ->
                                        rectangle.setFill(Color.color(0.7,1,1,0.6)));
                                rectangle.setOnMouseExited( exit ->
                                        rectangle.setFill(Color.color(0.7,1,1,0.4)));
                            }
                        }
                    }
                }catch (Exception err){
                    logger.log(Level.INFO,"Newton method error",err);
                }
            }

        });
        newtonPane.setCenter(comboBox);
        newtonPane.setAlignment(comboBox,Pos.CENTER);
        newtonPane.setStyle(BACKGROUND_STYLE);
        newtonPane.setTop(info);
        newtonPane.setBottom(confirmButton);
        newtonPane.setAlignment(confirmButton,Pos.CENTER);
        newtonPane.setAlignment(info,Pos.TOP_CENTER);

        Scene newtonScene = new Scene(newtonPane);
        newtonWindow.setScene(newtonScene);
        newtonWindow.setWidth(300);
        newtonWindow.setHeight(200);
        newtonWindow.setResizable(false);
        newtonWindow.showAndWait();

    }

    public TextArea getTextArea() {
        return this.textArea;
    }

    /**
     * Update all the GUI elements shown in the main Scene:
     * -Character figures
     * -Killshot Track
     * -Available ammos on the terrain
     * -User informations
     * -User owned cubes
     * -Red, blue and yellow weapons cabinets.
     */
    public void updateAllGUI() {
        this.setFigures();
        this.setKillShotTrack();
        this.setAmmo(mapNumber);
        this.setUserInfos();
        this.setCubes();
        this.setWeaponView(redBox, myRemoteView.getCabinetRed().getSlot());
        this.setWeaponView(yellowBox, myRemoteView.getCabinetYellow().getSlot());
        this.setWeaponView(blueBox, myRemoteView.getCabinetBlue().getSlot());
    }

    public Scene getScene() {
        return this.scene;
    }

    /**
     * Method used when shooting with the Railgun. Asks the players in which direction
     * he wants to shoot.
     * @param weaponsShot contains the infos needed.
     */
    private void displayOrientationChoice(WeaponShot weaponsShot){
        Stage orientationWindow = new Stage();
        ComboBox comboBox = new ComboBox();
        BorderPane orientationPane = new BorderPane();
        orientationPane.setStyle(BACKGROUND_STYLE);

        if(!weaponsShot.getNorthTargets().isEmpty())
            comboBox.getItems().add("North");
        if(!weaponsShot.getEastTargets().isEmpty())
            comboBox.getItems().add("East");
        if(!weaponsShot.getSouthTargets().isEmpty())
            comboBox.getItems().add("South");
        if(!weaponsShot.getWestTargets().isEmpty())
            comboBox.getItems().add("West");

        Label info = new Label("Choose a direction");
        info.setStyle(LABEL_STYLE);

        Button confirmButton = new Button("Confirm");
        confirmButton.setStyle(BUTTON_STYLE);
        comboBox.setPromptText("Select effect");
        comboBox.setPadding(new Insets(10,10,10,10));

        confirmButton.setOnAction(e-> {
            switch((String) comboBox.getValue()){
                case "North" : {
                    weaponsShot.setTargetablePlayer(weaponsShot.getNorthTargets());
                    displayTargets(weaponsShot);
                    orientationWindow.close();
                    break;
                }

                case "East" : {
                    weaponsShot.setTargetablePlayer(weaponsShot.getEastTargets());
                    displayTargets(weaponsShot);
                    orientationWindow.close();
                    break;
                }

                case "South" : {
                    weaponsShot.setTargetablePlayer(weaponsShot.getSouthTargets());
                    displayTargets(weaponsShot);
                    orientationWindow.close();
                    break;
                }

                case "West" : {
                    weaponsShot.setTargetablePlayer(weaponsShot.getWestTargets());
                    displayTargets(weaponsShot);
                    orientationWindow.close();
                    break;
                }
            }
        });

        orientationWindow.initModality(Modality.APPLICATION_MODAL);
        orientationPane.setCenter(comboBox);
        orientationPane.setAlignment(comboBox,Pos.CENTER);
        orientationPane.setStyle(BACKGROUND_STYLE);
        orientationPane.setTop(info);
        orientationPane.setBottom(confirmButton);
        orientationPane.setAlignment(confirmButton,Pos.CENTER);
        orientationPane.setAlignment(info,Pos.TOP_CENTER);

        Scene usableScene = new Scene(orientationPane);
        orientationWindow.setScene(usableScene);
        orientationWindow.setWidth(300);
        orientationWindow.setHeight(200);
        orientationWindow.show();

    }

    /**
     * Action used to move around the map.
     * @param steps number of steps eligible for this action.
     */
    private void moveAction(int steps){
        try {
            if(guiController.getRmiStub().getActivePlayer().equals(this.username)){
                if(guiController.getRmiStub().checkNumberAction(this.username)){
                    if (!myRemoteView.getCanMove()) {
                        guiController.getRmiStub().toggleAction(this.username);
                        guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reachableSquares(guiController.getMyRemoteView().getPosition(),steps));
                        setMovementSquares();
                    } else {
                        textArea.setText("Resolve your previous action!\n" + textArea.getText());
                    }
                } else {
                    textArea.setText("You have used all of your actions. Pass the turn.\n" + textArea.getText());
                }
            }
            else {
                textArea.setText("It's not your round!\n" + textArea.getText());
            }
        } catch (RemoteException e1) {
            logger.log(Level.INFO,"moveAction Error",e1);
        }
    }

    /**
     * Action that includes movement and grabbing a weapon or an ammo tile.
     * This action is  affected by adrenaline actions.
     * @param steps number of steps eligible for this action
     */
    private void moveGrabAction(int steps){
        try {
            if(guiController.getRmiStub().getActivePlayer().equals(this.username)){
                if(guiController.getRmiStub().checkNumberAction(this.username)){
                    if (!myRemoteView.getCanMove()) {
                        guiController.getRmiStub().toggleAction(this.username);
                        guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reachableSquares(guiController.getRmiStub().getMatch().searchPlayerByClientName(username).getPosition(),steps));
                        setMoveGrabSquares();
                    } else {
                        textArea.setText("Resolve your previous action!\n" + textArea.getText());
                    }
                } else {
                    textArea.setText("You have used all of your actions. Pass the turn.\n" + textArea.getText());
                }
            }
            else {
                textArea.setText("It's not your round!\n" + textArea.getText());
            }
        } catch (Exception e1) {
            logger.log(Level.INFO,"moveGrabAction() Error",e1);
        }
    }

    /**
     * Action that includes shooting or movement and shooting depending on
     * how many damage the player has.
     * @param steps number of steps eligible for this action.
     * @param usableWeapons a collection of weapons the player can use.
     */
    private void shootAction(int steps,ArrayList<Weapon> usableWeapons){
        try {
            if(guiController.getRmiStub().getActivePlayer().equals(this.username)){
                if(guiController.getRmiStub().checkNumberAction(this.username)){
                    if (!myRemoteView.getCanMove()) {
                        guiController.getRmiStub().toggleAction(this.username);
                        guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reachableSquares(guiController.getMyRemoteView().getPosition(),steps));
                        setShootSquares(usableWeapons);
                    } else {
                        textArea.setText("Resolve your previous action!\n" + textArea.getText());
                    }
                } else {
                    textArea.setText("You have used all of your actions. Pass the turn.\n" + textArea.getText());
                }
            }
            else {
                textArea.setText("It's not your round!\n" + textArea.getText());
            }
        } catch (RemoteException e1) {
            logger.log(Level.INFO,"shootAction Error",e1);
        }
    }

    /**
     * Displays the squares on the GUI map the player can move on during the
     * MoveAction().
     */
    private void setMovementSquares(){
        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
            for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                    rectangle.setFill(Color.color(0, 0.2, 1, 0.4));
                    rectangle.setOnMouseClicked(o -> {
                        try {
                            guiController.getRmiStub().setNewPosition(guiController.getRmiStub().getMatch().searchPlayerByClientName(username).getClientName(), Integer.parseInt(rectangle.getId()));
                            guiController.getRmiStub().notifyAllClientMovement(this.username, Integer.parseInt(rectangle.getId()));
                            guiController.getRmiStub().useAction(this.username);
                            guiController.getRmiStub().toggleAction(this.username);
                            updateAllGUI();
                            restoreSquares();
                        } catch (Exception exc) {
                            logger.log(Level.INFO,"setMovementSquare() Error",exc);
                        }
                    });
                    rectangle.setOnMouseEntered( enter ->
                            rectangle.setFill(Color.color(0,0.2,1,0.6)));
                    rectangle.setOnMouseExited( exit ->
                            rectangle.setFill(Color.color(0,0.2,1,0.4)));
                }
            }
        }
    }

    /**
     * Displays the squares on the GUI map the player can move on during the
     * MoveGrabAction().
     */
    private void setMoveGrabSquares(){
        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
            for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                    rectangle.setFill(Color.color(1, 1, 0, 0.4));
                    rectangle.setOnMouseClicked(o -> {
                        try {
                            guiController.getRmiStub().setNewPosition(guiController.getRmiStub().getMatch().searchPlayerByClientName(username).getClientName(), Integer.parseInt(rectangle.getId()));
                            guiController.getRmiStub().notifyAllClientMovement(this.username, Integer.parseInt(rectangle.getId()));
                            //textArea.setText("New position: " + rectangle.getId() + "\n" + textArea.getText());
                            ammoSet.getChildren().get(Integer.parseInt(rectangle.getId())).setTranslateX(-350);
                            ammoSet.getChildren().get(Integer.parseInt(rectangle.getId())).setTranslateY(250);
                            boolean isSpawn = guiController.getRmiStub().isSpawnPoint(myRemoteView.getPosition());
                            if (!isSpawn) {
                                guiController.getRmiStub().pickUpAmmo(myRemoteView.getUsername());
                                guiController.getRmiStub().notifyAllClientPickUpAmmo(username, guiController.getRmiStub().showLastAmmo().toString());
                                //textArea.setText("You have picked up an ammo:\n"+guiController.getRmiStub().showLastAmmo().toString()+"\n" + textArea.getText());
                            }
                            else {
                                //indexToPickUp e indexToSwitch sono due input dell'utente
                                displayCabinet();
                                if(indexToPickUp!= -1){
                                    if (myRemoteView.getWeapons().size() < 3) {
                                        guiController.getRmiStub().pickUpWeapon(username, indexToPickUp);
                                        textArea.setText("You have picked a weapon: " + myRemoteView.getWeapons().get(myRemoteView.getWeapons().size() - 1) + "\n" + textArea.getText());
                                    } else if (myRemoteView.getWeapons().size() == 3) {
                                        if(indexToDrop != -1){
                                            textArea.setText("You already have three weapons in you hand.\nChoose one to discard..." + "\n" + textArea.getText());
                                            guiController.getRmiStub().pickUpWeapon(username,indexToPickUp,indexToDrop);
                                        }
                                    } else {
                                        throw new RemoteException("You have more than three weapons in your hand\n");
                                    }
                                    guiController.getRmiStub().notifyAllClientPickUpWeapon(this.username);
                                }
                            }
                            guiController.getRmiStub().useAction(this.username);
                            guiController.getRmiStub().toggleAction(this.username);
                            updateAllGUI();
                            restoreSquares();
                        } catch (Exception exc) {
                            logger.log(Level.INFO,"setMoveGrabSquares() Error",exc);
                        }
                    });
                    rectangle.setOnMouseEntered( enter ->
                            rectangle.setFill(Color.color(1,1,0,0.6)));
                    rectangle.setOnMouseExited( exit ->
                            rectangle.setFill(Color.color(1,1,0,0.4)));
                }
            }
        }
    }

    /**
     * Displays the squares on the GUI map the player can move on during the
     * ShootAction().
     * @param usableWeapons a collection of weapon the player can use during this action
     */
    private void setShootSquares(ArrayList<Weapon> usableWeapons){

        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
            for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                    rectangle.setFill(Color.color(1, 0, 0, 0.4));
                    rectangle.setOnMouseClicked(o -> {
                        try {
                            guiController.getRmiStub().setNewPosition(guiController.getRmiStub().getMatch().searchPlayerByClientName(username).getClientName(), Integer.parseInt(rectangle.getId()));
                            guiController.getRmiStub().notifyAllClientMovement(this.username, Integer.parseInt(rectangle.getId()));
                            ArrayList<Weapon> newUsableWeapons = guiController.getRmiStub().verifyWeapons(this.username);
                            displayUsableWeapons(newUsableWeapons);
                            //textArea.setText("New position: " + rectangle.getId()+"\n" + textArea.getText());
                            guiController.getRmiStub().toggleAction(this.username);
                            restoreSquares();
                            updateAllGUI();
                        } catch (Exception exc) {
                            logger.log(Level.INFO,"setMovementSquare() Error",exc);
                        }
                    });
                    rectangle.setOnMouseEntered( enter ->
                            rectangle.setFill(Color.color(1,0,0,0.6)));
                    rectangle.setOnMouseExited( exit ->
                            rectangle.setFill(Color.color(1,0,0,0.4)));
                }
            }
        }
    }
    /**
     * Displays the squares on the GUI map in order to move enemies
     * after hitting them. This action can be executed with some weapons.
     */
    private void setPushSquares(WeaponShot weaponShot){
        try{
            Shot currentEffect = null;
            for(Shot effect: weaponShot.getWeapon().getEffect())
                if( effect != null && effect.getNameEffect().equals(weaponShot.getNameEffect()))
                    currentEffect = effect;
            guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reachableSquares(weaponShot.getTargetPlayer().get(0).getPosition(),currentEffect.getMaxMovementTarget()));
        } catch (Exception exc) {
            logger.log(Level.INFO,"setMovementSquare() Error",exc);
        }
        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
            for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                    rectangle.setFill(Color.color(1, 0.4, 0, 0.4));
                    rectangle.setOnMouseClicked(o -> {
                        try {
                            weaponShot.setNewPosition(Integer.parseInt(rectangle.getId()));
                            guiController.getRmiStub().applyEffectWeapon(weaponShot);
                            restoreSquares();
                            guiController.getRmiStub().getMatch().searchPlayerByClientName(this.username).setCanMove(false);
                            if(weaponShot.getChosenEffect().getMaxMovementPlayer()>0)
                                moveAction(weaponShot.getChosenEffect().getMaxMovementPlayer());
                        } catch (Exception exc) {
                            logger.log(Level.INFO,"setMovementSquare() Error",exc);
                        }
                    });
                    rectangle.setOnMouseEntered( enter ->
                            rectangle.setFill(Color.color(1,0.4,0,0.6)));
                    rectangle.setOnMouseExited( exit ->
                            rectangle.setFill(Color.color(1,0.4,0,0.4)));
                }
            }
        }
    }

    /**
     * Displays the squares on the GUI map the player can move on during the
     * reloadShootAction() method
     */
    private void setReloadShootSquares(){
        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
            for (Square square : guiController.getMyRemoteView().getReachableSquare()) {
                if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                    rectangle.setFill(Color.color(0.5, 1, 0.5, 0.4));
                    rectangle.setOnMouseClicked(o -> {
                        try {
                            reloadAlert(false);
                        } catch (Exception exc) {
                            logger.log(Level.INFO,"setMovementSquare() Error",exc);
                        }
                    });
                    rectangle.setOnMouseEntered( enter ->
                            rectangle.setFill(Color.color(0.5,1,0.5,0.6)));
                    rectangle.setOnMouseExited( exit ->
                            rectangle.setFill(Color.color(0.5,1,0.5,0.4)));
                }
            }
        }
    }

    /**
     * Final Frenzy action that includes: movement, reloading weapons and shoot.
     * @param steps number of movement steps eligible for this action.
     */
    private void reloadShootAction(int steps){
        try {
            if(guiController.getRmiStub().getActivePlayer().equals(this.username)){
                if(guiController.getRmiStub().checkNumberAction(this.username)){
                    if (!myRemoteView.getCanMove()) {
                        guiController.getRmiStub().toggleAction(this.username);
                        guiController.getMyRemoteView().setReachableSquare(guiController.getRmiStub().reachableSquares(guiController.getMyRemoteView().getPosition(),steps));
                        setReloadShootSquares();
                    } else {
                        textArea.setText("Resolve your previous action!\n" + textArea.getText());
                    }
                } else {
                    textArea.setText("You have used all of your actions. Pass the turn.\n" + textArea.getText());
                }
            }
            else {
                textArea.setText("It's not your round!\n" + textArea.getText());
            }
        } catch (RemoteException e1) {
            logger.log(Level.INFO,"shootAction Error",e1);
        }
    }

    /**
     * It performs several mandatory tasks at the end of the game turn.
     * @throws RemoteException exception thrown if the connection fails.
     */
    private void endTurnActionsRoutine() throws RemoteException{
        if(guiController.getRmiStub().deathPlayer(this.username)) {
            guiController.getRmiStub().respawnPlayer();
        }
        guiController.getRmiStub().restoreMap();
        setAmmo(mapNumber);
        guiController.getRmiStub().resetActionNumber(username);
        guiController.getRmiStub().setActivePlayer(username);
    }

    /**
     * It shows the list of targets while using VortexCannon weapon.
     * @param weaponShot is the WeaponShot object containing infos.
     */
    private void displayVortexSquares(WeaponShot weaponShot){
        for (int i = 0; i < 12; i++) {
            Rectangle rectangle = (Rectangle) grid.getChildren().get(i);
            for (Square square : weaponShot.getSquares()) {
                if (rectangle.getId().equals(Integer.toString(square.getPosition()))) {
                    rectangle.setFill(Color.color(0.4, 0, 1, 0.4));
                    rectangle.setOnMouseClicked(o -> {
                        try {
                            ArrayList<Player> localTargets = new ArrayList<>();
                            localTargets = guiController.getRmiStub().getLocalTargets(this.username,Integer.parseInt(rectangle.getId()));
                            if(!localTargets.isEmpty()){
                                weaponShot.getTargetablePlayer().addAll(localTargets);
                                weaponShot.setNewPosition(Integer.parseInt(rectangle.getId()));
                                displayTargets(weaponShot);
                            }
                            restoreSquares();
                        } catch (Exception exc) {
                            logger.log(Level.INFO,"setMovementSquare() Error",exc);
                        }
                    });
                    rectangle.setOnMouseEntered( enter ->
                            rectangle.setFill(Color.color(0.4,0,1,0.6)));
                    rectangle.setOnMouseExited( exit ->
                            rectangle.setFill(Color.color(0.4,0,1,0.4)));
                }
            }
        }
    }

    public void startMatch() {
        window.close();
        setGameScene();
        window.setScene(scene);
        window.show();
    }

    public void closeWindow() {
        Platform.exit();
        System.exit(0);
        //this.window.close();
    }
}