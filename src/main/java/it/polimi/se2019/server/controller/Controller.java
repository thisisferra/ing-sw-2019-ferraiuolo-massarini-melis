package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.CubesChecker;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.client.view.View;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    //references to the player who's performing actions and the match state
    private Player player;
    private Match match;
    private View view;
    //each player draws two cards in their first turn and discard one
    public void startingDraw(){
        //TODO da togliere index
        int index = 1;
        this.player.getHand().addPowerUp(match.pickUpPowerUp());
        this.player.getHand().addPowerUp(match.pickUpPowerUp());
        PowerUp discarded = this.player.getHand().chooseToDiscard(index);
        if(discarded.getColor().equals("red")) this.player.setPosition(4);
        else if (discarded.getColor().equals("yellow")) this.player.setPosition(11);
        else if (discarded.getColor().equals("blue")) this.player.setPosition(2);
        else System.out.println(discarded.getColor() + "is not a valid color for respawning!");

    }

    public Player getPlayer() {
        return player;
    }

    //it places the player figure back on the board by drawing a powerup card
    public void resurrect(){
        String color = this.match.pickUpPowerUp().getColor();
        if(color.equals("red")) this.player.setPosition(4);
        else if (color.equals("yellow")) this.player.setPosition(11);
        else if (color.equals("blue")) this.player.setPosition(2);
        else System.out.println(color + "is not a valid color for respawning!");
    }

    //player's movement action, up to three tiles (standard)
    public void movement(){

        MovementChecker movement = new MovementChecker(this.match.getMap().getAllSquare(),3,this.player.getPosition());
        ArrayList<Square> availableSquares = movement.getReachableSquares();
        //update view
        //receives an input from view about the new position
        //if new position is in availableSquares --> set new position

    }
    //player's movement&grab action, up to 1 tile and grabbing a weapon or ammo.
    public void moveGather(){
        MovementChecker movement = new MovementChecker((this.match.getMap().getAllSquare()),1,this.player.getPosition());
        ArrayList<Square> availableSquares = movement.getReachableSquares();
        Square currentSquare;
        //update view
        //receives an input from view about the new position
        //if new position is in availableSquares --> set new position
        for(Square object : availableSquares)
            if(object.getPosition() == player.getPosition()){
                player.pickUpAmmo(object,this.match);
                //pickUpAmmo should be generalized to include weapons as well
            }
         //update view with movement, ammo tile or weapon changes


    }
    //selecting a weapon among the three available in the resurrection room (when buying) or before shooting.
    public void chooseWeapon(){

    }

    //player's shooting ability
    public void shoot(){

    }
    //a weapon is reloaded by paying its cost,
    public void reloadWeapons(){
        Weapon weaponBeingReloaded;
        ArrayList<Weapon> weaponList;
        weaponList = player.getHand().getWeapons();
        //ask index to client, for now it's zero
        int index = 0;
        weaponBeingReloaded = weaponList.get(index);
        CubesChecker cubesChecker = new CubesChecker(player.getPlayerBoard().getAmmoCubes(),weaponBeingReloaded.getReloadCost());
        if (cubesChecker.check()){
            weaponBeingReloaded.setLoad(true);
            player.getPlayerBoard().setAmmoCubes(cubesChecker.subtract());
        } else System.out.println(weaponBeingReloaded.getType() + " can't be reloaded.");
    }

    //it fills back weapon and ammo cards grabbed during the previous turn
    public void restoreMap(){
        for(Square object : match.getMap().getAllSquare())
                if(!object.isSpawnPoint())
                    object.setFull(true);
                else {
                    //for all arsenal's slot, if there are empty cells, refill each cabinet
                }
    }
    //the view is updated each time a change occurs.
    public void updateView(){

    }
    //at the end of each turn the game has to perform several tasks: refilling
    //the weapon slot, ammo and resurrecting players killed.
    public void endTurn(){

    }

    public Cubes askCubeToPay(Player player) {
        Cubes cubeToPay;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the color of the cube you want to pay: \n1. red\n2. blue\n3. yellow");
        String choice = input.nextLine();
        System.out.println(choice);
        if (choice.equals("red")) {
            if (checkEnoughCubes(player, choice)) {
                return cubeToPay = new Cubes(1, 0, 0);
            }
            else {
                System.out.println("You don't have enough red cubes");
                return null;
            }
        }
        else if (choice.equals("blue")) {
            if (checkEnoughCubes(player, choice)) {
                return cubeToPay = new Cubes(0, 0, 1);
            }
            else {
                System.out.println("You don't have enough blue cubes");
                return null;
            }
        }
        else if (choice.equals("yellow")) {
            if (checkEnoughCubes(player, choice)) {
                return cubeToPay = new Cubes(0, 1, 0);
            }
            else {
                System.out.println("You don't have enough yellow cubes");
                return null;
            }
        }
        else {
            System.out.println("You have digit an illegal color");
            return null;
        }
    }

    public boolean checkEnoughCubes(Player player, String choice) {
        if (choice.equals("red")) {
            if(player.getPlayerBoard().getAmmoCubes().getReds() > 0 ) return true;
        }
        if (choice.equals("blue")) {
            if(player.getPlayerBoard().getAmmoCubes().getBlues() > 0 ) return true;
        }
        if (choice.equals("yellow")) {
            if(player.getPlayerBoard().getAmmoCubes().getYellows() > 0 ) return true;
        }
        return false;
    }

    public PowerUp choosePowerUp(Player player) {
        Scanner input = new Scanner(System.in);
        System.out.println("Select which power-up to use: ");
        for(int i = 0; i < player.getHand().getPowerUps().size(); i++) {
            System.out.println(i + ". " + player.getHand().getPowerUps().get(i));
        }
        int powerUpChoosed = Integer.parseInt(input.nextLine());
        return player.getHand().getPowerUps().get(powerUpChoosed);
    }

    public Player chooseTargetingPlayer() {
        int i = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Insert the player you want to attack: \n");
        for(Player samplePlayer : match.getAllPlayers()) {
            System.out.println(i + ". " + samplePlayer.getClientName());
        }
        int numberPlayer = Integer.parseInt(input.nextLine());
        return match.getAllPlayers().get(numberPlayer);
    }

    public int chooseNewPosition() {
        int i = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the new position");
        for (Square sampleSquare : match.getMap().getAllSquare()) {
            System.out.println(i + ". " + sampleSquare.toString());
            i++;
        }
        return match.getMap().getAllSquare()[Integer.parseInt(input.nextLine())].getPosition();
    }

//    public void usePowerUpController(Player player) {
//        PowerUp powerUpToUse = choosePowerUp(player);
//        if (powerUpToUse.getType().equals("targeting scope")) {
//            Cubes colorCube = askCubeToPay(player);
//        }
//        player.usePowerUp(powerUpToUse);
//    }
}