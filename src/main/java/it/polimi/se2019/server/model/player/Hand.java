package it.polimi.se2019.server.model.player;

import com.google.gson.Gson;
import it.polimi.se2019.server.model.cards.weapons.*;
import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Hand implements Serializable {

    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

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

    /*
      discard one card from the player hand,
      the card is lost if it's a weapon card,
      otherwise it's added on the corresponding
      discarded list
    */
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void addPowerUp(PowerUp currentPowerUp) {
        if (checkPowerUps()) {
            this.powerUps.add( currentPowerUp);
        }
        else{
            this.powerUps.add( currentPowerUp);
            this.chooseToDiscard(1);
        }
    }

    //Check how many powerUp the player has in his hand.
    //Return true if the player can draw a powerUp, false otherwise.
    public boolean checkPowerUps() {
        if(powerUps.size() <= 3) {
            return true;
        }
        else{
            return false;
        }
    }

    public PowerUp chooseToDiscard(int indexToDiscard) {
        int index = 1;
        //Print the list of all powerups the player own
        for(PowerUp object : powerUps) {
            System.out.println(index + ". " + object.getType() + " - " + object.getColor());
            index++;
        }

        if(indexToDiscard >=4) {
            System.out.println("You have typed an illegal number");
            return null;
        }
        else {
            //remove from the ArrayList the powerUp chosen
            return powerUps.remove(indexToDiscard);

        }
    }

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