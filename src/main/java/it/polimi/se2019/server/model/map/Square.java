package it.polimi.se2019.server.model.map;

import it.polimi.se2019.server.model.player.Player;
import org.json.simple.JSONObject;

import java.io.Serializable;

public class Square implements Serializable {
    // position and the four cardinal direction indicates squares they're adjacent with, in the square array.
    private int position;
    private int north;
    private int east;
    private int south;
    private int west;
    private int northWall;
    private int eastWall;
    private int southWall;
    private int westWall;
    private int step=0;
    private String color;
    private boolean full;          //bool that indicates if square is full or not
    private boolean spawnPoint;      //bool that indicates if square is a spawn point or not
    private boolean visited;

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

    public Square() {
        //Needed for resuming a square from saved match
    }

    public int getStep(){
        return this.step;
    }

    @Override
    public String toString(){
        return  this.getPosition() + " " + this.color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this){
            return true;
        }

        if (!(o instanceof Square)) {
            return false;
        }

        Square square = (Square) o;

        return this.getPosition() == ((Square) o).getPosition();
    }
    //TODO CHECKTHIS
    /*
    @Override
    public int hashCode() {
        return hashCode();
    }

     */

    public JSONObject toJSON() {
        JSONObject squareJson = new JSONObject();

        squareJson.put("position", this.getPosition());
        squareJson.put("north", this.getNorth());
        squareJson.put("east", this.getEast());
        squareJson.put("south", this.getSouth());
        squareJson.put("west", this.getWest());

        squareJson.put("northWall", this.getNorthWall());
        squareJson.put("eastWall", this.getEastWall());
        squareJson.put("southWall", this.getSouthWall());
        squareJson.put("westWall", this.getWestWall());

        squareJson.put("step", this.getStep());
        squareJson.put("color", this.getColor());
        squareJson.put("full", this.isFull());
        squareJson.put("spawnPoint", this.isSpawnPoint());
        squareJson.put("visited", this.getVisited());

        return squareJson;
    }

    public static Square resumeSquare(JSONObject squareToResume) {
        Square resumedSquare = new Square();

        resumedSquare.position = ((Number) squareToResume.get("position")).intValue();
        resumedSquare.north = ((Number) squareToResume.get("north")).intValue();
        resumedSquare.east = ((Number) squareToResume.get("east")).intValue();
        resumedSquare.south = ((Number) squareToResume.get("south")).intValue();
        resumedSquare.west = ((Number) squareToResume.get("west")).intValue();

        resumedSquare.northWall = ((Number) squareToResume.get("northWall")).intValue();
        resumedSquare.eastWall = ((Number) squareToResume.get("eastWall")).intValue();
        resumedSquare.southWall = ((Number) squareToResume.get("southWall")).intValue();
        resumedSquare.westWall = ((Number) squareToResume.get("westWall")).intValue();

        resumedSquare.step = ((Number) squareToResume.get("step")).intValue();
        resumedSquare.color = (String) squareToResume.get("color");
        resumedSquare.full = (boolean) squareToResume.get("full");
        resumedSquare.spawnPoint = (boolean) squareToResume.get("spawnPoint");
        resumedSquare.visited = (boolean) squareToResume.get("visited");

        return resumedSquare;
    }
}
