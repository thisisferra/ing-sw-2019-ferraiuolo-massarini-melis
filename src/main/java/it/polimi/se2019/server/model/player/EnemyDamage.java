package it.polimi.se2019.server.model.player;


public class EnemyDamage{
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
        if (this.getDamage() <= 11) {
            if (this.getDamage() + damage <= 11) {
                this.damage = this.damage + damage;
            }
            else {
                this.damage = 11;
            }
        }
    }

}
