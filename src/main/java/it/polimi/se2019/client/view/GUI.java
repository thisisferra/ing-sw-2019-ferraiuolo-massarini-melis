package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.network.RMI.RMIServer;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class GUI extends Application{

    private Registry registry;
    private RMIServerInterface stub;
    private View view;

    private Stage window;
    private Group root = new Group();
    private Image image;
    private ImageView imageView = new ImageView();
    private GridPane grid = new GridPane();
    private HBox redBox = new HBox();
    private HBox blueBox = new HBox();
    private HBox yellowBox = new HBox();
    private StackPane cabinets = new StackPane();
    private HBox deathTrack = new HBox();
    private VBox cubeBox = new VBox();
    private StackPane ammoSet = new StackPane();
    private VBox playerboards = new VBox();
    private HBox weaponHand = new HBox();
    private HBox powerUpHand = new HBox();
    private VBox leftMenu = new VBox();
    private StackPane stack = new StackPane();
    private VBox rightPane = new VBox();
    private BorderPane borderPane = new BorderPane();
    private ArrayList<String> weaponsName = new ArrayList<>();
    private ArrayList<String> powerUpsName = new ArrayList<>();
    private TextArea textArea = new TextArea("Welcome to the game!\n");
    private Image iconImage = null;
    private ImageView iconView=null;
    static final String BUTTON_STYLE = "-fx-background-color: #3c3c3c;-fx-text-fill: #999999;-fx-border-color: #2b2b2b;";
    static final String HIGHLIGHT_BUTTON_STYLE = "-fx-background-color: #bbbbbb;-fx-text-fill: #999999;-fx-border-color: #2b2b2b;";
    private int mapNumber = 4;
    private Scene scene,loginScene;


    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            registry = LocateRegistry.getRegistry(1099);
            stub = (RMIServerInterface) registry.lookup("remServer");
        } catch (Exception e) {
            e.printStackTrace();
        }
        window = primaryStage;
        window.setScene(loginScene());
        window.setTitle("Adrenaline");
        window.setResizable(true);
        window.setFullScreen(false);
        window.alwaysOnTopProperty();
        window.setOnCloseRequest(e->{
            e.consume();
            closeProgram();
        });


        // map background
        image = new Image(new FileInputStream("src/main/resources/Images/Maps/Map"+mapNumber+".png"));
        imageView = new ImageView(image);
        imageView.setFitHeight(700);
        imageView.setPreserveRatio(true);

        //ammos
        setAmmo(mapNumber);

        //deathtrack
        setDeathTrackSkulls();
        deathTrack.setPickOnBounds(false);

        //map grid
        for(int row = 0; row <3;row++){
            for(int col = 0; col <4;col++){
                Rectangle box = new Rectangle(160, 160);
                box.setFill(Color.color(1,1,1,0.1));
                box.setId(Integer.toString(row*4 + col));
                grid.add(box, col, row);
            }
            for(Node rect : grid.getChildren()){
                rect.setOnMouseClicked( e -> {
                    textArea.setText("Square #: " + rect.getId()+"\n"+textArea.getText());
                    ammoSet.getChildren().get(Integer.parseInt(rect.getId())).setTranslateX(500);
                    ammoSet.getChildren().get(Integer.parseInt(rect.getId())).setTranslateY(500);
                });
            }
        }

        grid.setTranslateX(149);
        grid.setTranslateY(145);
        grid.setGridLinesVisible(true);
        grid.setPickOnBounds(false);

        //cabinets
        resetWeaponsName(weaponsName);
        setWeaponView(redBox,weaponsName);
        setWeaponView(yellowBox,weaponsName);
        setWeaponView(blueBox,weaponsName);
        setWeaponView(weaponHand,weaponsName);
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
        cabinets.getChildren().addAll(redBox,blueBox,yellowBox);

        //powerups
        resetPowerUpsName(powerUpsName);
        setPowerUpsView(powerUpHand,powerUpsName);

        //playerboards
        setPlayerboards();

        //points
        Label points = new Label("Points: " + getPoints());
        points.setStyle("-fx-background-color: #404040; -fx-text-fill: #aaaaaa");


        //buttons
        Button moveButton = setButton("src/main/resources/Images/icons/move_icon.png",50,"Move");
        Button grabButton = setButton("src/main/resources/Images/icons/grab_icon.png",50,"Move and grab");
        Button shootButton = setButton("src/main/resources/Images/icons/shoot_icon.png",50,"Shoot");
        Button passButton = setButton("src/main/resources/Images/icons/pass_icon.png",50,"Pass turn and reload");
        Button powerUps = setButton("src/main/resources/Images/icons/powerup_icon.png",50,"");
        moveButton.setOnAction(e -> {
            try {
                view.setCanMove(!view.getCanMove());
                if (view.getCanMove()) {
                    view.setReachableSquare(stub.reacheableSquare(view.getPosition()));
                    view.getReachableSquare();
                }
                else {
                    System.out.println("You have already selected this option");
                }
                //TODO in questo istante le celle raggiungibili sono salvate nella view locale al client, cambiare la mappa in modo che siano highlightateeee
                //TODO Variabile che indica la nuova posizione scelta dal giocatore
                //stub.setNewPosition(view.username, newPosition);
            }
            catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        powerUps.setOnAction(e->{
            HBox box = new HBox();
            for(Node obj: powerUpHand.getChildren()){

                ImageView copy = (ImageView) obj;
                ImageView copiedPowerUp = new ImageView(copy.getImage());
                copiedPowerUp.setPreserveRatio(true);
                copiedPowerUp.setFitHeight(200);
                box.getChildren().add(copiedPowerUp);

            }
            PlayerStatus.display(box,"Power ups");
        });
        Button playersButton = setButton("src/main/resources/Images/icons/players_icon.png",50,"");
        playersButton.setOnAction(e->{
            VBox box = new VBox();
            for(Node obj: playerboards.getChildren()){

                ImageView copy = (ImageView) obj;
                ImageView copiedPlayerboard = new ImageView(copy.getImage());
                copiedPlayerboard.setPreserveRatio(true);
                copiedPlayerboard.setFitHeight(100);
                box.getChildren().add(copiedPlayerboard);

            }
            PlayerStatus.display(box,"Players");
        });
        Button weapons = setButton("src/main/resources/Images/icons/weapon_icon.png",50,"");
        weapons.setOnAction(e->{
            HBox box = new HBox();
            for(Node obj: weaponHand.getChildren()){

                ImageView copy = (ImageView) obj;
                ImageView copiedView = new ImageView(copy.getImage());
                copiedView.setPreserveRatio(true);
                copiedView.setFitHeight(300);
                box.getChildren().add(copiedView);

            }
            PlayerStatus.display(box,"Weapons");
        });
        Button button = new Button("x");
        button.setStyle(BUTTON_STYLE);

        button.setOnAction(e -> {
            setWeaponView(redBox,weaponsName);
            setWeaponView(blueBox,weaponsName);
            setWeaponView(yellowBox,weaponsName);
            window.show();
        });

        Button button2 = new Button("y");
        grabButton.setOnMouseEntered(e -> grabButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        grabButton.setOnMouseExited(e -> grabButton.setStyle(BUTTON_STYLE));
        grabButton.setOnAction(e -> {
            try {
                view.setReachableSquare(stub.reacheableSquare(view.getPosition()));
                view.getReachableSquare();
                //TODO cliccare sulla cella in cui voglio muovermi,
                // Ci sarà qualcosa del tipo setPosition(int newPosition)
                stub.pickUpAmmo(view.getUsername(), view.getPosition());
            }
            catch(Exception e1) {
                e1.printStackTrace();
            }
        });

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
        leftMenu.getChildren().addAll(moveButton,grabButton,shootButton,passButton,weapons,powerUps,playersButton,points,cubeBox,button,button2);

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

        stack.getChildren().addAll(imageView,ammoSet,firstPlayer,deathTrack,cabinets,grid);
        root = new Group(stack);
        root.setTranslateY(-375);
        root.setTranslateX(25);
        borderPane.setStyle("-fx-background-color: #505050");
        borderPane.setCenter(root);
        borderPane.setRight(rightPane);
        borderPane.setLeft(leftMenu);

        scene = new Scene(borderPane,1300,700);
        window.show();
    }

    public void  setPowerUpsView(HBox deck,ArrayList<String> powerUpsName){
        deck.getChildren().clear();
        if(powerUpsName.size()>2){
            for(int i=0 ;i<3; i++){
                Collections.shuffle(powerUpsName);
                String path;
                path = powerUpsName.get(0);
                Image imagePowerUp = null;
                try {
                    imagePowerUp = new Image(new FileInputStream( path));
                } catch (FileNotFoundException e){
                    System.out.println("File non trovato.");
                }
                ImageView powerUpView = new ImageView(imagePowerUp);
                powerUpView.setFitWidth(90);
                powerUpView.setPreserveRatio(true);
                deck.getChildren().add(powerUpView);
                powerUpsName.remove(path);
                Collections.shuffle(powerUpsName);

            }
        }
        else  resetWeaponsName(weaponsName);
    }

    public void resetWeaponsName(ArrayList<String> weaponsName){
        weaponsName.clear();
        weaponsName.add("src/main/resources/Images/Weapons/cyber_blade.png");
        weaponsName.add("src/main/resources/Images/Weapons/electroscythe.png");
        weaponsName.add("src/main/resources/Images/Weapons/flamethrower.png");
        weaponsName.add("src/main/resources/Images/Weapons/furnace.png");
        weaponsName.add("src/main/resources/Images/Weapons/granade_launcher.png");
        weaponsName.add("src/main/resources/Images/Weapons/heatseeker.png");
        weaponsName.add("src/main/resources/Images/Weapons/hellion.png");
        weaponsName.add("src/main/resources/Images/Weapons/lock_rifle.png");
        weaponsName.add("src/main/resources/Images/Weapons/machine_gun.png");
        weaponsName.add("src/main/resources/Images/Weapons/plasma_gun.png");
        weaponsName.add("src/main/resources/Images/Weapons/power_glove.png");
        weaponsName.add("src/main/resources/Images/Weapons/railgun.png");
        weaponsName.add("src/main/resources/Images/Weapons/rocket_launcher.png");
        weaponsName.add("src/main/resources/Images/Weapons/shockwave.png");
        weaponsName.add("src/main/resources/Images/Weapons/shotgun.png");
        weaponsName.add("src/main/resources/Images/Weapons/sledgehammer.png");
        weaponsName.add("src/main/resources/Images/Weapons/thor.png");
        weaponsName.add("src/main/resources/Images/Weapons/tractor_beam.png");
        weaponsName.add("src/main/resources/Images/Weapons/vortex_cannon.png");
        weaponsName.add("src/main/resources/Images/Weapons/whisperer.png");
        weaponsName.add("src/main/resources/Images/Weapons/zx-2.png");


    }

    public void  setWeaponView(HBox cabinet,ArrayList<String> weaponsName){
        cabinet.getChildren().clear();
        if(weaponsName.size()>2){
            for(int i=0 ;i<3; i++){
                Collections.shuffle(weaponsName);
                String weaponPath;
                weaponPath = weaponsName.get(0);
                Image imageWeapon = null;
                try {
                    imageWeapon = new Image(new FileInputStream( weaponPath));
                } catch (FileNotFoundException e){
                    System.out.println("File non trovato.");
                }
                ImageView weaponView = new ImageView(imageWeapon);
                weaponView.setFitWidth(90);
                weaponView.setPreserveRatio(true);
                cabinet.getChildren().add(weaponView);
                weaponsName.remove(weaponPath);
                Collections.shuffle(weaponsName);

            }
            for(Node obj: cabinet.getChildren()){
                VBox box = new VBox();
                obj.setOnMouseEntered(e->{
                    box.getChildren().clear();
                    if(rightPane.getChildren().size() >1)
                        rightPane.getChildren().remove(rightPane.getChildren().size()-1);

                    ImageView boxView = (ImageView) obj;
                    ImageView view = new ImageView(boxView.getImage());
                    view.setPreserveRatio(true);
                    view.setFitHeight(350);
                    box.getChildren().add(view);
                    rightPane.getChildren().add(box);
                    box.setTranslateX(-450);
                });
                obj.setOnMouseExited( e-> {
                    rightPane.getChildren().remove(box);
                });
            }
        }
        else  resetWeaponsName(weaponsName);
    }

    public void resetPowerUpsName(ArrayList<String> powerUpsName){
        powerUpsName.clear();
        powerUpsName.add("src/main/resources/Images/PowerUps/blue_newton.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/blue_tagback_grenade.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/blue_teleporter.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/blue_targeting_scope.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/red_newton.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/red_tagback_grenade.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/red_teleporter.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/red_targeting_scope.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/yellow_newton.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/yellow_tagback_grenade.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/yellow_teleporter.png");
        powerUpsName.add("src/main/resources/Images/PowerUps/yellow_targeting_scope.png");


    }

    public String getRedCubes(){
        return "2";
    }

    public String getYellowCubes(){
        return "2";
    }

    public String getBlueCubes(){
        return "3";
    }

    public void setDeathTrackSkulls(){
        Image skullImage = null;
        ImageView skullView;
        try {
            skullImage = new Image(new FileInputStream("src/main/resources/Images/icons/skull_icon.png"));
        } catch (FileNotFoundException e){
            System.out.println("File non trovato.");
        }

        for(int i=0; i<8;i++){
            skullView = new ImageView(skullImage);
            skullView.setFitHeight(40);
            skullView.setSmooth(true);
            skullView.setPreserveRatio(true);
            deathTrack.getChildren().add(skullView);
        }
    }

    public void addDeathTrackDamage(String color){
        Image tearImage = null;
        try {
            tearImage = new Image(new FileInputStream("src/main/resources/Images/icons/"+color+"_damage_icon.png"));
        } catch (FileNotFoundException e){
            System.out.println("File non trovato.");
        }
        ImageView tear = new ImageView(tearImage);
        tear.setFitHeight(40);
        tear.setSmooth(true);
        tear.setPreserveRatio(true);
        deathTrack.getChildren().add(tear);
    }

    public void setPlayerboards(){
        playerboards = new VBox();
        ImageView img;
        Image image1 = null;
        Image image2 = null;
        Image image3 = null;
        Image image4 = null;
        Image image5 = null;
        try{
            image1 = new Image(new FileInputStream("src/main/resources/Images/Playerboards/Banshee.png"));
            image2 = new Image(new FileInputStream("src/main/resources/Images/Playerboards/Dozer.png"));
            image3 = new Image(new FileInputStream("src/main/resources/Images/Playerboards/Distructor.png"));
            image4 = new Image(new FileInputStream("src/main/resources/Images/Playerboards/Violet.png"));
            image5 = new Image(new FileInputStream("src/main/resources/Images/Playerboards/Sprog.png"));
        }catch(FileNotFoundException e){
            System.out.println("File non trovato");
        }
        img = new ImageView(image1);
        playerboards.getChildren().add(img);
        img = new ImageView(image2);
        playerboards.getChildren().add(img);
        img = new ImageView(image3);
        playerboards.getChildren().add(img);
        img = new ImageView(image4);
        playerboards.getChildren().add(img);
        img = new ImageView(image5);
        playerboards.getChildren().add(img);
        for(Node obj :playerboards.getChildren()){
            ImageView object = (ImageView) obj;
            object.setPreserveRatio(true);
            object.setFitHeight(100);
        }
    }

    public void setAmmo(int map){

        Image ammoBack = null;

        try {
            ammoBack = new Image(new FileInputStream("src/main/resources/Images/Ammo/ammoback.png"));
        } catch (FileNotFoundException e){
            System.out.println("File non trovato");
        }

        ammoSet.getChildren().clear();
        for(int i = 0; i<12;i++){
            ammoSet.getChildren().add(new ImageView(ammoBack));
        }
        for(Node obj : ammoSet.getChildren()){
            obj.setTranslateX(500);
            obj.setTranslateY(500);
        }
        switch(map){
            case 1 : {



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
            case 2 : {


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
            case 3 : {


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
            case 4 : {

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
            default :
                System.out.println("Error");
        }
        for(Node obj : ammoSet.getChildren()){
            obj.setScaleX(0.3);
            obj.setScaleY(0.3);
        }
    }

    public void setCubes(){
        ImageView cubeImage = null;
        try{
            cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/redCube.png")));
        }catch(FileNotFoundException e){
            System.out.println("File non trovato.");
        }

        if(cubeImage != null){
            cubeImage.setFitWidth(20);
            cubeImage.setPreserveRatio(true);
        }

        Label redLabel = new Label(" " + getRedCubes()+"  ",cubeImage);
        redLabel.setStyle("-fx-text-fill: #ff0000; -fx-background-color: #404040");

        try{
            cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/yellowCube.png")));
        }catch(FileNotFoundException e){
            System.out.println("File non trovato.");
        }
        if(cubeImage != null){
            cubeImage.setFitWidth(20);
            cubeImage.setPreserveRatio(true);
        }
        Label yellowLabel = new Label(" " + getYellowCubes()+ "  ",cubeImage);
        yellowLabel.setStyle("-fx-text-fill: #fff000; -fx-background-color: #404040");

        try{
            cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/blueCube.png")));
        }catch(FileNotFoundException e){
            System.out.println("File non trovato.");
        }
        if(cubeImage != null){
            cubeImage.setFitWidth(20);
            cubeImage.setPreserveRatio(true);
        }
        Label blueLabel = new Label(" " + getBlueCubes() + "  ",cubeImage);
        blueLabel.setStyle("-fx-text-fill: #0010ff; -fx-background-color: #404040;");

        cubeBox.getChildren().addAll(redLabel,yellowLabel,blueLabel);
        cubeBox.setSpacing(5);
    }

    public int getPoints(){
        return 43;
    }

    private void closeProgram() {
        Boolean answer = ConfirmBox.display("Exit Adrenaline", "Are you sure?");
        if (answer) {
            window.close();
        }
    }

    public Button setButton(String path,int width,String text){
        Image iconImage = null;
        try{
            iconImage = new Image(new FileInputStream(path));
        }catch (FileNotFoundException e){
            System.out.println("File non trovato.");
        }
        ImageView iconView2 = new ImageView(iconImage);
        iconView2.setFitWidth(width);
        iconView2.setPreserveRatio(true);
        Button newButton = new Button("",iconView2);
        newButton.setOnAction(e-> textArea.setText(text +"\n"+ textArea.getText()));
        newButton.setOnMouseEntered(e -> newButton.setStyle(HIGHLIGHT_BUTTON_STYLE));
        newButton.setOnMouseExited(e -> newButton.setStyle(BUTTON_STYLE));
        newButton.setStyle(BUTTON_STYLE);


        return newButton;
    }

    public Scene loginScene(){

        //TODO implememtare scelta del colore per il giocatore
            String username;
            String password;
            GridPane grid = new GridPane();
            grid.setPadding(new Insets(50,75,50,75));
            grid.setVgap(8);
            grid.setHgap(10);

            //Name label
            Label nameLabel = new Label("Username: ");
            GridPane.setConstraints(nameLabel, 0,0);

            //Name input
            TextField nameInput = new TextField();
            //TextField nameInput = new TextField("Mattia");
            nameInput.setPromptText("Username");
            nameInput.setStyle("-fx-background-color: #726B72");
            GridPane.setConstraints(nameInput, 1,0);

            //Password label
            Label passLabel = new Label("Password");
            GridPane.setConstraints(passLabel, 0,1);

            //Password input
            //textfield = casella in cui scrivere
            // setPromptText inserisce una frase in grigio in textfield
            TextField passInput = new TextField();
            passInput.setStyle("-fx-background-color: #726B72");
            passInput.setPromptText("Password");
            GridPane.setConstraints(passInput,1,1);

            Button loginButton = new Button("Log in");
            GridPane.setConstraints(loginButton,1,2);
            loginButton.setOnAction(e-> {
                try {
                    boolean check = stub.register(nameInput.getText());
                    if(!check) {
                        //TODO implementare una finestra di errore:
                        // username già presente e ripresentare la finestra di login
                    }
                    //TODO implementare una finestra di attesa, di durata definita (es. 20 s)
                    // per aspettare che tutti i giocatori siano connessi
                    else{
                        view = new View(nameInput.getText());
                    }
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
                window.setScene(scene);
            });
            //grid.getChildren().addAll(nameLabel,nameInput,passLabel,passInput,loginButton);
            grid.getChildren().addAll(nameInput,passInput,loginButton);

            Scene scene = new Scene(grid,300,200);
            grid.setStyle("-fx-background-color: #c4bb55");
            return scene;

    }
}
