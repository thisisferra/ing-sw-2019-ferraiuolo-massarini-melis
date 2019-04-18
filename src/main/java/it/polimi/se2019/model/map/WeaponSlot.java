package it.polimi.se2019.model.map;

import it.polimi.se2019.model.cards.Weapon;
/*WeaponSlot represent one three slots array containing 3 weapons.
* Each one of them has a color among red,blue or yellow*/

public class WeaponSlot {
    private Weapon[] slot;
    private String cabinetColor;

    public WeaponSlot(String color){
            this.cabinetColor = color;
            /* vector is initialized by drawing three cards from weaponsdeck
            slot[0] = drawWeapon();
            slot[1] = drawWeapon();
            slot[2] = drawWeapon();
            */

    }
    public void setSlot(Weapon[] slot) {
        this.slot = slot;
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
