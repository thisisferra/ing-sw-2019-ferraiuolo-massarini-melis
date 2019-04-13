package it.polimi.se2019.model.map;
import com.google.gson.Gson;
import it.polimi.se2019.model.game.MovementChecker;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Map {
    private int mapID;
    private int[] dimensions;
    private Square[] allSquare;
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
    public void setAllSquare(){
        Gson gson = new Gson();
        try {
            allSquare = gson.fromJson(new FileReader("/home/mattia/IdeaProjects/ing-sw-2019-ferraiuolo-massarini-melis/src/main/java/it/polimi/se2019/map1.json"), Square[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i =0;i<allSquare.length;i++)
            System.out.println(allSquare[i].toString());
    }
}