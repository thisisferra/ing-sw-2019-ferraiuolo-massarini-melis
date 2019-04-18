package it.polimi.se2019.model.player;

import it.polimi.se2019.model.game.Cubes;
import java.util.ArrayList;

public class PlayerBoard {
    private ArrayList<Integer> tags;
    private ArrayList<Integer> damage;
    private int deaths;
    private Cubes ammoCubes;

    // show players tags
    public ArrayList<Integer> getTags(){
        return this.tags;
    }
    //show damage taken from all players
    public ArrayList<Integer> getDamage(){
        return this.damage;
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

    //it adds to ammoCubes an amount currentCubes
    //each color cannot exceeds 3 (max 3 reds, 3 yellows, 3 blues)
    public void setAmmoCubes(Cubes currentCubes) {
        ammoCubes.setCubes(currentCubes);
    }
}