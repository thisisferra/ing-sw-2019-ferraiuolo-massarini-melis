package it.polimi.se2019.model.cards;


public class PowerUp {
    private String type;
    private String color;
    private Shot effect;

    //apply the effect
    public void effect(){

    }

    public String getType(){
        return this.type;
    }

    public String getColor(){
        return this.color;
    }

    //return the infos about the powerup
    public String toString(){
        return "Type: " + this.type + " Color: " + this.color;
    }
}