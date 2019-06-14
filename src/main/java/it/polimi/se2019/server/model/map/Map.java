package it.polimi.se2019.server.model.map;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.*;

public class Map implements Serializable {
    private int mapID;
    private  Square[] allSquare;
    private ArrayList<ArrayList<Square>> roomSquare;

    public void setMapID(int mapID){
        if(mapID > 0) {
            this.mapID = mapID;
        }
        else throw new ArithmeticException("mapID cannot be negative or equal to 0");
    }
    public int getMapID(){
        return this.mapID;
    }

    //get an array of all squares of the map
    public Square[] getAllSquare(){
        return this.allSquare;
    }

    public Map(int mapID){
        this.mapID = mapID;
    }

    //based on mapID number the method parse the json file with the same id
    // filling the array allSquares with Square objects
    public void setAllSquare(){
        Gson gson = new Gson();
        try {
            if(mapID ==1) allSquare = gson.fromJson(new FileReader("./src/main/resources/map1.json"), Square[].class);
            else if(mapID ==2) allSquare = gson.fromJson(new FileReader("./src/main/resources/map2.json"), Square[].class);
                else if (mapID == 3) allSquare = gson.fromJson(new FileReader("./src/main/resources/map3.json"), Square[].class);
                    else if (mapID == 4) allSquare = gson.fromJson(new FileReader("./src/main/resources/map4.json"), Square[].class);
                        else System.out.println("Map number :"+ mapID +" doesn't exist!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // for(int i =0;i<allSquare.length;i++) System.out.println(allSquare[i].toString());
    }

    //fills roomSquare with the reference of all map squares, sorted based on their colours
    public void setRoomSquare(){

        this.roomSquare = new ArrayList<>();
        int j;

        //the main arraylist is created based on the number of colors available in the map
        for(j=0;j<colorCount();j++)
            roomSquare.add(new ArrayList<Square>());

        //iterate allSquare vector
        for(int i=0;i<allSquare.length;i++){
            //iterate the main array list
            j=0;
            //if the square is accessible
            if(!allSquare[i].getColor().equals("")){
                //iterate roomSquare outer list
                for(int k=0;k<roomSquare.size();k++){
                    //if allSquare[i] hasn't been added yet
                    if(j==0){
                        //if the first element's color of the inner list is the same as allSquare[i]
                        //OR the inner list is empty
                        //allSquare[i] is added to that inner list
                        if(roomSquare.get(k).isEmpty() || allSquare[i].getColor().equals(roomSquare.get(k).get(0).getColor())){
                            roomSquare.get(k).add(allSquare[i]);
                            //flag j is set to 1, allSquare[i] has been added already
                            j=1;
                        }
                    }
                }
            }

        }
    }

    public Square searchSquare(int position) {
        for (Square square : allSquare) {
            if (square.getPosition() == position) return square;
        }
        return null;
    }

    //return the number of different colors on the map
    public int colorCount(){

        Set <String> colorSet = new HashSet<>();
        for(Square object : this.allSquare){
            if(!object.getColor().equals(""))
                colorSet.add(object.getColor());
        }
        return colorSet.size();
    }
    public ArrayList<ArrayList<Square>> getRoomSquare(){
        return this.roomSquare;
    }
    public Square getSpecificSquare(int indexSquare) {
        return allSquare[indexSquare];
    }
}