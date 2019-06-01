package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class InfoShot {

    Weapon weapon;
    String nameEffect;
    int indexAbility;
    private Player damagingPlayer;
    private ArrayList<Player> targetablePlayer = new ArrayList<>();      //Other players I can view from my position
    private ArrayList<Player> targetPlayers = new ArrayList<>();        //ArrayLists of player i want to damage
    private ArrayList<Player> alreadyTarget = new ArrayList<>();        //Player that in my round I've already hit
    String cardinalDirection;                                           //String that represent the cardinal direction in effects that pull/push enemy or player have to move
    int numberOfMoves;                                                  //Integer that represents th number of movement
    int newPosition;

    public InfoShot(Weapon weapon, int indexAbility) {
        this.weapon = weapon;
        this.indexAbility = indexAbility;
        setnameEffect();

    }

    public String getNameEffect() {
        return nameEffect;
    }

    public Player getDamagingPlayer(){
        return this.damagingPlayer;
    }

    public ArrayList<Player> getTargetPlayer() {
        return this.targetPlayers;
    }

    public void setnameEffect() {
        this.nameEffect = weapon.getType() + " ability" + indexAbility;
    }

    public int getIndexAbility() {
        return this.indexAbility;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setNameEffect(String nameEffect) {
        this.nameEffect = nameEffect;
    }

    public void setIndexAbility(int indexAbility) {
        this.indexAbility = indexAbility;
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

    public void setAlreadyTarget(ArrayList<Player> alreadyTarget) {
        this.alreadyTarget = alreadyTarget;
    }

    public void setCardinalDirection(String cardinalDirection) {
        this.cardinalDirection = cardinalDirection;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public ArrayList<Player> getTargetablePlayer() {
        return targetablePlayer;
    }

    public ArrayList<Player> getTargetPlayers() {
        return targetPlayers;
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
        return newPosition;
    }
}
