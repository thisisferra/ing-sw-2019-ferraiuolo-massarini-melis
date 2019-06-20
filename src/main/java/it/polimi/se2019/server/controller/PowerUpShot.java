package it.polimi.se2019.server.controller;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.player.PlayerBoard;

import java.io.Serializable;

public class PowerUpShot implements Serializable {

    private Player damagingPlayer;
    private Player targetingPlayer;
    private int newPosition;
    private Cubes cubeToPay;

    /* * *   GETTER   * * */

    public Player getDamagingPlayer() {
        return this.damagingPlayer;
    }

    public Player getTargetingPlayer() {
        return this.targetingPlayer;
    }

    public int getNewPosition() {
        return this.newPosition;
    }

    public Cubes getCubeToPay() {
        return this.cubeToPay;
    }

    /* * *   SETTER   * * */

    public void setDamagingPlayer(Player damagingPlayer) {
        this.damagingPlayer = damagingPlayer;
    }

    public void setTargetingPlayer(Player targetingPlayer) {
        this.targetingPlayer = targetingPlayer;

    }

    public void setNewPosition(int newPosition) {
        this.newPosition = newPosition;
    }

    public void setCubeToPay(Cubes cubeToPay) {
        this.cubeToPay = cubeToPay;
    }

    /* * * OTHERS   * * */

    public void clearPowerUpShot() {
        this.targetingPlayer = null;
        this.newPosition = -1;
        this.cubeToPay = null;
    }

    @Override
    public String toString(){
        return " "+this.targetingPlayer + "";
    }
}
