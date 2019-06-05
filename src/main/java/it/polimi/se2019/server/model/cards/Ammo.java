package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;

import java.io.Serializable;

/*Ammo class represent the real counterpart ammo tile from the board game.
* When a player picks it up from the terrain he receives some cubes and if the
* attribute powerUpCard is true, he draws a PowerUp card from the PowerUp deck.
* Setters are omitted, since these cards are generated once, at the start of the game.*/

public class Ammo implements Serializable {

    private Cubes ammoCubes;
    private boolean powerUpCard;

    //Constructor of Ammo object
    public Ammo(Cubes ammoCubes,boolean powerUpCard){
        this.ammoCubes = ammoCubes;
        this.powerUpCard=powerUpCard;
    }

    public Cubes getAmmoCubes(){
        return this.ammoCubes;
    }

    public boolean getPowerUpCard() {
        return this.powerUpCard;
    }

    public String toString(){
        return this.ammoCubes.toString() +" PUC: " + this.powerUpCard;
    }
}