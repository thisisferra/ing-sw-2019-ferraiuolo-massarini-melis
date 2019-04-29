package it.polimi.se2019.model.player;


public class EnemyDamage{
    private Player aggressorPlayer;
    private int damage;
    private boolean firstShot;

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
            this.damage = this.damage + damage;
        }

    public void setFirstShot(boolean bool) {
        this.firstShot = bool;
    }

    public boolean getFirstShot(){
        return this.firstShot;
    }

    public Player getAggressorPlayer() {
        return this.aggressorPlayer;
    }

    public void setAggressorPlayer(Player aggressorPlayer){
        this.aggressorPlayer = aggressorPlayer;
    }

}
