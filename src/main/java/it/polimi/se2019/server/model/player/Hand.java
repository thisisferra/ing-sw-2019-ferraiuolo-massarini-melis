package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.cards.weapons.*;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Hand class represent the cards the player has.
 * It contains weapons and powerUps lists, containing Weapon and PowerUps objects respectively.
 * @author mattiamassarini,merklind,thisisferra.
 */

public class Hand implements Serializable {

    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    /**
     * It resume a Hand object from a JSONObject object.
     * @param playerHandToResume the JSONObject object to be restored.
     * @return the Hand object restored.
     */
    public static Hand resumeHand(JSONObject playerHandToResume) {
        Hand resumedPlayerHand = new Hand();

        JSONArray weaponsToResume = (JSONArray) playerHandToResume.get("weapons");
        for (Object weaponToResume : weaponsToResume) {
            resumedPlayerHand.weapons.add(AbstractWeapon.resumeWeapon((String) weaponToResume));
        }


        JSONArray powerUpsToResume = (JSONArray) playerHandToResume.get("powerUps");
        for (Object powerUpToResume : powerUpsToResume) {
            resumedPlayerHand.powerUps.add(PowerUp.resumePowerUp((JSONObject) powerUpToResume));
        }

        return resumedPlayerHand;
    }

    /**
     * Getter of the weapons list.
     * @return the list of weapons the player has.
     */
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Getter of the powerUps list.
     * @return the list of power ups the player has.
     */
    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    /**
     * Adds the currentPowerUp to the player hand, if the current hand is 2 cards or less.
     * @param currentPowerUp the powerUp to be added.
     */
    public void addPowerUp(PowerUp currentPowerUp) {
        if (checkPowerUps()) {
            this.powerUps.add( currentPowerUp);
        }
        else{
            this.powerUps.add( currentPowerUp);
            this.chooseToDiscard(1);
        }
    }

    /**
     * Check how many power ups the player has in his hand.
     * Return true if the player can draw a power up, false otherwise.
     */
    public boolean checkPowerUps() {
        if(powerUps.size() <= 3) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Discard the power up selected base on the indexToDiscard parameter
     * if the index is a number among 0 and 3.
     * @param indexToDiscard the array index.
     * @return the powerup discarded.
     */
    public PowerUp chooseToDiscard(int indexToDiscard) {
        if(indexToDiscard >=4) {
            return null;
        }
        else {
            return powerUps.remove(indexToDiscard);
        }
    }

    /**
     * It saves the current hand states on a JSONObject object.
     * @return the JSONObject object serialized.
     */
    public JSONObject toJSON() {
        JSONObject handJson = new JSONObject();

        JSONArray weaponsJson = new JSONArray();
        for (Weapon weapon : this.getWeapons()) {
            weaponsJson.add(weapon.getType());
        }
        handJson.put("weapons", weaponsJson);

        JSONArray powerUpsJson = new JSONArray();
        for (PowerUp powerUp : this.getPowerUps()) {
            powerUpsJson.add(powerUp.toJSON());
        }
        handJson.put("powerUps", powerUpsJson);

        return handJson;
    }
}