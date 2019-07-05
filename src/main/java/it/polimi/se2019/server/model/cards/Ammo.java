package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**Ammo class represent the real counterpart ammo tile from the board game.
 * When a player picks it up from the terrain he receives some cubes and if the
 * attribute powerUpCard is true, he draws a powerUp card from the powerUp deck.
 * Setters are omitted, since these cards are generated once, at the start of the game.
 */

public class Ammo implements Serializable {

    private Cubes ammoCubes;
    private boolean powerUpCard;

    /**
     * Constructor of the Ammo object.
     * @param ammoCubes the Cubes object inside the ammo.
     * @param powerUpCard true if there is a power up, false otherwise.
     */
    public Ammo(Cubes ammoCubes,boolean powerUpCard){
        this.ammoCubes = ammoCubes;
        this.powerUpCard=powerUpCard;
    }

    /**
     * Constructor of the Ammo object without parameters.
     */
    public Ammo() {
        //Needed for resuming an ammo from saved match
    }

    /**
     * Getter of the ammoCubes field.
     * @return a Cube object.
     */
    public Cubes getAmmoCubes(){
        return this.ammoCubes;
    }

    /**
     * Getter of the powerUpCard field.
     * @return true if the ammo contains a power up, false otherwise.
     */
    public boolean getPowerUpCard() {
        return this.powerUpCard;
    }

    public String toString(){
        return this.ammoCubes.toString() +"\nPower Up: " + this.powerUpCard;
    }

    /**
     * Saves the ammo inside a JSONObject object
     * @return the JSONObject to be restored.
     */
    public JSONObject toJSON() {
        JSONObject ammoJson = new JSONObject();

        ammoJson.put("ammoCubes", this.getAmmoCubes().toJSON());
        ammoJson.put("powerUpCard", this.getPowerUpCard());

        return ammoJson;
    }

    /**
     * Restore the Ammo object from the JSONObject object.
     * @param ammoToResume the JSONObject object to be restored.
     * @return the Ammo object restored.
     */
    public static Ammo resumeAmmo(JSONObject ammoToResume) {
        Ammo resumedAmmo = new Ammo();

        resumedAmmo.ammoCubes = Cubes.resumeCubes((JSONObject) ammoToResume.get("ammoCubes"));
        resumedAmmo.powerUpCard = (boolean) ammoToResume.get("powerUpCard");

        return resumedAmmo;
    }
}