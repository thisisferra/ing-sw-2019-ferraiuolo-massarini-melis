package it.polimi.se2019.server.model.player;

import org.json.simple.JSONObject;
import java.io.Serializable;

/**
 * EnemyDamage class represent the number of cumulative damages dealt by each enemy player.
 * It contains a Player reference of the enemy player who dealt the damage and
 * an int field containing the number of damages.
 * @author mattiamassarini,merklind,thisisferra.
 */
public class EnemyDamage implements Serializable {
    private Player aggressorPlayer;
    private int damage;

    /**
     * Constructor of the EnemyDamage class.
     * @param aggressorPlayer is the player who dealt damage.
     * @param damage the number of damages.
     */
    public EnemyDamage(Player aggressorPlayer, int damage) {
        this.aggressorPlayer = aggressorPlayer;
        this.damage = damage;
    }

    /**
     * Constructor with no parameter.
     */
    public EnemyDamage(){}

    /**
     * Getter of the aggressorPlayer field.
     * @return the player who dealt damage.
     */
    public Player getAggressorPlayer() {
        return this.aggressorPlayer;
    }

    /**
     * Getter of the damage field.
     * @return the number of damages dealt.
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Setter of the aggressorPlayer field.
     * @param aggressorPlayer the player who dealt damage.
     */
    public void setAggressorPlayer(Player aggressorPlayer){
        this.aggressorPlayer = aggressorPlayer;
    }

    /**
     * Setter of the damage field.
     * Damages exceeding 12 are ignored.
     * @param damage the damage dealt.
     */
    public void setDamage(int damage) {
        this.damage = this.damage+damage;
        if(this.damage > 12) this.damage = 12;
    }

    @Override
    public String toString(){
        return this.getAggressorPlayer()+ "" + " " + this.getDamage();
    }

    /**
     * It stores the current object state in a JSONObject object.
     * @return the JSONObject object to be restored.
     */
    public JSONObject toJSON() {
        JSONObject enemyDamageJson = new JSONObject();

        enemyDamageJson.put("aggressorPlayer", this.getAggressorPlayer().getClientName());
        enemyDamageJson.put("damage", this.getDamage());

        return enemyDamageJson;
    }
}
