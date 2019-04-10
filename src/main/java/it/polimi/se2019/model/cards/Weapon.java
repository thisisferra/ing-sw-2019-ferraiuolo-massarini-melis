package it.polimi.se2019.model.cards;
import it.polimi.se2019.model.game.Cubes;

public abstract class Weapon {
    private String type;
    private boolean load;
    private  Cubes buyingCost;
    private Cubes reloadCost;


    public void setLoad(boolean load) {
        this.load = load;
    }

    public boolean getLoad(){
        return this.load;
    }

    public Cubes getReloadCost() {
        return reloadCost;
    }

    public Cubes getBuyingCost() {
        return buyingCost;
    }

    public String getType() {
        return type;
    }

}