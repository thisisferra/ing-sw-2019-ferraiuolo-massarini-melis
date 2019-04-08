package it.polimi.se2019.model.cards;

import it.polimi.se2019.model.game.Cubes;
import it.polimi.se2019.model.player.Player;
import it.polimi.se2019.model.map.Square;
import java.util.ArrayList;

public class Shot {
    private boolean notUsed;
    private Cubes cost;
    private int damage;
    private int tags;
    private boolean peekingRequired;
    private int movesRequired;
    private int movesGranted;
    private boolean stepByStep;
    private boolean cardinalDirectionrequired;
    private int targetableEnemies;
    private int shockDisplacement;

    public void getInfo(){

    }
    //return the list of player apporachable from a given square
    public ArrayList<Player> approachable(Square from){
        ArrayList<Player> to = null;
        return to;
    }
    //apply the effect related to the weapon
    public void use(){

    }
}