package it.polimi.se2019.model.map;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;

public  class Square {
    // position and the four cardinal direction indicates room the're adjacent with, in the square array.
    private int position = -1;
    private int north = -1;
    private int east = -1;
    private int south = -1;
    private int west = -1;
    private int step=0;
    private String color;
    private boolean full;          //bool that indicates if square is full or not
    private boolean spawnPoint;      //bool that indicates if square is a spawn point or not
    private boolean visited;
    private ArrayList<Square> roomSquares;
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
    public void setFull() {
        this.full = true;
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
    public void restock(){
        if (!this.full && !this.spawnPoint) {
            this.setFull();
        }
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
    public void setStep(int step){
        this.step=step;
    }
    public int getStep(){
        return this.step;
    }
    @Override
    public String toString(){
        return "Cella: "+ this.getPosition();
    }

}
