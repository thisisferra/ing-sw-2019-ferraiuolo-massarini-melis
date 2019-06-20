package it.polimi.se2019.server.model.player;

import java.io.Serializable;

public class EnemyMark implements Serializable {
    private Player aggressorPlayer;
    private int marks;

    public EnemyMark(Player aggressorPlayer, int marks) {
        this.aggressorPlayer = aggressorPlayer;
        setMarks(marks);
    }

    public Player getAggressorPlayer() {
        return aggressorPlayer;
    }

    //marks can't be more than 3 per player
    public void setMarks(int marks) {
        this.marks = this.marks + marks;
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
