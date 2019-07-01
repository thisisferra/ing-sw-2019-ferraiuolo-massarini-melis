package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.map.Square;

import java.io.Serializable;
import java.util.ArrayList;

public class Shot implements Serializable {

    private String nameEffect;
    private Cubes extraCost;
    private int maxTarget;
    private String typeVisibility;
    private boolean peekingRequired;
    private boolean cardinalDirection;
    private boolean sameDamage;
    private int[] damage;
    private int[] mark;
    private int maxMovementTarget;
    private int maxMovementPlayer;
    private int minDistanceTarget;
    private int maxDistanceTarget;

    public Shot(Shot shot) {
        this.nameEffect = shot.nameEffect;
        this.extraCost = shot.extraCost;
        this.maxTarget = shot.maxTarget;
        this.peekingRequired = shot.peekingRequired;
        this.typeVisibility = shot.typeVisibility;
        this.cardinalDirection = shot.cardinalDirection;
        this.sameDamage = shot.sameDamage;
        this.damage = shot.damage;
        this.mark = shot.mark;
        this.maxMovementTarget = shot.maxMovementTarget;
        this.maxMovementPlayer = shot.maxMovementPlayer;
        this.minDistanceTarget = shot.minDistanceTarget;
        this.maxDistanceTarget = shot.maxDistanceTarget;
    }

    public Cubes getExtraCost(){
        return this.extraCost;
    }

    public String getTypeVisibility() {
        return this.typeVisibility;
    }

    public int getMinDistanceTarget(){
        return this.minDistanceTarget;
    }

    public int getMaxDistanceTarget() {
        return this.maxDistanceTarget;
    }

    public void setMaxTarget(int maxTarget){
        this.maxTarget = maxTarget;
    }

    public int getMaxTarget(){
        return this.maxTarget;
    }

    public int getMaxMovementTarget(){
        return this.maxMovementTarget;
    }

    public int getMaxMovementPlayer(){
        return this.maxMovementPlayer;
    }

    public void setMaxMovementTarget(int maxMovementTarget){
        this.maxMovementTarget = maxMovementTarget;
    }

    public void setMaxMovementPlayer(int maxMovementPlayer){
        this.maxMovementPlayer = maxMovementPlayer;
    }

    @Override
    public String toString() {
        return "Name: " + this.nameEffect + "\n" + "Extra cost: " + this.extraCost;
    }

    public String getNameEffect(){
        return this.nameEffect;
    }

}