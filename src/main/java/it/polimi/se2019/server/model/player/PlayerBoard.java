package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        this.damage = new ArrayList<>();
        this.pointDeaths = new ArrayList<>();
        this.ammoCubes = new Cubes(1,1,1);
        this.setPointDeaths();
        this.owner = owner;
    }

    public static PlayerBoard resumePlayerBoard(JSONObject playerBoardToResume, Match resumedMatch) {
        PlayerBoard resumedPlayerBoard;
        resumedPlayerBoard = new PlayerBoard(resumedMatch.searchPlayerByClientName((String) playerBoardToResume.get("owner")));

        JSONArray damageToResume = (JSONArray) playerBoardToResume.get("damage");
        for (Object damage : damageToResume) {
            resumedPlayerBoard.damage.add(resumedMatch.searchPlayerByClientName((String) damage));
        }

        JSONArray enemyDamagesToResume = (JSONArray) playerBoardToResume.get("enemyDamages");
        for (Object enemyDamageToResume : enemyDamagesToResume) {
            Player aggressorPlayerToResume = resumedMatch.searchPlayerByClientName((String) ((JSONObject) enemyDamageToResume).get("aggressorPlayer"));
            int aggressorDamageToResume = ((Number) ((JSONObject) enemyDamageToResume).get("damage")).intValue();
            resumedPlayerBoard.enemyDamages.add(new EnemyDamage(aggressorPlayerToResume, aggressorDamageToResume));
        }

        JSONArray enemyMarksToResume = (JSONArray) playerBoardToResume.get("enemyMarks");
        for (Object enemyMarkToResume : enemyMarksToResume) {
            Player aggressorPlayerToResume = resumedMatch.searchPlayerByClientName((String) ((JSONObject) enemyMarkToResume).get("aggressorPlayer"));
            int marksToResume = ((Number) ((JSONObject) enemyMarkToResume).get("marks")).intValue();
            resumedPlayerBoard.enemyMarks.add(new EnemyMark(aggressorPlayerToResume, marksToResume));
        }

        resumedPlayerBoard.deaths = ((Number) playerBoardToResume.get("deaths")).intValue();

        JSONArray pointDeathsToResume = (JSONArray) playerBoardToResume.get("pointDeaths");
        while (resumedPlayerBoard.pointDeaths.size() != pointDeathsToResume.size()) {
            resumedPlayerBoard.pointDeaths.remove(0);
        }

        resumedPlayerBoard.ammoCubes = Cubes.resumeCubes((JSONObject) playerBoardToResume.get("ammoCubes"));


        return resumedPlayerBoard;
    }

/*
    public static PlayerBoard resumePlayerBoard(JSONObject playerBoardToResume, PlayerBoard resumedPlayerBoard) {
        //PlayerBoard resumedPlayerBoard = new PlayerBoard();

        JSONArray damageToResume = (JSONArray) playerBoardToResume.get("damage");
        for (Object damage : damageToResume) {
            resumedPlayerBoard.damage.add(new Player()).setClientName((String) damage);
        }


        resumedPlayerBoard.deaths = (int) playerBoardToResume.get("deaths");

        JSONArray pointDeathsToResume = (JSONArray) playerBoardToResume.get("pointDeaths");
        for (Object pointDeath : pointDeathsToResume) {
            if (resumedPlayerBoard.pointDeaths.get(0) == pointDeath)
            resumedPlayerBoard.pointDeaths.remove(0);
        }
        resumedPlayerBoard.pointDeaths = ;

        resumedPlayerBoard.ammoCubes = Cubes.resumeCubes((JSONObject) playerBoardToResume.get("ammoCubes"));

        return resumedPlayerBoard;
    }
*/

    // init pointDeath arrayList at the beginning of the match
    private void setPointDeaths() {
        this.pointDeaths.add(8);
        this.pointDeaths.add(6);
        this.pointDeaths.add(4);
        this.pointDeaths.add(2);
        this.pointDeaths.add(1);
        this.pointDeaths.add(1);
    }

    public void setFinalFrenzyPointDeaths() {
        this.pointDeaths.clear();
        this.pointDeaths.add(2);
        this.pointDeaths.add(1);
        this.pointDeaths.add(1);
        this.pointDeaths.add(1);
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

    public void resetDeaths() {
        this.deaths = 0;
    }

    public ArrayList<EnemyDamage> getEnemyDamages(){
        return this.enemyDamages;
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

        if(this.damage.size() >10){
            if(this.damage.size() == 12)
                attacker.getPlayerBoard().dealMark(this.owner,1);
            owner.setPlayerDead(true);
        }
        if (this.damage.size()> 2 && this.damage.size() < 6) {
            owner.setPhaseAction(1);
        }
        else if (this.damage.size() > 5 && this.damage.size() < 11 ) {
            owner.setPhaseAction(2);
        }
        attacker.getHitThisTurnPlayers().add(owner);
        owner.getLastDamagingPlayers().add(attacker);
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
    public void dealMark(Player aggressor,int marks) {
        EnemyMark marked = null;
        for (EnemyMark mrk : this.enemyMarks) {
            if (mrk.getAggressorPlayer().equals(aggressor))
                marked = mrk;
        }

        if (marked != null) {
            marked.setMarks(marks);
        } else
            this.enemyMarks.add(new EnemyMark(aggressor, marks));
    }
    public void clearDamage() {
        this.enemyDamages.clear();
        this.damage.clear();
    }

    public void payCubes(Cubes cubesToPay){
        int reds = this.ammoCubes.getReds() - cubesToPay.getReds();
        int yellows = this.ammoCubes.getYellows() - cubesToPay.getYellows();
        int blues = this.ammoCubes.getBlues() - cubesToPay.getBlues();
        this.setDeltaAmmoCubes(new Cubes(reds,yellows,blues));
    }

    public JSONObject toJSON() {
        JSONObject playerBoardJson = new JSONObject();

        JSONArray damageJson = new JSONArray();
        for (Player player : this.getDamage()) {
            damageJson.add(player.getClientName());
        }
        playerBoardJson.put("damage", damageJson);

        JSONArray enemyDamagesJson = new JSONArray();
        for (EnemyDamage enemyDamage : this.getEnemyDamages()) {
            enemyDamagesJson.add(enemyDamage.toJSON());
        }
        playerBoardJson.put("enemyDamages", enemyDamagesJson);

        JSONArray enemyMarksJson = new JSONArray();
        for (EnemyMark enemyMark : this.getEnemyMarks()) {
            enemyMarksJson.add(enemyMark.toJSON());
        }
        playerBoardJson.put("enemyMarks", enemyMarksJson);

        playerBoardJson.put("deaths", this.getDeaths());

        JSONArray pointDeathsJson = new JSONArray();
        for (Integer integer : this.getPointDeaths()) {
            pointDeathsJson.add(integer.intValue());
        }
        playerBoardJson.put("pointDeaths", pointDeathsJson);

        playerBoardJson.put("ammoCubes", this.getAmmoCubes().toJSON());

        playerBoardJson.put("owner", this.owner.getClientName());

        return playerBoardJson;
    }
}