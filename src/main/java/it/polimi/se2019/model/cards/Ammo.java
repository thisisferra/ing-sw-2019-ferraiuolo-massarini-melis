package it.polimi.se2019.model.cards;

import it.polimi.se2019.model.game.Cubes;

/*Ammo class represent the real counterpart ammo tile from the board game.
* When a player picks it up from the terrain he receives some cubes and if the
* attribute powerUpCard is true, he draws a PowerUp card from the PowerUp deck.
* Setters are omitted, since these cards are generated once, at the start of the game.*/

public class Ammo {

    private Cubes ammoCubes;
    private boolean powerUpCard;

    public Cubes getAmmo(){
        return this.ammoCubes;
    }

    public Ammo(Cubes ammoCubes,boolean powerUpCard){
        this.ammoCubes = ammoCubes;
        this.powerUpCard=powerUpCard;
    }
}