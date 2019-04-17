package it.polimi.se2019.model.player;

import it.polimi.se2019.model.game.Cubes;
import java.util.ArrayList;

public class PlayerBoard {
    private ArrayList<Integer> tags;
    private ArrayList<Integer> damage;
    private int deaths;
    private Cubes ammoCubes;

    // show players tags
    public void showTags(){

    }
    //show damage taken from all players
    public void showDamage(){

    }
    //show deaths of all players
    public int getDeaths(){
        return this.deaths;
    }

    public PlayerBoard(){
        this.deaths = 0;
        this.ammoCubes = new Cubes(1,1,1);
    }
    public Cubes getAmmoCubes() {
        return this.ammoCubes;
    }

    public void setAmmoCubes(Cubes currentCubes) {
        ammoCubes.setCubes(currentCubes);
    }
}