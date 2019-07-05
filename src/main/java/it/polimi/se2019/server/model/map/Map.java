package it.polimi.se2019.server.model.map;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import java.util.*;

/**
 * Map class represent the game map.
 * The field mapID refers to the map's configuration (from 1 to 4).
 * Map is made of squares who are divided by rooms.
 * Squares who belongs to a room share the color.
 * @author mattiamassarini,merklind,thisisferra.
 */
public class Map implements Serializable {
    private int mapID;
    private Square[] allSquare;
    private ArrayList<ArrayList<Square>> roomSquare;

    /**
     * Setter of the mapID field.
     * @param mapID the unique id of the map. It must be a number greater than 0.
     */
    public void setMapID(int mapID){
        if(mapID > 0) {
            this.mapID = mapID;
        }
        else throw new ArithmeticException("mapID cannot be negative or equal to 0");
    }

    /**
     * Getter of the mapID field.
     * @return the current map id.
     */
    public int getMapID(){
        return this.mapID;
    }

    /**
     * Getter of the allSquare field.
     * @return an array of all squares that make the map.
     */
    public Square[] getAllSquare(){
        return this.allSquare;
    }

    /**
     * Setter method of the mapID field.
     * @param mapID the map id chosen.
     */
    public Map(int mapID){
        this.mapID = mapID;
    }

    //based on mapID number the method parse the json file with the same id
    // filling the array allSquares with Square objects

    /**
     * Setter of allSquare field.
     * It sets the map's squares based on the mapID chosen reading the
     * square information from a Json configuration file.
     */
    public void setAllSquare(){
        Gson gson = new Gson();
        try {
            if(mapID ==1) allSquare = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/map1.json")), Square[].class);
            else if(mapID ==2) allSquare = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/map2.json")), Square[].class);
                else if (mapID == 3) allSquare = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/map3.json")), Square[].class);
                    else if (mapID == 4) allSquare = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream("/map4.json")), Square[].class);
                        else System.out.println("Map number :"+ mapID +" doesn't exist!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //fills roomSquare with the reference of all map squares, sorted based on their colours

    /**
     * Setter of the roomSquare field.
     * It fills the collection with collections of squares, sorted by color.
     * Each list contains the squares that share the same color.
     */
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

    /**
     * It search for a square if the position parameter is valid.
     * @param position the number of the square.
     * @return the square required or null if the position is invalid.
     */
    public Square searchSquare(int position) {
        for (Square square : allSquare) {
            if (square.getPosition() == position) return square;
        }
        return null;
    }

    //return the number of different colors on the map

    /**
     * It counts the number of different colors of the rooms.
     * @return the number of different rooms colors of the map.
     */
    public int colorCount(){

        Set <String> colorSet = new HashSet<>();
        for(Square object : this.allSquare){
            if(!object.getColor().equals(""))
                colorSet.add(object.getColor());
        }
        return colorSet.size();
    }

    /**
     * Getter of the roomSquare field.
     * @return the list of lists of squares, sorted by colors.
     */
    public ArrayList<ArrayList<Square>> getRoomSquare(){
        return this.roomSquare;
    }

    /**
     * @param indexSquare the square's index requested.
     * @return the square matching the index requested.
     */
    public Square getSpecificSquare(int indexSquare) {
        return allSquare[indexSquare];
    }

    /**
     * It saves the map state to a JSONObject object.
     * @return the JSONObject to be restored.
     */
    public JSONObject toJSON() {
        JSONObject mapJson = new JSONObject();

        mapJson.put("mapID", this.getMapID());

        JSONArray allSquareJson = new JSONArray();
        for (Square square : this.getAllSquare()) {
            allSquareJson.add(square.toJSON());
        }
        mapJson.put("allSquare", allSquareJson);

        JSONArray roomSquareJson = new JSONArray();
        for (ArrayList<Square> room : this.getRoomSquare()) {
            JSONArray squaresJson = new JSONArray();
            for (Square square : room) {
                squaresJson.add(square.toJSON());
            }
            roomSquareJson.add(squaresJson);
        }
        mapJson.put("roomSquare", roomSquareJson);

        return mapJson;
    }

    /**
     * It restores the Map object from a JSONObject containing information about the map.
     * @param mapToResume the JSONObject to be restored.
     * @param chosenMap the map ID of the JSONObject to be restored.
     * @return the map restored.
     */
    public static Map resumeMap(JSONObject mapToResume, int chosenMap) {
        Map resumedMap = new Map(chosenMap);

        JSONArray allSquareToResume = (JSONArray) mapToResume.get("allSquare");
        resumedMap.allSquare = new Square[allSquareToResume.size()];
        for (int i = 0; i < allSquareToResume.size(); i++) {
            resumedMap.allSquare[i] = Square.resumeSquare((JSONObject) allSquareToResume.get(i));
        }

        JSONArray roomSquareToResume = (JSONArray) mapToResume.get("roomSquare");
        resumedMap.roomSquare = new ArrayList<>();
        int roomNumber = 0;
        for (Object room : roomSquareToResume) {

            JSONArray squaresToResume = (JSONArray) room;
            resumedMap.roomSquare.add(new ArrayList<>());

            for (Object square : squaresToResume) {
                resumedMap.roomSquare.get(roomNumber).add(Square.resumeSquare((JSONObject) square));
            }

            roomNumber++;
        }

        return resumedMap;
    }
}