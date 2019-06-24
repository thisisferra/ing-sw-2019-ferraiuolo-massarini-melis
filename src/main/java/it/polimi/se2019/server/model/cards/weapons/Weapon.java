package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.InfoShot;
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
    int getMaxTarget();
    void setMaxTarget(int maxTarget);
    int getMaxMovementTarget();
    int getMaxMovementPlayer();
    ArrayList<InfoShot> getInfoShots();
    void applyEffect(InfoShot infoShot);
    Weapon weaponFactory(Weapon weapon);
}
