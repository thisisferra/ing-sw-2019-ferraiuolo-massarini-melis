package it.polimi.se2019.client.view;

import it.polimi.se2019.client.controller.Client;
import it.polimi.se2019.server.controller.Controller;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.map.Square;

import java.util.ArrayList;


public class View {

    private String username;
    private int points;
    private int deaths;
    private int damage;
    private int position;
    private boolean canMove = false;
    private Cubes cubes = new Cubes(2,2, 2);
    private ArrayList<Square> reacheableSquare = new ArrayList<>();


    public View(String username) {
        this.username = username;
        this.deaths = 0;
        this.position = 5;
        this.damage = 0;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPosition() {
        return this.position;
    }

    public void setReachableSquare(ArrayList<Square> reacheableSquare) {
        this.reacheableSquare.clear();
        this.reacheableSquare = reacheableSquare;
    }

    public ArrayList<Square> getReachableSquare() {
        return reacheableSquare;
    }

    public boolean getCanMove() {
        return this.canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    //TODO non so perché ma non vanno sti pezzi di fango
    /*
    public String getRedCubes() {
        return Integer.toString(this.cubes.getReds());
    }

    public String getYellowCubes() {
        return Integer.toString(this.cubes.getYellows());
    }

    public String getBlueCubes() {
        return Integer.toString(this.cubes.getBlues());
    }
     */

    //the number of point of the current player
    public void showPoints(){

    }

    //the number of marks
    public void showMarks(){

    }

    //the number of cubes of all players
    public void showCubes(){

    }

    //the position of each player on the battlefield
    public void showPlayers(){

    }

    //the w.json owned or on the field
    public void showWeapons(){

    }

    //the current powerups owned
    public void showPowerUps(){

    }
}
