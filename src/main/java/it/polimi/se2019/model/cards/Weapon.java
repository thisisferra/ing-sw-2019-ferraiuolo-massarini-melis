package it.polimi.se2019.model.cards;

import it.polimi.se2019.model.game.Cubes;
import it.polimi.se2019.model.player.PlayerBoard;

public abstract class Weapon {
    private String type;
    private boolean load;
    private Cubes buyingCost;
    protected Cubes reloadCost;

    //it returns true if the weapons is actually reloadable, based on the amount of cubes of each color owned by the player
    public boolean reload(PlayerBoard playerBoard) {
        if(this.reloadCost.getBlues()>=playerBoard.getAmmoCubes().getBlues() &&
                this.reloadCost.getReds()>=playerBoard.getAmmoCubes().getReds() &&
                this.reloadCost.getYellows()>=playerBoard.getAmmoCubes().getYellows()) return false;
        else return true;
    }
}