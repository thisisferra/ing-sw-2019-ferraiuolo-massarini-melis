package it.polimi.se2019.server.model.map;

import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
/*WeaponSlot represent one three slots array containing 3 weapons.
* Each one of them has a color among red,blue or yellow*/

public class WeaponSlot {
    private Weapon[] slot;
    private String cabinetColor;
    private Match match;

    public WeaponSlot(String color,Match match){
            this.cabinetColor = color;
            this.match = match;

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
}
