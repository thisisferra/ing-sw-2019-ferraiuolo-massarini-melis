package it.polimi.se2019.server.model.game;

public class CubesChecker {

    /**
     * CubesChecker class used to check if cubes can be paid, based on the amount of cubes owned and due.
     * @author mattiamassarini.
     */
    private Cubes cubesStored;
    private Cubes cubesBeingPayed;

    /**
     * Constructor of CubesChecker.
     * @param cubesStored cubes the player has.
     * @param cubesBeingPayed cubes the player has to pay.
     */
    public CubesChecker(Cubes cubesStored, Cubes cubesBeingPayed){
        this.cubesStored = cubesStored;
        this.cubesBeingPayed = cubesBeingPayed;
    }

    /**
     * Check if the cubes can be paid.
     * @return true if the price can be paid, false otherwise.
     */
    public boolean check(){
        return(cubesStored.getReds() >= cubesBeingPayed.getReds() &&
                cubesStored.getYellows() >= cubesBeingPayed.getYellows() &&
                    cubesStored.getBlues() >= cubesBeingPayed.getBlues() );
    }
}
