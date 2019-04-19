package it.polimi.se2019.model.map;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Map {
    private int mapID;
    private  Square[] allSquare;
    private ArrayList<ArrayList<Square>> roomSquare;

    public void setMapID(int mapID){
        this.mapID=mapID;
    }
    public int getMapID(){
        return this.mapID;
    }
    public Square[] getAllSquare(){
        return this.allSquare;
    }
    public Map(int mapID){
        this.mapID = mapID;
    }
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
    public void setRoomSquare(){

        this.roomSquare = new ArrayList<ArrayList<Square>>();
        int j;
        for(j=0;j<6;j++)
            roomSquare.add(new ArrayList<Square>());

        //iterate allSquare vector
        System.out.println(roomSquare.size());
        for(int i=0;i<allSquare.length;i++){
            //iterate the main arraylist
            j=0;
            //if the square is accessible
            if(!allSquare[i].getColor().equals("")){
                //iterate roomsquare outerlist
                for(int k=0;k<roomSquare.size();k++){
                    //if allSquare[i] hasn't been added yet
                    if(j==0){
                        //if the first element's color of the inner list is the same as allSquare[i]
                        //OR the innerlist is empty
                        //allSquare[i] is added to that innerlist
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
    public ArrayList<ArrayList<Square>> getRoomSquare(){
        return this.roomSquare;
    }
    public Square getSpecificSquare(int indexSquare) {
        return allSquare[indexSquare];
    }
}