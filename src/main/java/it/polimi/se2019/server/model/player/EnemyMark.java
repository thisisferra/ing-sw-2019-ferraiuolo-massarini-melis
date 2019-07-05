package it.polimi.se2019.server.model.player;

import org.json.simple.JSONObject;
import java.io.Serializable;

/**
 * EnemyMark class represent the number of cumulative damage dealt by each player.
 * Each EnemyMark object contains a Player reference indicating the player who dealt marks,
 * and the number of marks.
 * @author mattiamassarini, merklind,thisisferra.
 */

public class EnemyMark implements Serializable {
    private Player aggressorPlayer;
    private int marks;

    /**
     * Constructor of the EnemyMark class.
     * @param aggressorPlayer the player who deal marks.
     * @param marks the number of marks.
     */
    public EnemyMark(Player aggressorPlayer, int marks) {
        this.aggressorPlayer = aggressorPlayer;
        setMarks(marks);
    }

    /**
     * Getter of the aggressorPlayer field.
     * @return it return the reference of the Player who dealt marks.
     */
    public Player getAggressorPlayer() {
        return aggressorPlayer;
    }

    /**
     * Setter of the marks field.
     * Marks can't be more than three for each player.
     * @param marks the number of marks dealt by the enemy player.
     */
    public void setMarks(int marks) {
        this.marks = this.marks + marks;
        if(this.marks >3)
            this.marks = 3;
    }

    /**
     * Getter of the marks field.
     * @return the number of marks dealt by the enemy player.
     */
    public int getMarks() {
        return marks;
    }

    @Override
    public String toString(){
        return this.getAggressorPlayer() +" "+ this.getMarks();
    }

    /**
     * It saves the current EnemyMark state into a JSONObject object.
     * @return the JSONObject object containing the information to be restored.
     */
    public JSONObject toJSON() {
        JSONObject enemyMarkJson = new JSONObject();

        enemyMarkJson.put("aggressorPlayer", this.getAggressorPlayer().getClientName());
        enemyMarkJson.put("marks", this.getMarks());

        return enemyMarkJson;
    }
}
