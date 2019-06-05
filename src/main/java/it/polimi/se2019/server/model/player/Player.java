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

/**
 * Represents the player game. Each player is identified by its clientName
 * It has some attributes such as color, position, score...
 * Each player has a reference to its playerboard, hand and match
 */


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

    public Player(String clientName, String color, Match match) {
        this.clientName = clientName;
        this.color = color;
        this.match = match;
        this.suspended = false;
        this.score = 0;
        this.firstPlayer = false;
        this.playerBoard = new PlayerBoard(this);
        this.playerHand = new Hand();
    }

    /**
     * Getter of the client name
     * @return the client name
     */
    public String getClientName(){
        return this.clientName;
    }

    /**
     * Setter of the client name. It's used in the constructor of the object
     * @param clientName the nickname of the player
     */
    public void setClientName(String clientName){
        this.clientName= clientName;
    }

    /**
     * Getter of the color's player
     * @return the color choosed by the player at the start of the game
     */
    public String getColor(){
        return this.color;
    }

    /**
     * getter of the score of the player
     * @return the score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * getter of the position of the player
     * @return the position (integer) of the player
     */
    public int getPosition(){
        return this.position;
    }

    /**
     * reference to {@link Hand} of this player
     * @return the hand of this player
     */
    public Hand getHand(){
        return this.playerHand;
    }

    /**
     * Reference to {@link PlayerBoard} of this player
     * @return The playerboard of this player
     */
    public PlayerBoard getPlayerBoard(){
        return this.playerBoard;
    }

    /**
     * Specify if the player is the first who has played in the match
     * @return true if the player is the first who has played, false otherwise
     */
    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Specify if the player has disconnected from the match
     * @return true if the player has disconnected, false otherwise
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * Setter of the position of the player.
     * It is used in the constructor of the object
     * @param position the new position of the player
     */
    public void setPosition(int position){
        this.position = position;
    }

    /**
     * Modify the {@link Hand} of the player adding cubes and, if necessary, a powerup.
     * If the player already has got three power-up nothing happens.
     * @param currentSquare the current square of the player
     * @param currentMatch the match
     */
    public void pickUpAmmo(Square currentSquare, Match currentMatch) {
        System.out.println(playerHand.getPowerUps().size());
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
                System.out.println(playerHand.getPowerUps().size());
            }
            else {
                System.out.println("You have already picked up this ammo");
            }
        }
        else{
            System.out.println("You are in a spawn point, you can't pick up an ammo");
        }
    }

    /**
     * Switch a power-up with the cubes specified by the power-up
     */
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

    /**
     * Check how many damages a player has got. If it has 11 or 12 damages it is dead
     * @return true if the player has got 11 or 12 damages, false otherwise.
     */
    public boolean checkDeath() {
        if (this.playerBoard.getDamage().size() == 11 || this.playerBoard.getDamage().size() == 12) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Set the new score of the player
     * @param pointsGained is the score the player has gained
     */
    public void addPoints(int pointsGained){
        this.score = this.score + pointsGained;
    }

    @Override
    public String toString(){
        return (this.clientName + " " + this.color + " " + this.getPosition());
    }

    /**
     * getter of the match
     * @return a reference to thc current match
     */
    public Match getMatch() {
        return this.match;
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