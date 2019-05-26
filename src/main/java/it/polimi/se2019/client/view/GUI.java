package it.polimi.se2019.client.view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class GUI extends Application{

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


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Adrenaline");
        window.setResizable(true);
        window.setFullScreen(false);
        window.centerOnScreen();


        // map background
        image = new Image(new FileInputStream("src/main/resources/Images/Maps/Map4.png"));
        imageView = new ImageView(image);
        imageView.setFitHeight(700);
        imageView.setPreserveRatio(true);

        //deathtrack
        addDeathTrackDamage("blue");
        addDeathTrackDamage("purple");
        addDeathTrackDamage("yellow");
        addDeathTrackDamage("grey");
        addDeathTrackDamage("green");
        addDeathTrackDamage("yellow");
        addDeathTrackDamage("grey");
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


        //buttons
        Image iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/grab_icon.png"));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button grab_button = new Button("",iconView);
        grab_button.setOnAction(e-> textArea.setText("Move and grab\n"+ textArea.getText()));
        grab_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");

        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/move_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button move_button = new Button("",iconView);
        move_button.setOnAction(e-> textArea.setText("Move\n"+ textArea.getText()));
        move_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");

        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/shoot_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button shoot_button = new Button("", iconView);
        shoot_button.setOnAction(e-> textArea.setText("Shoot\n"+ textArea.getText()));
        shoot_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");

        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/pass_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button pass_button = new Button("",iconView);
        pass_button.setOnAction(e-> textArea.setText("Pass turn and reload\n"+ textArea.getText()));
        pass_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");

        //-fx-border-color: #D219FF; -fx-border-width: 1px;
        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/powerup_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button powerUps = new Button("",iconView);
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
        powerUps.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999;");


        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/players_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button players_button = new Button("",iconView);
        players_button.setOnAction(e->{
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
        players_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999;");


        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/weapon_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button weapons = new Button("",iconView);
        weapons.setOnAction(e->{
            HBox box = new HBox();
            for(Node obj: weaponHand.getChildren()){

                ImageView copy = (ImageView) obj;
                ImageView copiedView = new ImageView(copy.getImage());
                copiedView.setPreserveRatio(true);
                copiedView.setFitHeight(200);
                box.getChildren().add(copiedView);

            }
            PlayerStatus.display(box,"Weapons");
        });
        weapons.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");
        Button button = new Button("x");
        button.setStyle("-fx-background-color: #505050;-fx-text-fill: #999999;");

        button.setOnAction(e -> {
            setWeaponView(redBox,weaponsName);
            setWeaponView(blueBox,weaponsName);
            setWeaponView(yellowBox,weaponsName);
            window.show();
        });


        //ammos


        //deathtracker
        deathTrack.setTranslateX(65);
        deathTrack.setTranslateY(40);
        deathTrack.setSpacing(-2);


        //cubes
        VBox cubeBox = new VBox();
        ImageView cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/redCube.png")));
        cubeImage.setFitWidth(20);
        cubeImage.setPreserveRatio(true);
        Label redLabel = new Label("" + getRedCubes(),cubeImage);
        redLabel.setStyle("-fx-text-fill: #ff0000; -fx-background-color: #505050");

        cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/yellowCube.png")));
        cubeImage.setFitWidth(20);
        cubeImage.setPreserveRatio(true);
        Label yellowLabel = new Label("" + getYellowCubes(),cubeImage);
        yellowLabel.setStyle("-fx-text-fill: #fff000; -fx-background-color: #505050");

        cubeImage = new ImageView(new Image(new FileInputStream("src/main/resources/Images/icons/blueCube.png")));
        cubeImage.setFitWidth(20);
        cubeImage.setPreserveRatio(true);
        Label blueLabel = new Label("" + getBlueCubes(),cubeImage);
        blueLabel.setStyle("-fx-text-fill: #0010ff; -fx-background-color: #505050");

        cubeBox.getChildren().addAll(redLabel,yellowLabel,blueLabel);
        cubeBox.setSpacing(5);

        //left boarderpane
        leftMenu.getChildren().addAll(move_button,grab_button,shoot_button,pass_button,weapons,powerUps,players_button,button,cubeBox);


        //right borderpane

        //textarea
        textArea.setPrefWidth(225);
        textArea.setPrefHeight(300);
        textArea.setTranslateX(-450);
        textArea.setTranslateY(10);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle("-fx-control-inner-background:#717171;  -fx-highlight-fill: #f1f7eb; -fx-highlight-text-fill: #717171; -fx-text-fill: #f1f7eb; ");

        rightPane.getChildren().add(textArea);
        rightPane.setSpacing(10);

        stack.getChildren().addAll(imageView,ammoSet,deathTrack,cabinets,grid);
        root = new Group(stack);
        root.setTranslateY(-375);
        root.setTranslateX(25);
        borderPane.setStyle("-fx-background-color: #505050");
        borderPane.setCenter(root);
        borderPane.setRight(rightPane);
        borderPane.setLeft(leftMenu);

        Scene scene = new Scene(borderPane,1300,700);
        window.setScene(scene);
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
                obj.setOnMouseClicked(e->{
                    if(rightPane.getChildren().size() >1)
                        rightPane.getChildren().remove(rightPane.getChildren().size()-1);
                    VBox box = new VBox();
                    ImageView we = (ImageView) obj;
                    ImageView wewe = new ImageView(we.getImage());
                    wewe.setPreserveRatio(true);
                    wewe.setFitHeight(350);
                    box.getChildren().add(wewe);
                    rightPane.getChildren().add(box);
                    box.setTranslateX(-450);
                    box.setOnMouseClicked( o -> {
                        rightPane.getChildren().remove(box);
                    });
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
        ImageView img = new ImageView(ammoBack);




        switch(map){
            case 1 : {

                ammoSet.getChildren().get(0).setTranslateX(-30);
                ammoSet.getChildren().get(0).setTranslateY(-200);

                ammoSet.getChildren().get(1).setTranslateX(-70);
                ammoSet.getChildren().get(1).setTranslateY(130);

                ammoSet.getChildren().get(2).setTranslateX(-70);
                ammoSet.getChildren().get(2).setTranslateY(130);

                ammoSet.getChildren().get(3).setTranslateX(-70);
                ammoSet.getChildren().get(3).setTranslateY(130);

                ammoSet.getChildren().get(4).setTranslateX(-70);
                ammoSet.getChildren().get(4).setTranslateY(130);

                ammoSet.getChildren().get(5).setTranslateX(-70);
                ammoSet.getChildren().get(5).setTranslateY(130);

                ammoSet.getChildren().get(6).setTranslateX(-70);
                ammoSet.getChildren().get(6).setTranslateY(130);
                break;
            }
            case 2 : {
                ammoSet.getChildren().get(0).setTranslateX(-70);
                ammoSet.getChildren().get(0).setTranslateY(130);

                ammoSet.getChildren().get(1).setTranslateX(-70);
                ammoSet.getChildren().get(1).setTranslateY(130);

                ammoSet.getChildren().get(2).setTranslateX(-70);
                ammoSet.getChildren().get(2).setTranslateY(130);

                ammoSet.getChildren().get(3).setTranslateX(-70);
                ammoSet.getChildren().get(3).setTranslateY(130);

                ammoSet.getChildren().get(4).setTranslateX(-70);
                ammoSet.getChildren().get(4).setTranslateY(130);

                ammoSet.getChildren().get(5).setTranslateX(-70);
                ammoSet.getChildren().get(5).setTranslateY(130);

                ammoSet.getChildren().get(6).setTranslateX(-70);
                ammoSet.getChildren().get(6).setTranslateY(130);
                break;
            }
            case 3 : {
                ammoSet.getChildren().get(0).setTranslateX(-70);
                ammoSet.getChildren().get(0).setTranslateY(130);

                ammoSet.getChildren().get(1).setTranslateX(-70);
                ammoSet.getChildren().get(1).setTranslateY(130);

                ammoSet.getChildren().get(2).setTranslateX(-70);
                ammoSet.getChildren().get(2).setTranslateY(130);

                ammoSet.getChildren().get(3).setTranslateX(-70);
                ammoSet.getChildren().get(3).setTranslateY(130);

                ammoSet.getChildren().get(4).setTranslateX(-70);
                ammoSet.getChildren().get(4).setTranslateY(130);

                ammoSet.getChildren().get(5).setTranslateX(-70);
                ammoSet.getChildren().get(5).setTranslateY(130);

                ammoSet.getChildren().get(6).setTranslateX(-70);
                ammoSet.getChildren().get(6).setTranslateY(130);
                break;
            }
            case 4 : {
                ammoSet.getChildren().get(0).setTranslateX(-300);
                ammoSet.getChildren().get(0).setTranslateY(100);

                break;
            }
        }
        for(Node obj : ammoSet.getChildren()){
            obj.setScaleX(0.3);
            obj.setScaleY(0.3);
        }
    }
}
