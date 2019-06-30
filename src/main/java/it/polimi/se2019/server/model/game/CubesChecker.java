package it.polimi.se2019.server.model.game;

public class CubesChecker {

    private Cubes cubesStored;
    private Cubes cubesBeingPayed;

    public CubesChecker(Cubes cubesStored, Cubes cubesBeingPayed){
        this.cubesStored = cubesStored;
        this.cubesBeingPayed = cubesBeingPayed;
    }

    //the price can be payed if the cubes owned are greater or equal of the cubes the player
    // has to pay
    // if the player can pay it return true, else it return false
    public boolean check(){
        return(cubesStored.getReds() >= cubesBeingPayed.getReds() &&
                cubesStored.getYellows() >= cubesBeingPayed.getYellows() &&
                    cubesStored.getBlues() >= cubesBeingPayed.getBlues() );

    }
    /*
    public Cubes subtract(){
        if(this.check()){
            return new Cubes((this.cubesStored.getReds()-this.cubesBeingPayed.getReds()),
                    (cubesStored.getYellows()-cubesBeingPayed.getYellows()),
                    (cubesStored.getBlues()-cubesBeingPayed.getBlues()));
        }
        else return null;
    }
     */
}
