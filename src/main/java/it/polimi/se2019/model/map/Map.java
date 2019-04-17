package it.polimi.se2019.model.map;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Map {
    private int mapID;
    private  Square[] allSquare;

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
        for(int i =0;i<allSquare.length;i++)
            System.out.println(allSquare[i].toString());
    }

    public Square getSpecificSquare(int indexSquare) {
        return allSquare[indexSquare];
    }
}