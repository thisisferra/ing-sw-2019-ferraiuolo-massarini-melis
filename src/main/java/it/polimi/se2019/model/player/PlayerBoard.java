package it.polimi.se2019.model.player;

import it.polimi.se2019.model.game.Cubes;

import java.util.ArrayList;


public class PlayerBoard {
    private ArrayList<Player> tags;
    private ArrayList<Player> damage;
    private int deaths;
    private ArrayList<Integer> pointDeaths = new ArrayList<>();
    private Cubes ammoCubes;

    //constructor of the class
    //set the ammoCubes to 1 red, 1 yellow and 1 blue
    public PlayerBoard(){
        tags = new ArrayList<>();
        damage = new ArrayList<>();
        this.ammoCubes = new Cubes(1,1,1);
        this.setPointDeaths();
    }

    // init pointDeath arrayList at the beginning of the match
    private void setPointDeaths() {
        this.pointDeaths.add(8);
        this.pointDeaths.add(6);
        this.pointDeaths.add(4);
        this.pointDeaths.add(2);
        this.pointDeaths.add(1);
        this.pointDeaths.add(1);
    }

    // show players tags
    public ArrayList<Player> getTags(){
        return this.tags;
    }

    //show damage taken from all players
    public ArrayList<Player> getDamage(){
        return this.damage;
    }

    //show deaths of the players
    public int getDeaths(){
        return deaths;
    }

    public void setDeaths() {
        if(this.getDeaths()<=5) {
            this.deaths += 1;
        }
        else{
            throw new IllegalStateException();

        }
    }
    public ArrayList<Integer> getPointDeaths() {
        return this.pointDeaths;
    }

    //return the points for a death at a specific index
    public int getSpecificPointDeaths(int index) {
        int size = this.pointDeaths.size();
        if (index >= 0 && index <= size) {
            return this.pointDeaths.get(index);
        }
        else {
            throw new IndexOutOfBoundsException("You have digit an illegal index");
        }
    }

    //return the cubes
    public Cubes getAmmoCubes() {
        return this.ammoCubes;
    }

    //it adds to ammoCubes an amount currentCubes
    //each color cannot exceeds 3 (max 3 reds, 3 yellows, 3 blues)
    //used when you draw a powerUp
    public void setAmmoCubes(Cubes currentCubes) {
        ammoCubes.setCubes(currentCubes);
    }

    //remove the element at index 0 in ArrayList pointDeath
    //used when a player dies
    public void deleteFirstPointDeaths() {
        if (this.getPointDeaths().size() >= 1) {
            pointDeaths.remove(0);
        }
        else{
            System.out.println("Il giocatore è morto per sempre");

        }
    }
}