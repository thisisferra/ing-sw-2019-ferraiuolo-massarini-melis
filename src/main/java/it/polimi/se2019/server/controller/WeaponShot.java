package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

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

    public WeaponShot(Weapon weapon, String nameEffect) {
        this.weapon = weapon;
        setNameEffect(nameEffect);
    }

    public WeaponShot() {

    }

    /* * *   GETTERS   * * */

    public Weapon getWeapon() {
        return this.weapon;
    }

    public String getNameEffect() {
        return this.nameEffect;
    }

    public Player getDamagingPlayer() {
        return this.damagingPlayer;
    }

    public ArrayList<Player> getTargetablePlayer() {
        return this.targetablePlayer;
    }

    public ArrayList<Player> getTargetPlayer() {
        return this.targetPlayers;
    }

    public ArrayList<Player> getAlreadyTarget() {
        return alreadyTarget;
    }

    public String getCardinalDirection() {
        return cardinalDirection;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getNewPosition() {
        return this.newPosition;
    }

    public Shot getChosenEffect(){
        return  this.chosenEffect;
    }

    public ArrayList<Square> getSquares() {
        return this.squares;
    }

    public ArrayList<Player> getNorthTargets(){
        return this.northTargets;
    }

    public ArrayList<Player> getEastTargets(){
        return this.eastTargets;
    }

    public ArrayList<Player> getSouthTargets(){
        return this.southTargets;
    }

    public ArrayList<Player> getWestTargets(){
        return this.westTargets;
    }

    /* * *   SETTERS   * * */

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setNameEffect(String nameEffect) {
        this.nameEffect = nameEffect;
    }

    public void setDamagingPlayer(Player damagingPlayer) {
        this.damagingPlayer = damagingPlayer;
    }

    public void setTargetablePlayer(ArrayList<Player> targetablePlayer) {
        this.targetablePlayer = targetablePlayer;
    }

    public void setTargetPlayers(ArrayList<Player> targetPlayers) {
        this.targetPlayers = targetPlayers;
    }

    public void setCardinalDirection(String cardinalDirection) {
        this.cardinalDirection = cardinalDirection;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

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


    /* * *   OTHERS   * * */

}
