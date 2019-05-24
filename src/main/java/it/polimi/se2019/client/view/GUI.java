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
    private HBox deathTrack = new HBox();
    private HBox weaponHand = new HBox();
    private HBox powerUpHand = new HBox();
    private StackPane stack = new StackPane();
    private VBox rightPane = new VBox();
    private HBox powerUpsDeck = new HBox();
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



        // grid image background
        image = new Image(new FileInputStream("src/main/resources/Images/Maps/Map4.png"));
        imageView = new ImageView(image);
        imageView.setFitHeight(700);

        imageView.setPreserveRatio(true);

        /*
        for(int i=0 ;i<9; i++)
            deathTrack.getChildren().add(new Rectangle(37,55,Color.color(1,1,1,0.2)));
         */
        addDeathTrackDamage("blue");
        addDeathTrackDamage("purple");
        addDeathTrackDamage("yellow");
        addDeathTrackDamage("grey");
        addDeathTrackDamage("green");
        addDeathTrackDamage("yellow");
        addDeathTrackDamage("grey");


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

        resetWeaponsName(weaponsName);
        setWeaponView(redBox,weaponsName);
        setWeaponView(yellowBox,weaponsName);
        setWeaponView(blueBox,weaponsName);
        setWeaponView(weaponHand,weaponsName);

        resetPowerUpsName(powerUpsName);
        setPowerUpsView(powerUpHand,powerUpsName);

        Image iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/grab_icon.png"));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button grab_button = new Button("",iconView);
        grab_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");

        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/move_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button move_button = new Button("",iconView);
        move_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");

        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/shoot_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button shoot_button = new Button("", iconView);
        shoot_button.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999");

        iconImage = new Image(new FileInputStream("src/main/resources/Images/icons/pass_icon.png"));
        iconView = new ImageView(iconImage);
        iconView.setFitWidth(50);
        iconView.setPreserveRatio(true);
        Button pass_button = new Button("",iconView);
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
                ImageView copiedView = new ImageView(copy.getImage());
                copiedView.setPreserveRatio(true);
                copiedView.setFitHeight(200);
                box.getChildren().add(copiedView);

            }
            Hand.display(box,"Power ups");
        });
        powerUps.setStyle("-fx-background-color: #3c3c3c;-fx-text-fill: #999999;");

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
            Hand.display(box,"Weapons");
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

        Image ammoBack = new Image(new FileInputStream("src/main/resources/Images/Ammo/ammoback.png"));
        Group ammoSet = new Group();
        ammoSet.getChildren().addAll(
                new ImageView(ammoBack),
                new ImageView(ammoBack),
                new ImageView(ammoBack),
                new ImageView(ammoBack),
                new ImageView(ammoBack),
                new ImageView(ammoBack),
                new ImageView(ammoBack)
        );

        for(Node img : ammoSet.getChildren()){
            img.setScaleX(0.3);
            img.setScaleY(0.3);
        }
        ammoSet.getChildren().get(0).setLayoutX(-70);
        ammoSet.getChildren().get(0).setLayoutY(130);

        ammoSet.getChildren().get(1).setLayoutX(90);
        ammoSet.getChildren().get(1).setLayoutY(205);

        ammoSet.getChildren().get(2).setLayoutX(75);
        ammoSet.getChildren().get(2).setLayoutY(335);

        ammoSet.getChildren().get(3).setLayoutX(275);
        ammoSet.getChildren().get(3).setLayoutY(360);

        ammoSet.getChildren().get(4).setLayoutX(505);
        ammoSet.getChildren().get(4).setLayoutY(355);

        ammoSet.getChildren().get(5).setLayoutX(100);
        ammoSet.getChildren().get(5).setLayoutY(500);

        ammoSet.getChildren().get(6).setLayoutX(500);
        ammoSet.getChildren().get(6).setLayoutY(500);

        ImageView i1,i2,i3,i4,i5,i6,i7;
        i1 = new ImageView(ammoBack);
        i2 = new ImageView(ammoBack);
        i3 = new ImageView(ammoBack);
        i4 = new ImageView(ammoBack);
        i5 = new ImageView(ammoBack);
        i6 = new ImageView(ammoBack);
        i7 = new ImageView(ammoBack);

        i1.setScaleX(0.3);
        i1.setScaleY(0.3);
        i1.setTranslateX(-280);
        i1.setTranslateY(-170);


        StackPane cabinets = new StackPane();
        grid.setPickOnBounds(false);
        cabinets.setPickOnBounds(false);
        deathTrack.setPickOnBounds(false);
        i1.setPickOnBounds(false);
        i2.setPickOnBounds(false);
        i3.setPickOnBounds(false);
        i4.setPickOnBounds(false);
        i5.setPickOnBounds(false);
        i6.setPickOnBounds(false);
        i7.setPickOnBounds(false);
        cabinets.getChildren().addAll(redBox,blueBox,yellowBox);
        stack.getChildren().addAll(imageView,deathTrack,cabinets,grid);

        //caselle di movimento
        grid.setTranslateX(149);
        grid.setTranslateY(145);
        grid.setGridLinesVisible(true);

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

        //deathtracker
        deathTrack.setTranslateX(65);
        deathTrack.setTranslateY(40);
        deathTrack.setSpacing(-2);

        root = new Group(stack);
        root.setTranslateY(-375);
        root.setTranslateX(25);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #505050");
        borderPane.setCenter(root);
        VBox leftMenu = new VBox();

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

        leftMenu.getChildren().addAll(move_button,grab_button,shoot_button,pass_button,weapons,powerUps,button,cubeBox);
        borderPane.setLeft(leftMenu);

        textArea.setPrefWidth(225);
        textArea.setPrefHeight(300);
        textArea.setTranslateX(-450);
        textArea.setTranslateY(10);

        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle("-fx-control-inner-background:#717171;  -fx-highlight-fill: #f1f7eb; -fx-highlight-text-fill: #717171; -fx-text-fill: #f1f7eb; ");
        rightPane.getChildren().add(textArea);
        borderPane.setRight(rightPane);
        rightPane.setSpacing(10);
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
}
