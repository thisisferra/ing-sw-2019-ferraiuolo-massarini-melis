package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;

import java.util.ArrayList;

public interface Weapon {

    /**
     * Setter of the load field.
     * @param load the loading state of the weapons.
     */
    void setLoad(boolean load);

    /**
     * Getter of the load field.
     * @return true if it can be used, false otherwise.
     */
    boolean getLoad();

    /**
     * Getter of the reloadCost.
     * @return the Cubes cost of the weapon to reload.
     */
    Cubes getReloadCost();

    /**
     * Getter of the buyingCost.
     * @return the Cubes cost of the weapon to buy.
     */
    Cubes getBuyingCost();

    /**
     * Getter of the type field.
     * @return the name of the weapon.
     */
    String getType();

    /**
     * Getter of the effect field.
     * @return the array of the effect the weapon has.
     */
    Shot[] getEffect();

    /**
     * Getter of the weaponShots field.
     * @return the list of the weaponShots available.
     */
    ArrayList<WeaponShot> getWeaponShots();

    /**
     * Apply the effect of the weapon based on the weapon,
     * the effect chosen inside weaponShot parameter.
     * @param weaponShot the WeaponShot object used to obtain data
     *                   and for applying damage and marks.
     */
    void applyEffect(WeaponShot weaponShot);

    /**
     * Factory method for duplicating weapons, in order to
     * apply the effect.
     * @param weapon the weapon to be duplicated.
     * @return the weapon duplicated.
     */
    Weapon weaponFactory(Weapon weapon);
}
