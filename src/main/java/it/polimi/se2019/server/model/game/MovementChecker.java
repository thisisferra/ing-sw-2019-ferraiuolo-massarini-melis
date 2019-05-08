package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.map.Square;

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

    //return the list of squares from your position to the first right wall
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

    //return the list of squares from your position to the first left wall
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

    //return the list of squares from your position to the first upwards wall
    public ArrayList<Square> getWalkableUpwardsSquares(){
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

    //return the list of squares from your position to the first downwards wall
    public ArrayList<Square> getWalkableDownwardsSquares(){
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
