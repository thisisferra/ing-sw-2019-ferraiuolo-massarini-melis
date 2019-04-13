package it.polimi.se2019.model.game;
import it.polimi.se2019.model.map.Square;

import java.util.*;

public class MovementChecker {
    private Square[] allSquares ;
    private ArrayList<Square> reachableSquares = new ArrayList<Square>();
    private int steps;
    private int index;

    public MovementChecker(Square[] squareList,int steps,int index){
        this.allSquares = Arrays.copyOf(squareList,squareList.length);
        this.steps=steps;
        this.index=index;
    }

    /*public void check(){
        int i=0;
        int k=(int)Math.pow(4.0, this.steps-1);
        int l= 1;
        System.out.println(k);
        reachableSquares.add(allSquares[this.index]);
        Square dummySquare = new Square();
        while(i<k){
            if(reachableSquares.get(i).getNorth()>=0 ) reachableSquares.add(allSquares[reachableSquares.get(i).getNorth()]);
            else reachableSquares.add(dummySquare);
            if(reachableSquares.get(i).getEast()>=0 ) reachableSquares.add(allSquares[reachableSquares.get(i).getEast()]);
            else reachableSquares.add(dummySquare);
            if(reachableSquares.get(i).getSouth()>=0 ) reachableSquares.add(allSquares[reachableSquares.get(i).getSouth()]);
            else reachableSquares.add(dummySquare);
            if(reachableSquares.get(i).getWest()>=0 ) reachableSquares.add(allSquares[reachableSquares.get(i).getWest()]);
            else reachableSquares.add(dummySquare);
            i++;
            System.out.println(l + ":" +reachableSquares);
            l++;
        }
        Set<Square> set = new HashSet<>(reachableSquares);

        reachableSquares.clear();
        reachableSquares.addAll(set);
        reachableSquares.removeIf(obj-> obj.getPosition() == this.index);
        reachableSquares.sort(Comparator.comparing(Square::getPosition));

    }*/

    public void check(){
        int i =0;
        while(reachableSquares.get(i).getStep()<this.steps-1){
            if(reachableSquares.get(i).getNorth()>=0 ) {
                reachableSquares.add(allSquares[reachableSquares.get(i).getNorth()]);
                allSquares[reachableSquares.get(i).getNorth()].setStep();
            }
            if(reachableSquares.get(i).getEast()>=0 ) reachableSquares.add(allSquares[reachableSquares.get(i).getEast()]);
            if(reachableSquares.get(i).getSouth()>=0 ) reachableSquares.add(allSquares[reachableSquares.get(i).getSouth()]);
            if(reachableSquares.get(i).getWest()>=0 ) reachableSquares.add(allSquares[reachableSquares.get(i).getWest()]);
            i++;
            System.out.println(reachableSquares);

        }
    }
    public ArrayList<Square> getReachableSquares(){
        return this.reachableSquares;
    }


}
