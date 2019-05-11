package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;

import java.util.ArrayList;

public class Weapon {
    private String type;
    private boolean load;
    private  Cubes buyingCost;
    private Cubes reloadCost;
    private Shot[] effect;

    public Weapon(){
    }

    public Weapon(Weapon weapon) {
         this.type = weapon.type;
         this.load = weapon.load;
         this.buyingCost = new Cubes(weapon.buyingCost.getReds(), weapon.buyingCost.getYellows(), weapon.buyingCost.getBlues());
         this.reloadCost = new Cubes(weapon.reloadCost.getReds(), weapon.reloadCost.getYellows(), weapon.reloadCost.getBlues());
         int length = weapon.effect.length;
         this.effect = new Shot[length];
         for(int i = 0; i < length; i++)
             this.effect[i] = new Shot(weapon.effect[i]);

    }

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

    public String toString(){
        return this.type + " BC: " + this.buyingCost + " RC: " + this.reloadCost;
    }

    public Shot[] getEffect(){
        return this.effect;
    }

}