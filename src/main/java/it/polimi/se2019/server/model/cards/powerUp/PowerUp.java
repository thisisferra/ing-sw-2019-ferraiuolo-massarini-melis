package it.polimi.se2019.server.model.cards.powerUp;


import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.model.cards.Shot;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * PowerUp abstract class describes the power up cards.
 * Fields are type: the name of the power up, the color.
 */
public abstract class PowerUp implements Serializable {
    protected String type;
    protected String color;
    protected Shot effect;

    /**
     * Getter of the type field.
     * @return the name of the powerup.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Getter of the color field.
     * @return the color of the power up.
     */
    public String getColor(){
        return this.color;
    }

    public String toString(){
        return "Type: " + this.type + " Color: " + this.color;
    }

    /**
     * Abstract method used to apply the power up effect, depending on
     * the dynamic type.
     * @param powerUpShot object used for applying the effect, it contains
     *                    data from the user.
     */
    public abstract void applyEffect(PowerUpShot powerUpShot);

    /**
     * It stores the PowerUp object into a JSONObject object.
     * @return the JSONObject stored.
     */
    public JSONObject toJSON() {
        JSONObject powerUpJson = new JSONObject();

        powerUpJson.put("type", this.getType());
        powerUpJson.put("color", this.getColor());
        return powerUpJson;
    }

    /**
     * Restore the powerUp from the JSONObject object.
     * @param powerUpToResume the JSONObject to be restored.
     * @return the PowerUp restored.
     */
    public static PowerUp resumePowerUp(JSONObject powerUpToResume) {
        PowerUp resumedPowerUp;

        String type = (String) powerUpToResume.get("type");

        switch(type) {
            case "newton":
                resumedPowerUp = new Newton((String) powerUpToResume.get("type"), (String) powerUpToResume.get("color"));
                break;
            case "tagback_grenade":
                resumedPowerUp = new TagbackGrenade((String) powerUpToResume.get("type"), (String) powerUpToResume.get("color"));
                break;
            case "targeting_scope":
                resumedPowerUp = new TargetingScope((String) powerUpToResume.get("type"), (String) powerUpToResume.get("color"));
                break;
            case "teleporter":
                resumedPowerUp = new Teleporter((String) powerUpToResume.get("type"), (String) powerUpToResume.get("color"));
                break;
            default:
                resumedPowerUp = null;
                break;
        }

        return resumedPowerUp;
    }
}