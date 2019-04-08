package it.polimi.se2019.controller;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;

public class Controller {
    //references to the player who's performing actions and the match state
    ArrayList<Player> player;
    ArrayList <Match> match;
    //each player draws two cards in their first turn and discard one
    public void startingDraw(){

    }
    //it places the player figure back on the board by drawing a powerup card
    public void resurrect(){

    }
    //player's movement action, up to three tiles (standard)
    public void movement(){

    }
    //player's movement&grab action, up to 1 tile and grabbing a weapon or ammo.
    public void moveGather(){

    }
    //selecting a weapon among the three available in the resurrection room (when buying) or before shooting.
    public void chooseWeapon(){

    }

    //player's shooting ability
    public void shoot(){

    }
    //a weapon is reloaded by paying its cost,
    public void reloadWeapons(){

    }
    //it fills back weapons and ammo cards grabbed during the previous turn
    public void restoreMap(){

    }
    //the view is updated each time a change occurs.
    public void updateView(){

    }
    //at the end of each turn the game has to perform several tasks: refilling
    //the weapons slot, ammo and resurrecting players killed.
    public void endTurn(){

    }

}