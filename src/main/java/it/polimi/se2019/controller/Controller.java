package it.polimi.se2019.controller;
import it.polimi.se2019.model.cards.PowerUp;
import it.polimi.se2019.model.cards.Weapon;
import it.polimi.se2019.model.game.Cubes;
import it.polimi.se2019.model.game.CubesChecker;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.game.MovementChecker;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    //references to the player who's performing actions and the match state
    private Player player;
    private Match match;

    //each player draws two cards in their first turn and discard one
    public void startingDraw(){
        //TODO da togliere index
        int index = 1;
        this.player.getHand().addPowerUp(match.pickUpPowerUp());
        this.player.getHand().addPowerUp(match.pickUpPowerUp());
        PowerUp discarded = this.player.getHand().chooseToDiscard(index);
        if(discarded.getColor().equals("red")) this.player.setPosition(4);
        else if (discarded.getColor().equals("yellow")) this.player.setPosition(11);
        else if (discarded.getColor().equals("blue")) this.player.setPosition(2);
        else System.out.println(discarded.getColor() + "is not a valid color for respawning!");

    }

    //it places the player figure back on the board by drawing a powerup card
    public void resurrect(){
        String color = this.match.pickUpPowerUp().getColor();
        if(color.equals("red")) this.player.setPosition(4);
        else if (color.equals("yellow")) this.player.setPosition(11);
        else if (color.equals("blue")) this.player.setPosition(2);
        else System.out.println(color + "is not a valid color for respawning!");
    }

    //player's movement action, up to three tiles (standard)
    public void movement(){

        MovementChecker movement = new MovementChecker(this.match.getMap().getAllSquare(),3,this.player.getPosition());
        movement.check();
        ArrayList<Square> availableSquares = movement.getReachableSquares();
        //update view
        //receives an input from view about the new position
        //if new position is in availableSquares --> set new position

    }
    //player's movement&grab action, up to 1 tile and grabbing a weapon or ammo.
    public void moveGather(){
        MovementChecker movement = new MovementChecker((this.match.getMap().getAllSquare()),1,this.player.getPosition());
        movement.check();
        ArrayList<Square> availableSquares = movement.getReachableSquares();
        Square currentSquare;
        //update view
        //receives an input from view about the new position
        //if new position is in availableSquares --> set new position
        for(Square object : availableSquares)
            if(object.getPosition() == player.getPosition()){
                player.pickUpAmmo(object,this.match);
                //pickUpAmmo should be generalized to include weapons as well
            }
         //update view with movement, ammo tile or weapon changes


    }
    //selecting a weapon among the three available in the resurrection room (when buying) or before shooting.
    public void chooseWeapon(){

    }

    //player's shooting ability
    public void shoot(){

    }
    //a weapon is reloaded by paying its cost,
    public void reloadWeapons(){
        Weapon weaponBeingReloaded;
        ArrayList<Weapon> weaponList;
        weaponList = player.getHand().getWeapons();
        //ask index to client, for now it's zero
        int index = 0;
        weaponBeingReloaded = weaponList.get(index);
        CubesChecker cubesChecker = new CubesChecker(player.getPlayerBoard().getAmmoCubes(),weaponBeingReloaded.getReloadCost());
        if (cubesChecker.check()){
            weaponBeingReloaded.setLoad(true);
            player.getPlayerBoard().setAmmoCubes(cubesChecker.subtract());
        } else System.out.println(weaponBeingReloaded.getType() + " can't be reloaded.");
    }

    //it fills back weapon and ammo cards grabbed during the previous turn
    public void restoreMap(){
        for(Square object : match.getMap().getAllSquare())
                if(!object.isSpawnPoint())
                    object.setFull(true);
                else {
                    //for all arsenal's slot, if there are empty cells, refill each cabinet
                }
    }
    //the view is updated each time a change occurs.
    public void updateView(){

    }
    //at the end of each turn the game has to perform several tasks: refilling
    //the weapon slot, ammo and resurrecting players killed.
    public void endTurn(){

    }

}