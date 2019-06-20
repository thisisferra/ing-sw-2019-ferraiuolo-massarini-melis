package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.game.Cubes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * Playerboard class represent the playerboard of each player. It includes some info like the amount
 * of cubes, tags, damage and deaths and some reference to external opbject
 */
public class PlayerBoard implements Serializable {
    //list of references of players who marked this player (max 3 references per player)
    private ArrayList<Player> tags;
    //list of references of players who damaged this player
    private ArrayList<Player> damage;
    //list of enemyDamage instances counting the total damage each player inflicted
    private  ArrayList<EnemyDamage> enemyDamages ;
    //list of enemyMark instances counting the total marks each player inflicted
    private  ArrayList<EnemyMark> enemyMarks ;
    private int deaths;
    //list representing the points players get when the target is killed (starting from 8,6,4,2,1,1,)
    private ArrayList<Integer> pointDeaths;
    //cubes the player currently have
    private Cubes ammoCubes;
    private Player owner;

    //constructor of the class
    //set the ammoCubes to 1 red, 1 yellow and 1 blue at the start of the game
    public PlayerBoard(Player owner){
        this.enemyDamages = new ArrayList<>();
        this.enemyMarks = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.damage = new ArrayList<>();
        this.pointDeaths = new ArrayList<>();
        this.ammoCubes = new Cubes(1,1,1);
        this.setPointDeaths();
        this.owner = owner;
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

    public ArrayList<EnemyDamage> getEnemyDamages(){
        return this.enemyDamages;
    }

    public ArrayList<EnemyMark> getMarkDamages(){
        return this.enemyMarks;
    }
    public ArrayList<Integer> getPointDeaths() {
        return this.pointDeaths;
    }

    //return the cubes
    public Cubes getAmmoCubes() {
        return this.ammoCubes;
    }

    public ArrayList<EnemyMark> getEnemyMarks() {
        return enemyMarks;
    }

    //it adds to ammoCubes an amount currentCubes
    //each color cannot exceeds 3 (max 3 reds, 3 yellows, 3 blues)
    //used when you draw a powerUp
    public void setAmmoCubes(Cubes currentCubes) {
        ammoCubes.setCubes(currentCubes);
    }

    public void setDeltaAmmoCubes(Cubes currentCubes) {
        ammoCubes.setDeltaCubes(currentCubes);
    }

    //remove the element at index 0 in ArrayList pointDeath
    //used when a player dies
    public void deleteFirstPointDeaths() {
        if (!this.getPointDeaths().isEmpty()) {
            pointDeaths.remove(0);
        }
        else{
            System.out.println("Il giocatore è stato ucciso.");

        }
    }

    //sort enemyDamages list based on the damage dealt by the enemies, from the highest to the lowest
    public void sortAggressor() {
        Collections.sort(this.enemyDamages, Comparator.comparingInt(EnemyDamage::getDamage).reversed());
    }

    //deal damage to this target, add a number of Player references equal to the damage parameter,
    // marks increase the damage if available
    //if the attacked player damage list is empty, the attacker gains 1 point
    //if the attacker reaches the 12th damage, he gets marked
    public void dealDamage(Player attacker,int damage){
        EnemyDamage found = null;
        int additionalDamage = marksToDamage(attacker);


        if(this.getDamage().isEmpty()){
            attacker.addPoints(1);
        }

        for(int i = 0; i<damage + additionalDamage; i++)
            if(this.damage.size()<12)
                this.damage.add(attacker);
        for(EnemyDamage enemyDamage : this.enemyDamages){
            if(enemyDamage.getAggressorPlayer().equals(attacker))
                found = enemyDamage;
        }
        if(found != null)
            found.setDamage(damage+additionalDamage);
        else this.enemyDamages.add(new EnemyDamage(attacker,damage+additionalDamage));

        if(this.damage.size() >11){
            attacker.getPlayerBoard().dealMark(this.owner,1);
        }
        sortAggressor();
    }

    //check if the attacker has marks on his target, if so it returns the number of
    // marks accumulated and erase that element from the enemyMarks list.
    private int marksToDamage(Player attacker){
        int boostDamage = 0;
        EnemyMark markExpired = null;
        for(EnemyMark marks : this.enemyMarks){
            if(marks.getAggressorPlayer().equals(attacker)){
                boostDamage = marks.getMarks();
                markExpired = marks;
            }
        }
        if(boostDamage > 0)
            this.enemyMarks.remove(markExpired);
        return boostDamage;
    }

    //apply a number of marks, if it's the first time the aggressor deals marks a new
    // EnemyMark object is created.
    public void dealMark(Player aggressor,int marks){
        EnemyMark marked = null;
        for(EnemyMark mrk: this.enemyMarks){
            if(mrk.getAggressorPlayer().equals(aggressor))
                marked = mrk;
        }
        if(marked != null){
            marked.setMarks(marks);
        }
        else this.enemyMarks.add(new EnemyMark(aggressor,marks));
    }
}