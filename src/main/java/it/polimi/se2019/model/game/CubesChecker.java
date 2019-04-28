package it.polimi.se2019.model.game;

public class CubesChecker {

    Cubes cubesStored;
    Cubes cubesBeingPayed;

    public CubesChecker(Cubes cubesStored, Cubes cubesBeingPayed){
        this.cubesStored = cubesStored;
        this.cubesBeingPayed = cubesBeingPayed;
    }

    //the price can be payed if the cubes owned are greater or equal of the cubes the player
    // has to pay
    // if the player can pay it return true, else it return false
    public boolean check(){
        if(cubesStored.getYellows() >= cubesBeingPayed.getYellows() &&
                cubesStored.getBlues() >= cubesBeingPayed.getBlues() &&
                    cubesStored.getReds() >= cubesBeingPayed.getReds() )
            return true;
        else return false;
    }
    public Cubes subtract(){
        return new Cubes((this.cubesStored.getReds()-this.cubesBeingPayed.getReds()),
                    (cubesStored.getYellows()-cubesBeingPayed.getYellows()),
                        (cubesStored.getBlues()-cubesBeingPayed.getBlues()));
    }
}
