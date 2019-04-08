package it.polimi.se2019.model.cards;

import it.polimi.se2019.model.game.Cubes;

public class OptionalWeapon extends Weapon {
    private Shot basicShot;
    private Shot alphaOptional;
    private Shot betaOptional;

    //reload the weapons paying its cost
    @Override
    public void reload(Cubes cost) {

    }
}