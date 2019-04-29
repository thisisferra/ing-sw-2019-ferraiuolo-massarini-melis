package it.polimi.se2019.model.player;


public class EnemyDamage{
    private Player aggressorPlayer;
    private int damage;
    private boolean firstShot;

    public int getDamage() {
        return this.damage;
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
