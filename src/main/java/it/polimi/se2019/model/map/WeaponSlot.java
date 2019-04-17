package it.polimi.se2019.model.map;

import it.polimi.se2019.model.cards.Weapon;
/*WeaponSlot represent one three slots array containing 3 w.json ready to picked up.
* Each of them has a color (red,blue,yellow)*/

public class WeaponSlot {
    private Weapon[] slot;
    private String cabinetColor;

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
