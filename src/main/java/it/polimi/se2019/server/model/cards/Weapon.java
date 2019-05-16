package it.polimi.se2019.server.model.cards;

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
    ArrayList<Player> getTargets();
    void applyEffect();
    Weapon weaponFactory(Weapon weapon);
}
