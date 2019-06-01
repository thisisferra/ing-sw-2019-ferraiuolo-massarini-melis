package it.polimi.se2019.server.model.cards.weapons;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.model.cards.Shot;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

public interface Weapon {

    void setLoad(boolean load);
    boolean getLoad();
    Cubes getReloadCost();
    Cubes getBuyingCost();
    String getType();
    Shot[] getEffect();
    void applyEffect(InfoShot infoShot);
    Weapon weaponFactory(Weapon weapon);
}
