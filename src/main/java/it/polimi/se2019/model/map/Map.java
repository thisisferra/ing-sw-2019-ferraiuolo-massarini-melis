package it.polimi.se2019.model.map;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Map {
    private int mapID;
    private int[] dimensions;
    private  Square[] allSquare;

    public int[] getDimensions() {
        return dimensions;
    }

    public void setMapID(int mapID){
        this.mapID=mapID;
    }

    public int getMapID(){
        return this.mapID;
    }

    public void setDimensions(int[] dimensions){
        this.dimensions=dimensions;
    }

    public Square[] getAllSquare(){
        return this.allSquare;
    }

    public void setAllSquare(int number){
        Gson gson = new Gson();
        try {
            if(number ==1) allSquare = gson.fromJson(new FileReader("./src/main/resources/map1.json"), Square[].class);
            else if(number ==2) allSquare = gson.fromJson(new FileReader("./src/main/resources/map1.json"), Square[].class);
                else if (number == 3) allSquare = gson.fromJson(new FileReader("./src/main/resources/map1.json"), Square[].class);
                    else if (number == 4) allSquare = gson.fromJson(new FileReader("./src/main/resources/map1.json"), Square[].class);
                        else System.out.println("Map number :"+ number +" doesn't exist!");
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