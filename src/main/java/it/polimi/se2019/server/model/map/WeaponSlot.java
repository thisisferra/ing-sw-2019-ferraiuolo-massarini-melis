package it.polimi.se2019.server.model.map;

import it.polimi.se2019.server.model.cards.weapons.AbstractWeapon;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
/*WeaponSlot represent one three slots array containing 3 weapons.
* Each one of them has a color among red,blue or yellow*/

public class WeaponSlot implements Serializable {
    private Weapon[] slot;
    private String cabinetColor;
    private Match match;

    public WeaponSlot(String color,Match match){
            this.cabinetColor = color;
            this.match = match;
            this.initSlot();

    }

    public WeaponSlot(Match resumedMatch) {
        //Needed for resuming a weaponSlot from saved match
        this.match = resumedMatch;
    }

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

    public void initSlot() {
        slot = new Weapon[3];
        slot[0] = this.match.pickUpWeapon();
        slot[1] = this.match.pickUpWeapon();
        slot[2] = this.match.pickUpWeapon();
    }
     public Weapon[] getSlot(){
        return this.slot;
     }

    public String getCabinetColor() {
        return cabinetColor;
    }
    public void setCabinetColor(String color){
        this.cabinetColor= color;
    }

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
