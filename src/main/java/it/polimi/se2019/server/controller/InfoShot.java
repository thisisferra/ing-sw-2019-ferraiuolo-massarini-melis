package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.cards.Weapon;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public class InfoShot {

    Weapon weapon;
    String nameEffect;                                                 //Boolean that indicates if the player want to use basic ability of the weapon (always true)
    int indexAbility;
    private Player damagingPlayer;
    private ArrayList<Player> targetablePlayer = new ArrayList<>();      //Other players I can view from my position
    private ArrayList<Player> targetPlayers = new ArrayList<>();        //ArrayLists of player i want to damage
    private ArrayList<Player> alreadyTarget = new ArrayList<>();        //Player that in my round I've already hit
    String cradinalDirection;                                           //String that represent the cardinal direction in effects that pull/push enemy or player have to move
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




}
