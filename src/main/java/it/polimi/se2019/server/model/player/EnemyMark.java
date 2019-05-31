package it.polimi.se2019.server.model.player;

public class EnemyMark {
    private Player aggressorPlayer;
    private int marks;

    public EnemyMark(Player aggressorPlayer, int marks) {
        this.aggressorPlayer = aggressorPlayer;
        this.marks = marks;
    }

    public Player getAggressorPlayer() {
        return aggressorPlayer;
    }

    public void setAggressorPlayer(Player aggressorPlayer) {
        this.aggressorPlayer = aggressorPlayer;
    }

    //marks can't be more than 3 per player
    public void setMarks(int marks) {
        this.marks = marks;
        if(this.marks >3)
            this.marks = 3;
    }

    public int getMarks() {
        return marks;
    }

    @Override
    public String toString(){
        return this.getAggressorPlayer() +" "+ this.getMarks();
    }
}
