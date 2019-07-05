package it.polimi.se2019.server.model.map;

import it.polimi.se2019.server.model.cards.weapons.AbstractWeapon;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * WeaponSlot represent one three slots array containing 3 weapons maximum.
 * Each one of them has a color among red,blue or yellow.
 * @author mattiamassarini,merklind,thisisferra.
 */
public class WeaponSlot implements Serializable {
    private Weapon[] slot;
    private String cabinetColor;
    private Match match;

    /**
     * Constructor of the WeaponSlot class.
     * @param color the color of the cabinet.
     * @param match the match whose cabinet belongs to.
     */
    public WeaponSlot(String color,Match match){
            this.cabinetColor = color;
            this.match = match;
            this.initSlot();

    }

    /**
     * Constructor of the WeaponsSlot class.
     * @param resumedMatch the match whose cabinet belongs to.
     */
    public WeaponSlot(Match resumedMatch) {
        //Needed for resuming a weaponSlot from saved match
        this.match = resumedMatch;
    }

    /**
     * It restore the cabinet from a JSONObject object.
     * @param weaponSlotToResume the JSONOBject object to be restored.
     * @param resumedMatch the match whose cabinet belongs to.
     * @return the WeaponsSlot restored.
     */
    public static WeaponSlot resumeWeaponSlot(JSONObject weaponSlotToResume, Match resumedMatch) {
        WeaponSlot resumedWeaponSlot = new WeaponSlot(resumedMatch);

        JSONArray slotToResume = (JSONArray) weaponSlotToResume.get("slot");
        resumedWeaponSlot.slot = new Weapon[slotToResume.size()];
        for (int i = 0; i < slotToResume.size(); i++) {
            resumedWeaponSlot.slot[i] = AbstractWeapon.resumeWeapon((String) slotToResume.get(i));
        }

        resumedWeaponSlot.cabinetColor = (String) weaponSlotToResume.get("cabinetColor");

        return resumedWeaponSlot;
    }

    /**
     * Initialize the cabinets with 3 weapons picked up from the weaponsStack.
     */
    public void initSlot() {
        slot = new Weapon[3];
        slot[0] = this.match.pickUpWeapon();
        slot[1] = this.match.pickUpWeapon();
        slot[2] = this.match.pickUpWeapon();
    }

    /**
     * Getter of the slot field.
     * @return it return an array containing the weapons.
     */
     public Weapon[] getSlot(){
        return this.slot;
     }

    /**
     * Getter of the color field.
     * @return it return the color of the cabinet.
     */
    public String getCabinetColor() {
        return cabinetColor;
    }

    /**
     * It saves the current cabinet state on a JSONObject object.
     * @return the JSONObject object containing the information.
     */
    public JSONObject toJSON() {
        JSONObject weaponSlotJson = new JSONObject();

        JSONArray slotJson = new JSONArray();
        for (Weapon weapon : this.getSlot()) {
            if (weapon != null) {
                slotJson.add(weapon.getType());
            }
        }
        weaponSlotJson.put("slot", slotJson);

        weaponSlotJson.put("cabinetColor", this.getCabinetColor());

        return weaponSlotJson;
    }
}
