package it.polimi.se2019.model.player;

import it.polimi.se2019.model.cards.Ammo;
import it.polimi.se2019.model.cards.PowerUp;
import it.polimi.se2019.model.game.Cubes;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.map.Square;

public class Player {
    private String clientName;
    private String color;
    private int position;
    private int score;
    private boolean firstPlayer;
    private boolean suspended;
    private Hand playerHand;
    private PlayerBoard playerBoard;
    private Match match;

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
                System.out.println("Violet!" );
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
    public void initPlayer(){

    }

    //return player name
    public String getClientName(){
        return this.clientName;
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

    //trade the current powerup in a cube of the matching color as a Cubes object
    public void tradeCube(){
        PowerUp powerUp = this.playerHand.chooseToDiscard();
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

}