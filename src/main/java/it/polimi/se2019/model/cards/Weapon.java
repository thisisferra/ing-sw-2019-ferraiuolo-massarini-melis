package it.polimi.se2019.model.cards;

import it.polimi.se2019.model.game.Cubes;

public abstract class Weapon {
    private String type;
    private boolean load;
    private Cubes buyingCost;
    private Cubes reloadCost;

    public abstract void reload(Cubes cost);
}