package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.player.PlayerBoard;

import java.io.Serializable;

/**
 * Support class where we save useful info for use the PowerUp.
 * In this class we save information like the damaging player, tha targeting player, the new position
 * of the targeting or damaging player
 * @author marcomelis, thisisferra, mattiamassarini
 */
public class PowerUpShot implements Serializable {

    private Player damagingPlayer;
    private Player targetingPlayer;
    private int newPosition;
    private Cubes cubeToPay;

    /* * *   GETTER   * * */

    /**
     * Retrieve the damagingPlayer attribute of the class
     * @return the player who give the damage
     */
    public Player getDamagingPlayer() {
        return this.damagingPlayer;
    }

    /**
     * Getter of targetingPlayer
     * @return targetingPlayer attribute
     */
    public Player getTargetingPlayer() {
        return this.targetingPlayer;
    }

    /**
     * Getter of the new position of the player who uses the powerUp
     * @return newPosition attribute
     */
    public int getNewPosition() {
        return this.newPosition;
    }

    /* * *   SETTER   * * */

    /**
     * Setter of damagingPlayer
     * @param damagingPlayer: the player who gives the damage
     */
    public void setDamagingPlayer(Player damagingPlayer) {
        this.damagingPlayer = damagingPlayer;
    }

    /**
     * Setter of targetingPlayer attribute
     * @param targetingPlayer: the player who receives damage
     */
    public void setTargetingPlayer(Player targetingPlayer) {
        this.targetingPlayer = targetingPlayer;

    }

    /**
     * Setter of newPosition attribute
     * @param newPosition: the new position of the targeting player or damagingPlayer
     */
    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    /**
     * Setter of cubeToPay attribute
     * @param cubeToPay: cubes the player want to pay
     */
    public void setCubeToPay(Cubes cubeToPay) {
        this.cubeToPay = cubeToPay;
    }

    /* * * OTHERS   * * */

    @Override
    public String toString(){
        return " "+this.targetingPlayer + "";
    }
}
