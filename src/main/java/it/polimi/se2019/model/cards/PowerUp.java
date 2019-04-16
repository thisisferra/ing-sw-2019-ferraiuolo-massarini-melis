package it.polimi.se2019.model.cards;
import it.polimi.se2019.model.game.Cubes;
import javafx.scene.effect.Effect;

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

    //trade the current powerup in cubes of the matching color
    public Cubes tradeCube(){

        if(this.color.equals("RED")) return new Cubes(1,0,0);
        else if(this.color.equals("BLUE")) return new Cubes(0,0,1);
        else return new Cubes(0,1,0);

    }
    //return the infos about the powerup
    public String toString(){
        return "Type: " + this.type + " Color: " + this.color;
    }
}