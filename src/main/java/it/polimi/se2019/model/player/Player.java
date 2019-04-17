package it.polimi.se2019.model.player;

import it.polimi.se2019.model.cards.Ammo;
import it.polimi.se2019.model.cards.PowerUp;
import it.polimi.se2019.model.game.Cubes;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;

import java.util.ArrayList;

public class Player {
    private String clientName;
    private int position;
    private int score;
    private String color;
    private PlayerBoard playerBoard;
    private boolean firstPlayer;
    private boolean suspended;
    private Hand playerHand;


    public String getClientName(){
        return this.clientName;
    }

    public String getColor(){
        return this.color;
    }

    public int getScore(){
        return this.score;
    }

    public int getPosition(){
        return this.position;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setClientName(String clientName){
        this.clientName= clientName;
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

}