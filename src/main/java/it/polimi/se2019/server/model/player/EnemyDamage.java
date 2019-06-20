package it.polimi.se2019.server.model.player;


import java.io.Serializable;

public class EnemyDamage implements Serializable {
    private Player aggressorPlayer;
    private int damage;

    public EnemyDamage(Player aggressorPlayer, int damage) {
        this.aggressorPlayer = aggressorPlayer;
        this.damage = damage;
    }
    public EnemyDamage(){}

    public Player getAggressorPlayer() {
        return this.aggressorPlayer;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setAggressorPlayer(Player aggressorPlayer){
        this.aggressorPlayer = aggressorPlayer;
    }

    public void setDamage(int damage) {
        this.damage = this.damage+damage;
        if(this.damage > 12) this.damage = 12;
    }

    @Override
    public String toString(){
        return this.getAggressorPlayer()+ "" + " " + this.getDamage();
    }

}
