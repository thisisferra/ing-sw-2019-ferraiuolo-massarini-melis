package it.polimi.se2019.model.cards;

import it.polimi.se2019.model.game.Cubes;

public class Ammo {

    private Cubes ammoCubes;
    private boolean powerUpCard;

    public Ammo getAmmo(){
        return this;
    }
    public void setAmmo(Cubes ammoCubes, boolean powerUpCard){
        this.ammoCubes = ammoCubes;
        this.powerUpCard=powerUpCard;
    }
    public Ammo(Cubes ammoCubes,boolean powerUpCard){
        this.ammoCubes = ammoCubes;
        this.powerUpCard=powerUpCard;
    }
}