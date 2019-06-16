package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.controller.ShotController;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.map.WeaponSlot;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents the player game. Each player is identified by its clientName
 * It has some attributes such as color, position, score...
 * Each player has a reference to its playerboard, hand and match
 */


public class Player implements Serializable {
    private String character;
    private String clientName;
    private String color;
    private int position;
    private int score;
    private boolean firstPlayer;
    private boolean suspended;
    private Hand playerHand;
    private PlayerBoard playerBoard;
    private Match match;
    private int numberOfAction = 2;
    private ShotController shotController;

    public Player(String clientName, Match match) {
        this.clientName = clientName;
        this.match = match;
        this.character = chooseCharacter();
        this.suspended = false;
        this.score = 0;
        this.firstPlayer = false;
        this.playerBoard = new PlayerBoard(this);
        this.playerHand = new Hand();
        this.shotController = new ShotController(match);
        this.position = -1;
    }

    public String getCharacter() {
        return this.character;
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

    public int getNumberOfAction() {
        return this.numberOfAction;
    }

    public void setNumberOfAction() {
        this.numberOfAction = this.numberOfAction - 1;
    }

    public void resetNumberOfAction() {
        this.numberOfAction = 2;
    }

    public ShotController getShotController() {
        return this.shotController;
    }

    /**
     * Modify the {@link Hand} of the player adding cubes and, if necessary, a powerup.
     * If the player already has got three power-up nothing happens.
     * @param currentSquare the current square of the player
     * @param currentMatch the match
     */
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
                if(currentAmmo.getPowerUpCard() && this.playerHand.getPowerUps().size() < 3) {
                    //Add a power up to player's hand
                    playerHand.addPowerUp(currentMatch.pickUpPowerUp());
                }
                //Create an object cubes that contains the cubes in the ammo that I've picked up
                Cubes currentCubes = currentAmmo.getAmmoCubes();
                //Change the value of the cubes I own
                playerBoard.setAmmoCubes(currentCubes);
            }
            else {
                System.out.println("You have already picked up this ammo");
            }
        }
        else{
            System.out.println("You are in a spawn point, you can't pick up an ammo");
        }
    }

    public void pickUpPowerUp(){
        if(playerHand.getPowerUps().size()<3){
            PowerUp powerUp = match.pickUpPowerUp();
            playerHand.addPowerUp(powerUp);
        }
    }

    /**
     * Pick up a weapon in case i have less then 3 weapons, it deletes from the right arsenal the weapon
     * I chose and add it to my hand. In the method I also pay the right amount of cubes to buy the weapon.
     * @param indexToPickUp index of the weapon slot where I pick uop the weapon
     * @return true if I can draw a weapon, false otherwise
     */
    public boolean pickUpWeapon(int indexToPickUp) {
        if (this.getHand().getWeapons().size() < 3) {
            //Check if the player is in a spawn point
            Square currentSquare = match.getMap().searchSquare(this.getPosition());
            if (currentSquare.isSpawnPoint()) {
                //Retrieve the color of the square/weapon-slot from which I draw the weapon
                String colorWeaponSlot = currentSquare.getColor();
                //Search the right color weapon slot
                for (WeaponSlot weaponSlot : match.getArsenal()) {
                    if (weaponSlot.getCabinetColor().equals(colorWeaponSlot)) {
                        //Retrieve the cubes cost of the weapon I choosed
                        Cubes cubesToPay = new Cubes(
                                weaponSlot.getSlot()[indexToPickUp].getBuyingCost().getReds(),
                                weaponSlot.getSlot()[indexToPickUp].getBuyingCost().getYellows(),
                                weaponSlot.getSlot()[indexToPickUp].getBuyingCost().getBlues());
                        //Check if I can pay the weapon I choosed
                        if (
                                cubesToPay.getReds() <= this.getPlayerBoard().getAmmoCubes().getReds() &&
                                        cubesToPay.getYellows() <= this.getPlayerBoard().getAmmoCubes().getYellows() &&
                                        cubesToPay.getBlues() <= this.getPlayerBoard().getAmmoCubes().getBlues()
                        ) {
                            //Add the weapon to my hand
                            this.getHand().getWeapons().add(weaponSlot.getSlot()[indexToPickUp]);
                            //Delete from the cabinet the weapon I choosed to draw
                            weaponSlot.getSlot()[indexToPickUp] = null;
                            //Sub buying cubes from hand
                            this.getPlayerBoard().setDeltaAmmoCubes(new Cubes(this.getPlayerBoard().getAmmoCubes().getReds()-cubesToPay.getReds(),
                                    this.getPlayerBoard().getAmmoCubes().getYellows()-cubesToPay.getYellows(),
                                    this.getPlayerBoard().getAmmoCubes().getBlues()-cubesToPay.getBlues()));
                        } else {
                            return false;
                        }
                    }
                }

                return true;
            }
            //The player isn't in a spawn point and can't pick up a weapon
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public boolean pickUpWeapon(int indexToPickUp, int indexToSwitch) {
        //Check if the player has already three weapons in his hand
        if (this.getHand().getWeapons().size() == 3) {
            //Retrieve the square of the player
            Square currentSquare = match.getMap().getSpecificSquare(this.getPosition());
            //Check if the player square is a spawn point
            if (currentSquare.isSpawnPoint()) {
                //Retrieve the color of the square/weapon-slot from which I draw the weapon
                String colorWeaponSlot = currentSquare.getColor();
                //Search the right color weapon slot
                for (WeaponSlot weaponSlot : match.getArsenal()) {
                    if (weaponSlot.getCabinetColor().equals(colorWeaponSlot)) {
                        //Retrieve the cubes cost of the weapon I chose
                        Cubes cubesToPay = new Cubes (
                                weaponSlot.getSlot()[indexToPickUp].getBuyingCost().getReds(),
                                weaponSlot.getSlot()[indexToPickUp].getBuyingCost().getYellows(),
                                weaponSlot.getSlot()[indexToPickUp].getBuyingCost().getBlues());
                        //Check if I can pay the weapon I chose
                        if (
                                cubesToPay.getReds() <= this.getPlayerBoard().getAmmoCubes().getReds() &&
                                        cubesToPay.getYellows() <= this.getPlayerBoard().getAmmoCubes().getYellows() &&
                                        cubesToPay.getBlues() <= this.getPlayerBoard().getAmmoCubes().getBlues()
                        ) {
                            //Load the weapon i want to switch
                            this.getHand().getWeapons().get(indexToSwitch).setLoad(true);
                            //Add to my hand the weapon i chose to pick up
                            this.getHand().getWeapons().add(weaponSlot.getSlot()[indexToPickUp]);
                            //Set the slot where i picked uop the weapon with the weapon I want to switch
                            weaponSlot.getSlot()[indexToPickUp] = this.getHand().getWeapons().get(indexToSwitch);
                            //Delete the weapon i switched from my hand
                            this.getHand().getWeapons().remove(indexToSwitch);
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
        return true;
    }

    //TODO nel caso in cui abbia già 3 armi
    //TODO pickupWeapon()

    /**
     * Switch a power-up with the cubes specified by the power-up
     */
    public void tradeCube(int index){
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
        return (this.clientName + " - " + this.character + " - " + this.getPosition());
    }

    /**
     * getter of the match
     * @return a reference to thc current match
     */
    public Match getMatch() {
        return this.match;
    }

    public String chooseCharacter() {
        double randomNumber = (int) (Math.random() * this.getMatch().getCharacterAvailable().size());
        int intRandomNumber = (int) randomNumber;
        return this.match.getCharacterAvailable().remove(intRandomNumber);
    }


    //return the list of weapons the player owns if he has enough cubes for each of them and if the weapons
    // is unloaded.
    // (E.G. if the player has 1 red cube and three weapons whose reload cost is 1 red cube each, this method will
    //  return all three weapons)
    public ArrayList<Weapon> getReloadableWeapons(){
        ArrayList<Weapon> reloadableWeapons = new ArrayList<>();
        for(Weapon weapon : this.getHand().getWeapons()){

            if(weapon.getReloadCost().getReds() <= this.playerBoard.getAmmoCubes().getReds()
                && weapon.getReloadCost().getYellows() <= this.playerBoard.getAmmoCubes().getYellows()
                    && weapon.getReloadCost().getBlues() <= this.playerBoard.getAmmoCubes().getBlues()
                        && !weapon.getLoad()){
                reloadableWeapons.add(weapon);

            }
        }
        return reloadableWeapons;
    }
}