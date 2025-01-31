package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.controller.ShotController;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.map.WeaponSlot;
import org.json.simple.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the player itself. Each player is identified by its clientName
 * It has some attributes such as character, position, score...
 * Each player has a reference to its playerboard, hand and match.
 * @author mattiamassarini, merklind, thisisferra.
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
    private int numberOfAction;
    private ShotController shotController;
    private HashSet<Player> hitThisTurnPlayers = new HashSet<>();
    private HashSet<Player> lastDamagingPlayers = new HashSet<>();
    private int finalFrenzy;
    private int phaseAction;
    private boolean canMove;
    private boolean playerDead;
    private int typePlayerBoard;
    private boolean firstSpawn;

    private transient Logger logger = Logger.getAnonymousLogger();

    public Player(String clientName, Match match) {
        this.clientName = clientName;
        this.match = match;
        this.character = chooseCharacter();
        this.suspended = false;
        this.score = 0;
        this.firstPlayer = false;
        this.playerBoard = new PlayerBoard(this);
        this.playerHand = new Hand();
        this.position = -1;
        this.numberOfAction=2;
        this.playerDead = false;
        this.canMove = false;
        this.finalFrenzy = 0;       //It will be 0 if final frenzy is disabled, 1 if player will have 1 action, 2 if player will have 2 actions
        this.phaseAction = 0;
        this.typePlayerBoard = 0;
        this.firstSpawn = true;
    }

    public Player(Match resumedMatch) {
        this.match = resumedMatch;
    }

    /**
     * Resume the a player from a JSONObject object.
     * @param playerToResume the player to be restored.
     * @param resumedMatch the match in which the player is.
     * @return the Player object restored.
     */
    public static Player resumePlayer(JSONObject playerToResume, Match resumedMatch) {
        Player resumedPlayer = new Player(resumedMatch);

        resumedPlayer.character = (String) playerToResume.get("character");
        resumedPlayer.clientName = (String) playerToResume.get("clientName");
        resumedPlayer.color = (String) playerToResume.get("color");
        resumedPlayer.position = ((Number) playerToResume.get("position")).intValue();
        resumedPlayer.score = ((Number) playerToResume.get("score")).intValue();
        resumedPlayer.firstPlayer = (boolean) playerToResume.get("firstPlayer");
        resumedPlayer.suspended = (boolean) playerToResume.get("suspended");
        resumedPlayer.playerHand = Hand.resumeHand((JSONObject) playerToResume.get("playerHand"));
        resumedPlayer.numberOfAction = ((Number) playerToResume.get("numberOfAction")).intValue();
        resumedPlayer.finalFrenzy = ((Number) playerToResume.get("finalFrenzy")).intValue();
        resumedPlayer.phaseAction = ((Number) playerToResume.get("phaseAction")).intValue();
        resumedPlayer.canMove = (boolean) playerToResume.get("canMove");
        resumedPlayer.playerDead = (boolean) playerToResume.get("playerDead");
        resumedPlayer.typePlayerBoard = ((Number) playerToResume.get("typePlayerBoard")).intValue();
        resumedPlayer.firstSpawn = (boolean) playerToResume.get("firstSpawn");

        return resumedPlayer;
    }

    /**
     * Getter method of the character field.
     * @return the name of the character assigned to the player.
     */
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
     * Setter of the phaseAction field
     * @param phaseAction the number of phase action the player is in.
     */
    public void setPhaseAction(int phaseAction){
        this.phaseAction=phaseAction;
    }

    /**
     * Getter of the phaseAction field.
     * @return an int indicating the phase action status the player is in.
     */
    public int getPhaseAction(){
        return this.phaseAction;
    }

    /**
     * Setter of the color field.
     * @param color the color assigned to the player. It relates to the character.
     */
    public void setColor(String color){
        this.color = color;
    }

    /**
     * Getter of the color field.
     * @return the color assigned to the player.
     */
    public String getColor(){
        return this.color;
    }

    /**
     * Getter of the final frenzy status.
     * It's 0 when the final frenzy hasn't started yet,
     * 1 if the final frenzy has started and the player has 1 move available
     * 2 if the final frenzy has started and the player has 2 moves available.
     *
     * @return the int representing the final frenzy status.
     */
    public int getFinalFrenzy(){
        return this.finalFrenzy;
    }

    /**
     * Setter of the final frenzy field.
     * @param finalFrenzy the finalfrenzy status the player is in.
     */
    public void setFinalFrenzy(int finalFrenzy){
        this.finalFrenzy=finalFrenzy;
    }

    /**
     * Getter of the canMove field.
     * @return if the player can use one of his action or not.
     */
    public boolean getCanMove(){
        return this.canMove;
    }

    /**
     * Setter of the firstPlayer field.
     * @param firstPlayer true if the player is the first player of the game, false otherwise.
     */
    public void setFirstPlayer(boolean firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    /**
     * Setter of the canMove field.
     * @param canMove true if the player can use one of his action, false otherwise.
     */
    public void setCanMove(boolean canMove){
        this.canMove=canMove;
    }

    /**
     * getter of the score of the player
     * @return the score of the player
     */
    public int getScore(){
        return this.score;
    }

    /**
     * setter of the score of the player.
     * Increase the score by the amount specified as parameter
     */
    public void setScore(int score) {
        this.score = this.score + score;
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

    public void reSetPlayerBoard(JSONObject playerBoardToResume, Match resumedMatch) {
        this.playerBoard = PlayerBoard.resumePlayerBoard(playerBoardToResume, resumedMatch);
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
    public boolean getSuspended() {
        return this.suspended;
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
     * Getter of the hitThisTurnPlayers collection.
     * @return the collection of player the current player hit this turn.
     */
    public HashSet<Player> getHitThisTurnPlayers(){
        return this.hitThisTurnPlayers;
    }

    /**
     * It clears the hitThisTurnPlayer list.
     */
    public void clearHitThisTurnPlayers(){
        this.hitThisTurnPlayers.clear();
    }

    /**
     * Getter of the number of actions the player has.
     * @return the number of action left.
     */
    public int getNumberOfAction() {
        return this.numberOfAction;
    }

    /**
     * Decrease by one the numberOfAction field.
     */
    public void decreaseNumberOfAction() {
        this.numberOfAction = this.numberOfAction - 1;
    }

    /**
     * Increase by one the numberOfAction field.
     */
    public void increaseNumberOfAction(){
        this.numberOfAction = this.numberOfAction +1;
    }

    /**
     * Setter of the numberOfAction field.
     * @param numberOfAction the number of actions assigned.
     */
    public void setNumberOfAction(int numberOfAction) {
        this.numberOfAction = numberOfAction;
    }

    /**
     * Reset the number of action according to the game and player states.
     *
     */
    public void resetNumberOfAction() {
        if (this.getFinalFrenzy() == 0)
            this.numberOfAction = 2;
        if (this.getFinalFrenzy() == 1)
            this.numberOfAction = 1;
        if (this.getFinalFrenzy() == 2)
            this.numberOfAction = 2;
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
                logger.log(Level.INFO,"You have already picked up this ammo");
            }
        }
        else{
            logger.log(Level.INFO,"You are in a spawn point, you can't pick up an ammo");
        }
    }

    /**
     * It picks up a power up only if the current power up array's size is less than three.
     */
    public void pickUpPowerUp(){
        if(playerHand.getPowerUps().size()<3){
            PowerUp powerUp = match.pickUpPowerUp();
            playerHand.addPowerUp(powerUp);
        }
    }

    /**
     * It picks up a power up even if the current hand is three.
     * Used when discarding a power up for spawning.
     * @return the power up drawn.
     */
    public PowerUp pickUpPowerUpToRespawn() {
        PowerUp powerUp = match.pickUpPowerUp();
        playerHand.addPowerUp(powerUp);
        return powerUp;
    }

    /**
     * Pick up a weapon in case the player has less than 3 weapons, it removes from the arsenal the weapon
     * the player chose and add it to his hand. Additionally the player pay the right amount of cubes to buy the weapon.
     * @param indexToPickUp index of the weaponsSlot array the players pick up the weapon from.
     * @return true if the player can draw a weapon, false otherwise
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

    /**
     * Pick up a weapon in case the player has more than 3 weapons, it removes from the arsenal the weapon
     * the player chose and add it to his hand. The player pays the right amount of cubes to buy the weapon.
     * The player put a weapon of his choice back inside the same cabinet he picked up the chosen weapons from.
     * The returned weapon is loaded.
     * @param indexToPickUp index of the weaponsSlot array the players pick up the weapon from.
     * @param indexToSwitch index of the weapon to be returned.
     * @return true if the player can draw a weapon, false otherwise
     */
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
                            //Set the weapon i've just dropped to load
                            weaponSlot.getSlot()[indexToPickUp].setLoad(true);
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

    /**
     * Trade a power up with a cube of the same color.
     */
    public void tradeCube(int index){
        PowerUp powerUp = this.playerHand.chooseToDiscard(index);
        Cubes cubeObtained;
        switch(powerUp.getColor()) {
            case "red": {
                cubeObtained = new Cubes(1, 0, 0);
                this.match.getDiscardedPowerUps().add(powerUp);
                break;
            }
            case "blue": {
                cubeObtained = new Cubes(0, 0, 1);
                this.match.getDiscardedPowerUps().add(powerUp);
                break;

            }
            case "yellow": {
                cubeObtained = new Cubes(0, 1, 0);
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
     * Check how many damages a player has got. If he has 11 or 12 damages he is dead
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
     * Add points to the current points.
     * @param pointsGained is the score the player has gained
     */
    public void addPoints(int pointsGained){
        this.score = this.score + pointsGained;
    }

    @Override
    public String toString(){
        return (this.clientName + " - " + this.character + " - " + this.getPosition() + " - " + this.getScore());
    }

    /**
     * Getter method of the match.
     * @return a reference to the current match
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

    /**
     * It finds the list of weapons the player can actually reload, taking into account the
     * number of cubes the player has, the number of cubes needed in order to pay the reload cost
     * and if the weapon is loaded or not.
     * @return a subset of weapons that the player has and that can be reloaded.
     */
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

    /**
     * Getter of the playerdead status.
     * @return if the player is currently dead or not.
     */
    public boolean getPlayerDead() {
        return this.playerDead;
    }

    /**
     * Setter of the playerDead field.
     * @param playerDead true if the player died, false otherwise.
     */
    public void setPlayerDead(boolean playerDead) {
        this.playerDead = playerDead;
    }

    /**
     * It saves the status of the player into a JSONObject object.
     * @return the JSONObject containing all the information in order to resume the player.
     */
    public JSONObject toJSON() {
        JSONObject playerJson = new JSONObject();

        playerJson.put("character", this.getCharacter());
        playerJson.put("clientName", this.getClientName());
        playerJson.put("color", this.getColor());
        playerJson.put("position", this.getPosition());
        playerJson.put("score", this.getScore());
        playerJson.put("firstPlayer", this.isFirstPlayer());
        playerJson.put("suspended", false);  //For resume game, all players have to reconnect --> not suspended

        playerJson.put("playerHand", this.getHand().toJSON());
        playerJson.put("playerBoard", this.getPlayerBoard().toJSON());

        playerJson.put("numberOfAction", this.getNumberOfAction());
        playerJson.put("finalFrenzy", this.getFinalFrenzy());
        playerJson.put("phaseAction", this.getPhaseAction());
        playerJson.put("canMove", this.getCanMove());
        playerJson.put("playerDead", this.getPlayerDead());
        playerJson.put("typePlayerBoard", this.getTypePlayerBoard());
        playerJson.put("firstSpawn", this.getFirstSpawn());


        return playerJson;
    }

    /**
     * Setter of the suspended field.
     * @param suspended true if the player is suspended, false otherwise.
     */
    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    /**
     * Getter method of the typePlayerBoard field.
     * @return the number type of the playerboard. 0 is the normal type, 1 the final frenzy type.
     */
    public int getTypePlayerBoard() {
        return this.typePlayerBoard;
    }

    /**
     * Method called to setUp the final frenzy boards, according to the rules.
     * @param typePlayerBoard the number type of the playerboard. 0 is the normal type, 1 the final frenzy type.
     */
    public void setTypePlayerBoard(int typePlayerBoard) {
        this.getPlayerBoard().setFinalFrenzyPointDeaths();
        this.getPlayerBoard().resetDeaths();
        this.typePlayerBoard = typePlayerBoard;

    }

    /**
     * Getter method of the firstSpawn field.
     * @return if the player is spawning for the first time or not.
     */
    public boolean getFirstSpawn() {
        return this.firstSpawn;
    }

    /**
     * Setter method of the firstSpawn field.
     * @param firstSpawn true if the player hasn't spawned yet, false otherwise.
     */
    public void setFirstSpawn(boolean firstSpawn) {
        this.firstSpawn = firstSpawn;
    }

    /**
     * Getter of the lastDamagingPlayer list
     * @return the player collection containing all the player who attacked you from the player last turn to the current.
     */
    public HashSet<Player> getLastDamagingPlayers() {
        return this.lastDamagingPlayers;
    }

}