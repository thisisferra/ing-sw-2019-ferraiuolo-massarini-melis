package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.game.Cubes;
import java.io.Serializable;

/**
 * Shot class describe the weapon effect.
 */
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

    /**
     * Constructor of Shot class.
     * @param shot shot to be copied.
     */
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

    /**
     * Getter of the extraCost field.
     * @return the cost in cubes of the effect.
     */
    public Cubes getExtraCost(){
        return this.extraCost;
    }

    /**
     * Getter of the typeVisibility field.
     * @return the type of visibility.
     */
    public String getTypeVisibility() {
        return this.typeVisibility;
    }

    /**
     * Getter of the minDistanceTarget field.
     * @return the minimum number of squares
     * in order to hit the enemy with the effect.
     */
    public int getMinDistanceTarget(){
        return this.minDistanceTarget;
    }

    /**
     * Getter of the maxDistanceTarget.
     * @return the maximum number of squares
     * in order to hit the enemy with the effect.
     */
    public int getMaxDistanceTarget() {
        return this.maxDistanceTarget;
    }

    /**
     * Setter of the maxTarget field.
     * @param maxTarget the max number of opponents
     *                  the effect can hit.
     */
    public void setMaxTarget(int maxTarget){
        this.maxTarget = maxTarget;
    }

    /**
     * Getter of the maxTarget field.
     * @return the maximum number of targets
     * the effect can hit.
     */
    public int getMaxTarget(){
        return this.maxTarget;
    }

    /**
     * Getter fo the maxMovementTarget.
     * @return the maximum movement the
     * player can push the enemy away.
     */
    public int getMaxMovementTarget(){
        return this.maxMovementTarget;
    }

    /**
     * Getter of the maxMovementPlayer.
     * @return the maximum movement the player can
     * do after using the effect.
     */
    public int getMaxMovementPlayer(){
        return this.maxMovementPlayer;
    }

    /**
     * Setter of the maxMovementPlayer.
     * @param maxMovementTarget the maximum movement the player can
     * do after using the effect.
     */
    public void setMaxMovementTarget(int maxMovementTarget){
        this.maxMovementTarget = maxMovementTarget;
    }

    /**
     * Setter fo the maxMovementTarget.
     * @param maxMovementPlayer the maximum movement the
     * player can push the enemy away.
     */
    public void setMaxMovementPlayer(int maxMovementPlayer){
        this.maxMovementPlayer = maxMovementPlayer;
    }

    @Override
    public String toString() {
        return "Name: " + this.nameEffect + "\n" + "Extra cost: " + this.extraCost;
    }

    /**
     * Getter of the nameEffect field.
     * @return the name of the effect.
     */
    public String getNameEffect(){
        return this.nameEffect;
    }

}