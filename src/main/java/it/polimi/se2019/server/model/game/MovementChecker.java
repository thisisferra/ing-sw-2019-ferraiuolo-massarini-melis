package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.map.Square;
import java.io.Serializable;
import java.util.*;

/**
 * MovementChecker class implements the movement game mechanic.
 * Given a Map object, an index and a steps field, it checks
 * all the squares reachable from a certain position, within a range
 * defined by the steps field.
 * The class uses a Breadth First Search algorithm in order to retrieve the
 * reachable squares.
 * @author mattiamassarini.
 */
public class MovementChecker implements Serializable {
    private Square[] allSquares ;
    private ArrayList<Square> reachableSquares = new ArrayList<>();
    private int steps;
    private int index;
    private int validSquares;

    /**
     * Constructor of MovementChecker class.
     * @param squareList the square list of the map
     * @param steps the maximum number of steps.
     * @param index the square index representing the starting point.
     */
    public MovementChecker(Square[] squareList,int steps,int index){
        allSquares = new Square[squareList.length];
        int valid =0;
        for(int i=0; i<allSquares.length;i++){
            allSquares[i] = new Square(squareList[i]);
            if(!squareList[i].getColor().equals(""))
                valid++;
        }
        this.validSquares = valid;
        this.steps=steps;
        this.index=index;
        this.check();
    }


    /**
     * Using a BFS algorithm, starting from a given square, the method saves in reachableSquares field all
     * the square reachable by the player within a number of steps defined in the steps field.
     * Once a square in discovered the field visited is marked, and it will be ignored in the following
     * computation. The algorithm stops when all the squares are visited, or when reaching the number
     * of steps admissible.
     */
    private void check(){
        int i =0;
        Square newSquare;
        reachableSquares.add(allSquares[index]);

        while(reachableSquares.get(i).getStep()<this.steps && reachableSquares.size() < validSquares){
            if(reachableSquares.get(i).getNorth()>=0 && !allSquares[reachableSquares.get(i).getNorth()].getVisited()) {
                newSquare = new Square(allSquares[reachableSquares.get(i).getNorth()]);
                newSquare.setStep(reachableSquares.get(i).getStep()+1);
                allSquares[newSquare.getPosition()].setVisited(true);
                reachableSquares.add(newSquare);

            }
            if(reachableSquares.get(i).getEast()>=0 && !allSquares[reachableSquares.get(i).getEast()].getVisited()) {
                newSquare = new Square(allSquares[reachableSquares.get(i).getEast()]);
                newSquare.setStep(reachableSquares.get(i).getStep()+1);
                allSquares[newSquare.getPosition()].setVisited(true);
                reachableSquares.add(newSquare);

            }
            if(reachableSquares.get(i).getSouth()>=0 && !allSquares[reachableSquares.get(i).getSouth()].getVisited()) {
                newSquare = new Square(allSquares[reachableSquares.get(i).getSouth()]);
                newSquare.setStep(reachableSquares.get(i).getStep()+1);
                allSquares[newSquare.getPosition()].setVisited(true);
                reachableSquares.add(newSquare);

            }
            if(reachableSquares.get(i).getWest()>=0 && !allSquares[reachableSquares.get(i).getWest()].getVisited()) {
                newSquare = new Square(allSquares[reachableSquares.get(i).getWest()]);
                newSquare.setStep(reachableSquares.get(i).getStep()+1);
                allSquares[newSquare.getPosition()].setVisited(true);
                reachableSquares.add(newSquare);

            }
            allSquares[reachableSquares.get(i).getPosition()].setVisited(true);
            i++;
        }
        reachableSquares.sort(Comparator.comparing(Square::getPosition));
        //sort square list from the lowest position to the highest
        for(Square object: reachableSquares){
            object.setStep(0);
            object.setVisited(false);
        }
    }

    /**
     * Getter of the reachableSquares field.
     * @return it returns the list of squares reachable.
     */
    public ArrayList<Square> getReachableSquares(){
        return this.reachableSquares;
    }

    //return the list of squares from your position to the first right wall

    /**
     * Find all the squares a player can walk heading east until he finds a wall.
     * @return all the squares the player can cross walking right.
     */
    public ArrayList<Square> getWalkableRightSquares(){
        ArrayList<Square> rightHorizontalSquares = new ArrayList<>();
        int next = allSquares[index].getEast();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            rightHorizontalSquares.add(allSquares[next]);
            next = allSquares[next].getEast();
            moves++;
        }
        return rightHorizontalSquares;
    }

    /**
     * Find all the squares a player can walk heading west until he finds a wall.
     * @return all the squares the player can cross walking left.
     */
    public ArrayList<Square> getWalkableLeftSquares(){
        ArrayList<Square> leftHorizontalSquares = new ArrayList<>();
        int next = allSquares[index].getWest();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            leftHorizontalSquares.add(allSquares[next]);
            next = allSquares[next].getWest();
            moves++;
        }
        return leftHorizontalSquares;

    }

    /**
     * Find all the squares a player can walk heading north until he finds a wall.
     * @return all the squares the player can cross walking upwards.
     */    public ArrayList<Square> getWalkableUpwardsSquares(){
        ArrayList<Square> upwardsSquares = new ArrayList<>();
        int next = allSquares[index].getNorth();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            upwardsSquares.add(allSquares[next]);
            next = allSquares[next].getNorth();
            moves++;
        }
        return upwardsSquares;
    }

    /**
     * Find all the squares a player can walk heading south until he finds a wall.
     * @return all the squares the player can cross walking downwards.
     */    public ArrayList<Square> getWalkableDownwardsSquares(){
        ArrayList<Square> downwardsSquares = new ArrayList<>();
        int next = allSquares[index].getSouth();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            downwardsSquares.add(allSquares[next]);
            next = allSquares[next].getSouth();
            moves++;
        }
        return downwardsSquares;
    }

    /**
     * Find all the squares heading north ignoring walls.
     * @return all the squares upwards.
     */
    public ArrayList<Square> getAllUpwardsSquares(){
        ArrayList<Square> upwardsSquares = new ArrayList<>();
        int next = allSquares[index].getNorthWall();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            upwardsSquares.add(allSquares[next]);
            next = allSquares[next].getNorthWall();
            moves++;
        }
        return upwardsSquares;
    }

    /**
     * Find all the squares heading east ignoring walls.
     * @return all the squares on the right.
     */
    public ArrayList<Square> getAllRightSquares(){
        ArrayList<Square> rightSquares = new ArrayList<>();
        int next = allSquares[index].getEastWall();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            rightSquares.add(allSquares[next]);
            next = allSquares[next].getEastWall();
            moves++;
        }
        return rightSquares;
    }

    /**
     * Find all the squares heading south ignoring walls.
     * @return all the squares downward.
     */
    public ArrayList<Square> getAllDownwardsSquares(){
        ArrayList<Square> downwardsSquares = new ArrayList<>();
        int next = allSquares[index].getSouthWall();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            downwardsSquares.add(allSquares[next]);
            next = allSquares[next].getSouthWall();
            moves++;
        }
        return downwardsSquares;
    }

    /**
     * Find all the squares heading west ignoring walls.
     * @return all the squares on the left.
     */
    public ArrayList<Square> getAllLeftSquares(){
        ArrayList<Square> leftSquares = new ArrayList<>();
        int next = allSquares[index].getWestWall();
        int moves = 0;
        while(next != -1 && moves<this.steps){
            leftSquares.add(allSquares[next]);
            next = allSquares[next].getWestWall();
            moves++;
        }
        return leftSquares;
    }

}
