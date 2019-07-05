package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * WeaponShot is the object of support for shoot an enemy. It contains all the useful information like
 * the weapon the player want use, an ArrayList of targetable player,...
 * @author mattiamassarini, merklind, thisisferra
 */
public class WeaponShot implements Serializable {

    private Weapon weapon;
    private String nameEffect;
    private Player damagingPlayer;
    private ArrayList<Player> targetablePlayer = new ArrayList<>();      //Other players I can view from my position
    private ArrayList<Player> targetPlayers = new ArrayList<>();        //ArrayLists of player i want to damage
    private ArrayList<Player> alreadyTarget = new ArrayList<>();        //Player that in my round I've already hit
    private ArrayList<Player> northTargets = new ArrayList<>();
    private ArrayList<Player> eastTargets = new ArrayList<>();
    private ArrayList<Player> southTargets = new ArrayList<>();
    private ArrayList<Player> westTargets = new ArrayList<>();
    private ArrayList<Square> squares= new ArrayList<>();
    private String cardinalDirection;                                           //String that represent the cardinal direction in effects that pull/push enemy or player have to move
    private int numberOfMoves;                                                  //Integer that represents th number of movement
    private int newPosition;
    private Shot chosenEffect;

    /**
     * Instantiates a new Weapon shot.
     *
     * @param weapon     the weapon
     * @param nameEffect the name effect
     */
    public WeaponShot(Weapon weapon, String nameEffect) {
        this.weapon = weapon;
        setNameEffect(nameEffect);
    }

    /**
     * Instantiates a new Weapon shot.
     */
    public WeaponShot() {

    }

    /* * *   GETTERS   * * */

    /**
     * Gets weapon.
     *
     * @return the weapon
     */
    public Weapon getWeapon() {
        return this.weapon;
    }

    /**
     * Gets name effect.
     *
     * @return the name effect
     */
    public String getNameEffect() {
        return this.nameEffect;
    }

    /**
     * Gets damaging player.
     *
     * @return the damaging player
     */
    public Player getDamagingPlayer() {
        return this.damagingPlayer;
    }

    /**
     * Gets targetable player.
     *
     * @return the targetable player
     */
    public ArrayList<Player> getTargetablePlayer() {
        return this.targetablePlayer;
    }

    /**
     * Gets target player.
     *
     * @return the target player
     */
    public ArrayList<Player> getTargetPlayer() {
        return this.targetPlayers;
    }

    /**
     * Gets already target.
     *
     * @return the already target
     */
    public ArrayList<Player> getAlreadyTarget() {
        return alreadyTarget;
    }

    /**
     * Gets cardinal direction.
     *
     * @return the cardinal direction
     */
    public String getCardinalDirection() {
        return cardinalDirection;
    }

    /**
     * Gets number of moves.
     *
     * @return the number of moves
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    /**
     * Gets new position.
     *
     * @return the new position
     */
    public int getNewPosition() {
        return this.newPosition;
    }

    /**
     * Get chosen effect shot.
     *
     * @return the shot
     */
    public Shot getChosenEffect(){
        return  this.chosenEffect;
    }

    /**
     * Gets squares.
     *
     * @return the squares
     */
    public ArrayList<Square> getSquares() {
        return this.squares;
    }

    /**
     * Get north targets array list.
     *
     * @return the array list
     */
    public ArrayList<Player> getNorthTargets(){
        return this.northTargets;
    }

    /**
     * Get east targets array list.
     *
     * @return the array list
     */
    public ArrayList<Player> getEastTargets(){
        return this.eastTargets;
    }

    /**
     * Get south targets array list.
     *
     * @return the array list
     */
    public ArrayList<Player> getSouthTargets(){
        return this.southTargets;
    }

    /**
     * Get west targets array list.
     *
     * @return the array list
     */
    public ArrayList<Player> getWestTargets(){
        return this.westTargets;
    }

    /* * *   SETTERS   * * */

    /**
     * Sets weapon.
     *
     * @param weapon the weapon
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Sets name effect.
     *
     * @param nameEffect the name effect
     */
    public void setNameEffect(String nameEffect) {
        this.nameEffect = nameEffect;
    }

    /**
     * Sets damaging player.
     *
     * @param damagingPlayer the damaging player
     */
    public void setDamagingPlayer(Player damagingPlayer) {
        this.damagingPlayer = damagingPlayer;
    }

    /**
     * Sets targetable player.
     *
     * @param targetablePlayer the targetable player
     */
    public void setTargetablePlayer(ArrayList<Player> targetablePlayer) {
        this.targetablePlayer = targetablePlayer;
    }

    /**
     * Sets target players.
     *
     * @param targetPlayers the target players
     */
    public void setTargetPlayers(ArrayList<Player> targetPlayers) {
        this.targetPlayers = targetPlayers;
    }

    /**
     * Sets cardinal direction.
     *
     * @param cardinalDirection the cardinal direction
     */
    public void setCardinalDirection(String cardinalDirection) {
        this.cardinalDirection = cardinalDirection;
    }

    /**
     * Sets new position.
     *
     * @param newPosition the new position
     */
    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    /**
     * Set chosen effect.
     *
     * @param chosenEffect the chosen effect
     */
    public void setChosenEffect(Shot chosenEffect){
        this.chosenEffect = chosenEffect;
    }

    @Override
    public String toString(){
        return "" + this.getTargetablePlayer();
    }

    /*
    public JSONObject toJSON() {
        JSONObject infoShotJson = new JSONObject();

        infoShotJson.put("weapon", this.getWeapon().getType());
        infoShotJson.put("nameEffect", this.getNameEffect());
        infoShotJson.put("damagingPlayer", this.getDamagingPlayer().toJSON());

        JSONArray targetablePlayerJson = new JSONArray();
        for (Player player : this.getTargetablePlayer()) {
            targetablePlayerJson.add(player.toJSON());
        }
        infoShotJson.put("targetablePlayer", targetablePlayerJson);

        JSONArray targetPlayersJson = new JSONArray();
        for (Player player : this.getTargetPlayer()) {
            targetPlayersJson.add(player.toJSON());
        }
        infoShotJson.put("targetPlayers", targetPlayersJson);

        JSONArray alreadyTargetJson = new JSONArray();
        for (Player player : this.getAlreadyTarget()) {
            alreadyTargetJson.add(player.toJSON());
        }
        infoShotJson.put("alreadyTarget", alreadyTargetJson);

        infoShotJson.put("cardinalDirection", this.getCardinalDirection());
        infoShotJson.put("numberOfMoves", this.getNumberOfMoves());
        infoShotJson.put("newPosition", this.getNewPosition());

        return infoShotJson;
    }
     */

}
