package it.polimi.se2019.model.player;

import it.polimi.se2019.model.cards.PowerUp;
import it.polimi.se2019.model.game.Match;

import java.util.ArrayList;

public class Player {
    private String clientName;
    private int[] position;
    private int score;
    private String color;
    private PlayerBoard playerBoard;
    private boolean firstPlayer;
    private boolean suspended;
    private Hand playerHand;

    //it draws a card rom the powerup deck
    public void draw(ArrayList<PowerUp> deck){

    }
    public String getClientName(){
        return this.clientName;
    }

    public String getColor(){
        return this.color;
    }

    public int getScore(){
        return this.score;
    }

    public int[] getPosition(){
        return this.position;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setClientName(String clientName){
        this.clientName= clientName;
    }

}