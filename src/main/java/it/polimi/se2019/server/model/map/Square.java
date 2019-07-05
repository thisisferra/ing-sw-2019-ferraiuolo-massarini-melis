package it.polimi.se2019.server.model.map;

import org.json.simple.JSONObject;
import java.io.Serializable;

/**
 * Square class represent the real game square counterpart, where the character moves on.
 * Each square is uniquely defined by its position.
 * Each square contains 8 field describing if the cell facing a cardinal direction is available or not for moving or shooting.
 * 4 of them consider walls, the others 4 do not.
 * Each square can be a spawn point or not, has a color and can be full if a ammo is available to being picked up.
 * @author mattiamassarini,merklind,thisisferra.
 */
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

    /**
     * Setter of the visited field.
     * @param visited true if the square has been visited, false otherwise.
     */
    public void setVisited(boolean visited){
        this.visited= visited;
    }

    /**
     * Getter of the visited field.
     * @return true if the square has been visited, false otherwise.
     */
    public boolean getVisited(){
        return this.visited;
    }

    /**
     * Getter of the full field.
     * @return true if an ammo is available, false otherwise.
     */
    public boolean isFull(){
        return this.full;
    }

    /**
     * Getter of the color field.
     * @return the square color.
     */
    public String getColor(){
        return this.color;
    }

    /**
     * Getter of the spawnPoint field.
     * @return true if the square is a spawnpoint, false otherwise.
     */
    public boolean isSpawnPoint(){
        return this.spawnPoint;
    }

    /**
     * Getter of the position field.
     * @return the position of the square.
     */
    public int getPosition(){
        return this.position;
    }

    /**
     * Getter of the north field.
     * @return the square located north from this square,
     * -1 if there's a wall or a square doesn't exist.
     */
    public int getNorth(){
        return this.north;
    }

    /**
     * Getter of the south field.
     * @return the square located south from this square,
     * -1 if there's a wall or a square doesn't exist.
     */
    public int getSouth(){
        return this.south;
    }

    /**
     * Getter of the east field.
     * @return the square located east from this square,
     * -1 if there's a wall or a square doesn't exist.
     */
    public int getEast(){
        return this.east;
    }

    /**
     * Getter of the west field.
     * @return the square located west from this square,
     * -1 if there's a wall or a square doesn't exist.
     */
    public int getWest(){
        return this.west;
    }

    /**
     * Getter of the northWall field.
     * @return it returns the square located north from this square.
     */
    public int getNorthWall(){
        return this.northWall;
    }

    /**
     * Getter of the eastWall field.
     * @return the square located east from this square
     */
    public int getEastWall(){
        return this.eastWall;
    }

    /**
     * Getter of the southWall field.
     * @return the square located south from this square.
     */
    public int getSouthWall(){
        return this.southWall;
    }

    /**
     * Getter of the westWall field.
     * @return the square located west from this square.
     */
    public int getWestWall(){
        return this.westWall;
    }

    /**
     * Setter of the step field.
     * @param step the steps needed in order to arrive to this square.
     */
    public void setStep(int step){
        this.step=step;
    }

    /**
     * Setter of the color field.
     * @param color can be red,blue,yellow,white,purple,green.
     */
    public void setColor(String color){
        this.color=color;
    }

    /**
     * Setter of the full field.
     * @param full true if there is an ammo on the square, false otherwise.
     */
    public void setFull(boolean full){
        this.full=full;
    }

    /**
     * It refill the square if it is empty and it isn't a spawn point.d
     */
    public void restock(){
        if (!this.full && !this.spawnPoint) {
            this.setFull(true);
        }
    }

    /**
     * Setter of the position field.
     * @param position the uniquely position of the square.
     */
    public void setPosition(int position){
        this.position=position;
    }

    /**
     * Setter of the north field.
     * @param north the number of the square facing north from this square,
     *              it's -1 if there is a wall between.
     */
    public void setNorth(int north){
        this.north=north;
    }
    /**
     * Setter of the east field.
     * @param east the number of the square facing east from this square,
     *              it's -1 if there is a wall between.
     */
    public void setEast(int east){
        this.east=east;
    }
    /**
     * Setter of the south field.
     * @param south the number of the square facing south from this square,
     *              it's -1 if there is a wall between.
     */
    public void setSouth(int south){
        this.south=south;
    }
    /**
     * Setter of the west field.
     * @param west the number of the square facing west from this square,
     *              it's -1 if there is a wall between.
     */
    public void setWest(int west){
        this.west=west;
    }

    /**
     * Setter of the northWall field.
     * @param northWall the number position of the square facing north from this square,
     *                  ignoring walls.
     */
    public void setNorthWall(int northWall){
        this.northWall=northWall;
    }
    /**
     * Setter of the eastWall field.
     * @param eastWall the number position of the square facing east from this square,
     *                  ignoring walls.
     */
    public void setEastWall(int eastWall){
        this.eastWall=eastWall;
    }
    /**
     * Setter of the southWall field.
     * @param southWall the number position of the square facing south from this square,
     *                  ignoring walls.
     */
    public void setSouthWall(int southWall){
        this.southWall=southWall;
    }
    /**
     * Setter of the westWall field.
     * @param westWall the number position of the square facing west from this square,
     *                  ignoring walls.
     */
    public void setWestWall(int westWall){
        this.westWall=westWall;
    }

    /**
     * Constructor of the Square class.
     * @param square the square that has to be copied
     */
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

    /**
     * Constructor of the Square class, without parameters.
     */
    public Square() {
        //Needed for resuming a square from saved match
    }

    /**
     * Getter of the step field.
     * @return the number of steps required to arrive to this position.
     */
    public int getStep(){
        return this.step;
    }

    @Override
    public String toString(){
        return  this.getPosition() + " " + this.color;
    }

    /**
     * Overridden equals method, used to confronting two squares.
     * @param o the square to be compared.
     * @return true if the two object are equals, false otherwise.
     */
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
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + position;
        return result;
    }



    /**
     * It saves the square state into a JSONObject object.
     * @return the JSONObject object containing the information.
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

    /**
     * Restore the Square object from a JSONObject object.
     * @param squareToResume the JSONObject object to be restored.
     * @return the square restored.
     */
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
