package it.polimi.se2019.server.model.player;

public class MarkDamage {
    private Player aggressorPlayer;
    private int marks;

    public MarkDamage(Player aggressorPlayer, int marks) {
        this.aggressorPlayer = aggressorPlayer;
        this.marks = marks;
    }
}
