package it.polimi.se2019.model.game;

import it.polimi.se2019.model.map.Square;
import java.util.*;

public class MovementChecker {
    private Square[] allSquares ;
    private ArrayList<Square> reachableSquares = new ArrayList<>();
    private int steps;
    private int index;

    public MovementChecker(Square[] squareList,int steps,int index){
        this.allSquares = Arrays.copyOf(squareList,squareList.length);
        this.steps=steps;
        this.index=index;
    }


    public void check(){
        int i =0;
        Square newSquare;
        reachableSquares.add(allSquares[index]);

        while(reachableSquares.get(i).getStep()<this.steps){

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
        for(Square object: reachableSquares) System.out.println(object.toString());
    }
    public ArrayList<Square> getReachableSquares(){
        return this.reachableSquares;
    }


}
