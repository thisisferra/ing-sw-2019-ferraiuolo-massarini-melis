package it.polimi.se2019.server.model.map;

import it.polimi.se2019.server.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class Square implements Serializable {
    // position and the four cardinal direction indicates squares they're adjacent with, in the square array.
    private int position = -1;
    private int north = -1;
    private int east = -1;
    private int south = -1;
    private int west = -1;
    private int northWall = -1;
    private int eastWall = -1;
    private int southWall = -1;
    private int westWall = -1;
    private int step=0;
    private String color;
    private boolean full;          //bool that indicates if square is full or not
    private boolean spawnPoint;      //bool that indicates if square is a spawn point or not
    private boolean visited;
    private ArrayList<Square> hammingSquare;
    private ArrayList<Square> axialSquares;
    private ArrayList<Square> doors;
    private ArrayList<Player> playersOn;

    public void setVisited(boolean visited){
        this.visited= visited;
    }
    public boolean getVisited(){
        return this.visited;
    }
    public boolean isFull(){
        return this.full;
    }
    public String getColor(){
        return this.color;
    }
    public boolean isSpawnPoint(){
        return this.spawnPoint;
    }
    public int getPosition(){
        return this.position;
    }
    public int getNorth(){
        return this.north;
    }
    public int getSouth(){
        return this.south;
    }
    public int getEast(){
        return this.east;
    }
    public int getWest(){
        return this.west;
    }
    public int getNorthWall(){
        return this.northWall;
    }
    public int getEastWall(){
        return this.eastWall;
    }
    public int getSouthWall(){
        return this.southWall;
    }
    public int getWestWall(){
        return this.westWall;
    }
    public void setStep(int step){
        this.step=step;
    }
    public void setColor(String color){
        this.color=color;
    }
    public void setFull(boolean full){
        this.full=full;
    }
    public void restock(){
        if (!this.full && !this.spawnPoint) {
            this.setFull(true);
        }
    }
    public void setPosition(int position){
        this.position=position;
    }
    public void setNorth(int north){
        this.north=north;
    }
    public void setEast(int east){
        this.east=east;
    }
    public void setSouth(int south){
        this.south=south;
    }
    public void setWest(int west){
        this.west=west;
    }
    public void setNorthWall(int northWall){
        this.northWall=northWall;
    }
    public void setEastWall(int eastWall){
        this.eastWall=eastWall;
    }
    public void setSouthWall(int southWall){
        this.southWall=southWall;
    }
    public void setWestWall(int westWall){
        this.westWall=westWall;
    }

    public void setSpawnPoint(boolean spawnPoint){
        this.spawnPoint=spawnPoint;
    }

    public Square(Square square){
        this.spawnPoint=square.spawnPoint;
        this.position=square.position;
        this.step=square.step;
        this.color=square.color;
        this.visited=square.visited;
        this.north=square.north;
        this.east=square.east;
        this.south=square.south;
        this.west=square.west;
        this.full=square.full;
        this.northWall = square.northWall;
        this.eastWall = square.eastWall;
        this.southWall = square.southWall;
        this.westWall = square.westWall;
    }
    public int getStep(){
        return this.step;
    }

    @Override
    public String toString(){
        return "S: " + this.getPosition() + " Color: " + this.color;
    }

}
