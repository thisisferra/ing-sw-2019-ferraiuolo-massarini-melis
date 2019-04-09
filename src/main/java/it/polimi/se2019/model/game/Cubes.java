package it.polimi.se2019.model.game;

public class Cubes {
    private int reds;
    private int yellows;
    private int blues;

    Cubes (int reds, int yellows, int blues) {
        this.reds = reds;
        this.yellows = yellows;
        this.blues = blues;
    }

    public int getReds(){
        return this.reds;
    }
    public int getYellows(){
        return this.yellows;
    }
    public int getBlues(){
        return this.blues;
    }
}
