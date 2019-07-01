package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;
import org.json.simple.JSONObject;

import java.io.Serializable;

/*Ammo class represent the real counterpart ammo tile from the board game.
* When a player picks it up from the terrain he receives some cubes and if the
* attribute powerUpCard is true, he draws a powerUp card from the powerUp deck.
* Setters are omitted, since these cards are generated once, at the start of the game.*/

public class Ammo implements Serializable {

    private Cubes ammoCubes;
    private boolean powerUpCard;

    //Constructor of Ammo object
    public Ammo(Cubes ammoCubes,boolean powerUpCard){
        this.ammoCubes = ammoCubes;
        this.powerUpCard=powerUpCard;
    }

    public Ammo() {
        //Needed for resuming an ammo from saved match
    }

    public Cubes getAmmoCubes(){
        return this.ammoCubes;
    }

    public boolean getPowerUpCard() {
        return this.powerUpCard;
    }

    public String toString(){
        return this.ammoCubes.toString() +"\nPower Up: " + this.powerUpCard;
    }

    public JSONObject toJSON() {
        JSONObject ammoJson = new JSONObject();

        ammoJson.put("ammoCubes", this.getAmmoCubes().toJSON());
        ammoJson.put("powerUpCard", this.getPowerUpCard());

        return ammoJson;
    }

    public static Ammo resumeAmmo(JSONObject ammoToResume) {
        Ammo resumedAmmo = new Ammo();

        resumedAmmo.ammoCubes = Cubes.resumeCubes((JSONObject) ammoToResume.get("ammoCubes"));
        resumedAmmo.powerUpCard = (boolean) ammoToResume.get("powerUpCard");

        return resumedAmmo;
    }
}