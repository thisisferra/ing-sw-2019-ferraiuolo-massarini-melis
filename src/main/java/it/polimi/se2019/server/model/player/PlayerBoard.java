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
 * The Playerboard class contains all the information regarding the real playerboard counterpart.
 * It stores the number of damages the player accumulated as well the number of marks, point-death, cubes and deaths.
 * @author mattiamassarini,merklind,thisisferra
 */
public class PlayerBoard implements Serializable {

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

    /**
     * Set the ammoCubes to 1 red, 1 yellow and 1 blue at the start of the game
     * @param owner is the player who owns the playerboard.
     */
    public PlayerBoard(Player owner){
        this.enemyDamages = new ArrayList<>();
        this.enemyMarks = new ArrayList<>();
        this.damage = new ArrayList<>();
        this.pointDeaths = new ArrayList<>();
        this.ammoCubes = new Cubes(1,1,1);
        this.setPointDeaths();
        this.owner = owner;
    }

    /**
     * It restores the playerboard back to its original state.
     * @param playerBoardToResume JSONObject object containing the information needed to restore the object.
     * @param resumedMatch Match object used to search and link the players to their playerboards.
     * @return the playerboard restored.
     */
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

    /**
     * It fills the pointDeath arrayList with the starting game points.
     * Each element represent the number of points a player gets for killing another one.
     * The order is based on the number of damage dealt by each player.
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

    /**
     * It refills the pointDeath arrayList with the final frenzy game points.
     *  Each element represent the number of points a player gets for killing another one.
     *  The order is based on the number of damage dealt by each player.
     */
    public void setFinalFrenzyPointDeaths() {
        this.pointDeaths.clear();
        this.pointDeaths.add(2);
        this.pointDeaths.add(1);
        this.pointDeaths.add(1);
        this.pointDeaths.add(1);
    }

    /**
     * Getter method returning the Player collection.
     * @return the number of damages each player dealt in chronologically order.
     */
    public ArrayList<Player> getDamage(){
        return this.damage;
    }

    /**
     * Getter method returning how many times the player died during the game.
     * @return the number of deaths.
     */
    public int getDeaths(){
        return deaths;
    }

    /**
     * It adds one death to the deaths field.
     * It cannot exceeds 5 deaths.
     */
    public void setDeaths() {
        if(this.getDeaths()<=5) {
            this.deaths += 1;
        }
        else{
            throw new IllegalStateException();

        }
    }

    /**
     * Set the number of deaths to 0.
     */
    public void resetDeaths() {
        this.deaths = 0;
    }

    /**
     * Getter method of the enemyDamages collection.
     * @return the collection of EnemyDamages.
     */
    public ArrayList<EnemyDamage> getEnemyDamages(){
        return this.enemyDamages;
    }

    /**
     * Getter method of the pointDeaths collection.
     * @return pointDeaths collection.
     */
    public ArrayList<Integer> getPointDeaths() {
        return this.pointDeaths;
    }

    /**
     *
     * @return the number of cubes the player has.
     */
    public Cubes getAmmoCubes() {
        return this.ammoCubes;
    }

    /**
     *
     * @return the enemyMarks collection.
     */
    public ArrayList<EnemyMark> getEnemyMarks() {
        return enemyMarks;
    }

    /**
     * It adds cubes to the current ammoCubes.
     * They cannot exceeds 3 cubes for each color.
     * @param currentCubes the number of cubes to be added.
     */
    public void setAmmoCubes(Cubes currentCubes) {
        ammoCubes.setCubes(currentCubes);
    }

    /**
     * It sets the number of cubes to currentCubes.
     * @param currentCubes the number of cubes to be set.
     */
    public void setDeltaAmmoCubes(Cubes currentCubes) {
        ammoCubes.setDeltaCubes(currentCubes);
    }

    /**
     * It removes the first element of the firstPointDeaths list,
     * whenever the player die.
     */
    public void deleteFirstPointDeaths() {
        if (!this.getPointDeaths().isEmpty()) {
            pointDeaths.remove(0);
        }
        else{
            System.out.println("Il giocatore è stato ucciso.");

        }
    }

    /**
     * It sort the enemyDamages list from the highest damage to the lowest.
     */
    public void sortAggressor() {
        Collections.sort(this.enemyDamages, Comparator.comparingInt(EnemyDamage::getDamage).reversed());
    }

    //deal damage to this target, add a number of Player references equal to the damage parameter,
    // marks increase the damage if available
    //if the attacked player damage list is empty, the attacker gains 1 point
    //if the attacker reaches the 12th damage, he gets marked

    /**
     * It deals damage to the current player.
     * If the damage collection is empty, the attacker gains one point.
     * If the current player has marks of the attacker player, the method
     * marksToDamages will be called and additional damage is applied.
     * If the damage exceeds certain thresholds, it triggers adrenaline actions.
     * @param attacker the reference of the player who's attacking.
     * @param damage the damage dealt.
     */
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

    /**
     * check if the attacker has marks on his target, if so it returns the number of
     * marks accumulated and erase that element from the enemyMarks list.
     */
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

    /**
     * apply a number of marks, if it's the first time the aggressor deals marks a new
     * EnemyMark object is created.
     */
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

    /**
     * Subtract the current cubes stack from the cubes
     * @param cubesToPay cubes to be paid.
     */
    public void payCubes(Cubes cubesToPay){
        int reds = this.ammoCubes.getReds() - cubesToPay.getReds();
        if(reds <0 )
            reds = 0;
        int yellows = this.ammoCubes.getYellows() - cubesToPay.getYellows();
        if(yellows <0 )
            yellows = 0;
        int blues = this.ammoCubes.getBlues() - cubesToPay.getBlues();
        if(blues <0 )
            blues = 0;
        this.setDeltaAmmoCubes(new Cubes(reds,yellows,blues));
    }

    /**
     * It save the current state on a JSONObject object.
     * @return the JSONObject containing the information of the original Cubes object.
     */
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