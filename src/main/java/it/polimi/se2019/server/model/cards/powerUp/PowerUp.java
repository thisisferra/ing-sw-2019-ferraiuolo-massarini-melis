package it.polimi.se2019.server.model.cards.powerUp;


import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.player.EnemyDamage;
import it.polimi.se2019.server.model.player.Player;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class PowerUp implements Serializable {
    protected String type;
    protected String color;
    protected Shot effect;

    public String getType(){
        return this.type;
    }

    public String getColor(){
        return this.color;
    }

    //return the infos about the powerup
    public String toString(){
        return "Type: " + this.type + " Color: " + this.color;
    }

    public abstract void applyEffect(PowerUpShot powerUpShot);

    public JSONObject toJSON() {
        JSONObject powerUpJson = new JSONObject();

        powerUpJson.put("type", this.getType());
        powerUpJson.put("color", this.getColor());
        return powerUpJson;
    }

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