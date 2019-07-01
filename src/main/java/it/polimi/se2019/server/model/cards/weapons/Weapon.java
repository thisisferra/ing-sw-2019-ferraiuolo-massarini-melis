package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;

import java.util.ArrayList;

public interface Weapon {

    void setLoad(boolean load);
    boolean getLoad();
    Cubes getReloadCost();
    Cubes getBuyingCost();
    String getType();
    Shot[] getEffect();
    ArrayList<WeaponShot> getWeaponShots();
    void applyEffect(WeaponShot weaponShot);
    Weapon weaponFactory(Weapon weapon);
}
