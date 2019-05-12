package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.map.Square;

import java.util.ArrayList;

public class Shot {
    /*private boolean notUsed;
    private Cubes cost;
    private int damage;
    private int tags;
    private boolean peekingRequired;                    //Se posso vedere un giocatore
    private int movesRequired;                          //Distanza minima a cui deve essere il target
    private int movesGranted;                           //Devi muoverti fino a un massimo di movesGranted quadrati
    private boolean stepByStep;                         //2° effetto cyberguanto
    private boolean cardinalDirectionRequired;          //Se l'arma spara in direzioni cardinali
    private int targetableEnemies;                      //Numero massimo di nemici danneggaibili
    private int shockDisplacement;                      //Spostamento dell'avversario, se positivo lo spingi, se negativo lo tiri*/

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

    public String toString() {
        return "Name: " + this.nameEffect + "\n" + "Extra cost: " + this.extraCost;
    }


    /*public void getInfo(){

    }
    //return the list of player approachable from a given square
    public ArrayList<Player> approachable(Square from){
        return new ArrayList<Player>();
    }
    //apply the effect related to the weapon
    public void use(){

    }

    public boolean isCardinalDirectionRequired() {
        return cardinalDirectionRequired;
    }

    public boolean isNotUsed() {
        return notUsed;
    }

    public boolean isPeekingRequired() {
        return peekingRequired;
    }

    public boolean isStepByStep() {
        return stepByStep;
    }

    public Cubes getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getMovesGranted() {
        return movesGranted;
    }

    public int getMovesRequired() {
        return movesRequired;
    }

    public int getShockDisplacement() {
        return shockDisplacement;
    }

    public int getTags() {
        return tags;
    }

    public int getTargetableEnemies() {
        return targetableEnemies;
    }*/
}