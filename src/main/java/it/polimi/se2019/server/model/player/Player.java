package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.controller.Controller;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Player {
    private Controller controller;
    private String clientName;
    private String color;
    private int position;
    private int score;
    private boolean firstPlayer;
    private boolean suspended;
    private Hand playerHand;
    private PlayerBoard playerBoard;
    private Match match;
    private  ArrayList<EnemyDamage> enemyDamages = new ArrayList<>();

    public Player(String clientName, String color,Match match){
        this.clientName = clientName;
        this.color=color;
        this.match = match;
        this.suspended=false;
        this.score = 0;
        this.firstPlayer = false;
        System.out.print(this.clientName + " has picked ");
        switch(this.getColor()){
            case "yellow":{
                System.out.println(":D-struct-OR ...");
                break;
            }
            case "purple":{
                System.out.println("Violet!");
                break;
            }
            case "grey":{
                System.out.println("Dozer!");
                break;
            }
            case "green":{
                System.out.println("Sprog!");
                break;
            }
            case "blue":{
                System.out.println("Banshee!");
                break;
            }
            default:{
                System.out.println("something from the floor probably");
                break;
            }
        }

        this.playerBoard = new PlayerBoard();
        this.playerHand = new Hand();
    }

    public ArrayList<EnemyDamage> getEnemyDamages(){
        return this.enemyDamages;
    }

    //return player name
    public String getClientName(){
        return this.clientName;
    }

    public void setClientName(String clientName){
        this.clientName= clientName;
    }

    //return color figure,
    //N.B. it can be used to differentiate players since each player has a different color
    public String getColor(){
        return this.color;
    }

    //return player score
    //N.B. each score can be seen only by it's owner
    public int getScore(){
        return this.score;
    }

    public int getPosition(){
        return this.position;
    }

    public Hand getHand(){
        return this.playerHand;
    }

    public PlayerBoard getPlayerBoard(){
        return this.playerBoard;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public void pickUpAmmo(Square currentSquare, Match currentMatch) {
        //Check if the player is in an ammo square
        if(!(currentSquare.isSpawnPoint())) {
            //check if there is an ammo in the current square
            if(currentSquare.isFull()) {
                //Set the square to empty
                currentSquare.setFull(false);
                //Create an ammo object where i put the ammo I've picked up
                Ammo currentAmmo = currentMatch.pickUpAmmoStack();
                //Add the current ammo into the discardedAmmos list
                currentMatch.discardAmmo(currentAmmo);
                //If the ammo I've picked up has a power up -> pick a power up card from ArrayList
                if(currentAmmo.getPowerUpCard()) {
                    //Add a power up to player's hand
                    playerHand.addPowerUp(currentMatch.pickUpPowerUp());
                }
                //Create an object cubes that contains the cubes in the ammo that I've picked up
                Cubes currentCubes = currentAmmo.getAmmoCubes();
                //Change the value of the cubes I own
                playerBoard.setAmmoCubes(currentCubes);
            }
        }
        else{
            System.out.println("You are in a spawn point, you can't pick up an ammo");
        }
    }

    //trade the current powerup in a cube of the matching color as a Cubes object
    public void tradeCube(){
        int index = this.playerHand.indexToDiscard();
        PowerUp powerUp = this.playerHand.chooseToDiscard(index);
        Cubes cubeObtained;
        switch(powerUp.getColor()) {
            case "red": {
                cubeObtained = new Cubes(1, 0, 0);
                //powerup discarded goes into discardedPowerUps
                this.match.getDiscardedPowerUps().add(powerUp);
                break;
            }
            case "blue": {
                cubeObtained = new Cubes(1, 0, 1);
                //powerup discarded goes into discardedPowerUps
                this.match.getDiscardedPowerUps().add(powerUp);
                break;

            }
            case "yellow": {
                cubeObtained = new Cubes(0, 1, 0);
                //powerup discarded goes into discardedPowerUps
                this.match.getDiscardedPowerUps().add(powerUp);
                break;
            }
            default: {
                //if an error occurs, no cubes are returned and the powerup cards goes back
                //in playerHand
                cubeObtained = null;
                this.playerHand.addPowerUp(powerUp);
                break;
            }
        }
        //the cube is added to ammoCubes field in playerBoard
        this.playerBoard.setAmmoCubes(cubeObtained);
    }

    //Return true if the amount of damage is 11 or 12, false otherwise
    public boolean checkDeath() {
        if (this.playerBoard.getDamage().size() == 11 || this.playerBoard.getDamage().size() == 12) {
            return true;
        }
        else {
            return false;
        }
    }

    //Sort the enemyDamages ArrayList by damage amount in descendending order
    public void sortAggressor() {
        Collections.sort(enemyDamages, Comparator.comparingInt(EnemyDamage::getDamage).reversed());
    }

    //Gestisce l'assegnazione dei punteggi alla morte di un giocatore
    public void setScore() {
        int k = 0;
        int maximumNumPoint = this.getPlayerBoard().getPointDeaths().size();
        //Legge la struttura EnemyDamage
        for(EnemyDamage enDam : enemyDamages){
            //Per ogni giocatore che ha fatto danno al giocatore morto...
            Player player = enDam.getAggressorPlayer();
            //Incrementa lo score di ogni giocatore che ha colpito almeno una volta
            //il giocatore morto con la quantità di punti corrispondente
            if(k < maximumNumPoint) {
                player.score = player.score + this.playerBoard.getSpecificPointDeaths(k);
                k++;
            }
            else {
                player.score += 1;
            }

            //TODO da implementare l'assegnazione di un punto per il primo danno a un giocatore
        }
        //Elimina il primo elemento di pointDeaths del giocatore morto
        //per diminuire il massimo punteggio ottenibile alla pressima morte
        this.playerBoard.deleteFirstPointDeaths();

    }

    public String toString(){
        return (this.clientName + " " + this.color + " " + this.getPosition());
    }


    //Check if one player had already make damage to a specific player.
    public boolean checkDamage(Player damagingPlayer) {
        for(EnemyDamage singleEnemyDamage : enemyDamages) {
            if(damagingPlayer.equals(singleEnemyDamage.getAggressorPlayer()))
                return true;
        }
        return false;
    }

//    public void usePowerUp(PowerUp powerUpToUse) {
//        if (powerUpToUse.getType().equals("targeting scope")) {
//            Player targetingPlayer = controller.chooseTargetingPlayer();
//            powerUpToUse.effect(this, targetingPlayer);
//        }
//        if (powerUpToUse.getType().equals("newton")) {
//        }
//        if (powerUpToUse.getType().equals("tagback grenade")) {
//
//        }
//        if (powerUpToUse.getType().equals("teleporter")) {
//            int newPosition = controller.chooseNewPosition();
//            powerUpToUse.effect(this , newPosition);
//        }
//    }
}