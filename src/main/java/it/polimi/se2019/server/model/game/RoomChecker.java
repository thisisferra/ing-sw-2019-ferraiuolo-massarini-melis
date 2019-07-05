package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.map.Map;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;
import java.util.*;

/**
 * RoomChecker class implements the visibility game mechanic.
 * Given a Map and an index, it checks the rooms you can see from that position.
 * The collection roomsColor is necessary in order to store the rooms' colors.
 * The collection visibleRooms contains all the squares visible from a given position.
 * Both collection are filled during the constructor call, and the object created can be used for
 * retrieving additional infos like the visible players and non visible players.
 * @author mattiamassarini.
 */
public class RoomChecker {

    private ArrayList<String> roomsColor = new ArrayList<>();
    private ArrayList<Square> visibleRooms = new ArrayList<>();
    private Map map;

    //roomColor is a list containing all the colors you can access from a square
    public RoomChecker(Map map, int index){
         Square[] allSquare = map.getAllSquare();
         this.map = map;
         roomsColor.add(allSquare[index].getColor());
         if(allSquare[index].getNorth()>=0)
            roomsColor.add(allSquare[allSquare[index].getNorth()].getColor());
         if(allSquare[index].getEast()>=0)
            roomsColor.add(allSquare[allSquare[index].getEast()].getColor());
         if(allSquare[index].getSouth()>=0)
            roomsColor.add(allSquare[allSquare[index].getSouth()].getColor());
         if(allSquare[index].getWest()>=0)
            roomsColor.add(allSquare[allSquare[index].getWest()].getColor());
         Set<String> set = new LinkedHashSet<>();
         set.addAll(roomsColor);
         roomsColor.clear();
         roomsColor.addAll(set);
         this.setAccessibleRooms();
    }
    public List<String> getRoomsColor(){
        return this.roomsColor;
    }

    /**It fills accessibleRooms array with all the squares whose color is found
     * in roomColor.
     * e.g. if roomColor = {red,blue}, accessibleRooms will contain all red and blue squares.
     */
    private void setAccessibleRooms(){
        ArrayList<ArrayList<Square>> roomSquares= map.getRoomSquare();
        for(ArrayList<Square> list: roomSquares){
            if(!list.isEmpty()){
                if(roomsColor.contains(list.get(0).getColor())){
                    this.visibleRooms.addAll(list);
                }
            }
            }
        this.visibleRooms.sort(Comparator.comparing(Square::getPosition));
    }

    /**
     * Getter of accessibleRooms field.
     * @return the visible squares.
     */
    public ArrayList<Square> getAccessibleRooms(){
        return this.visibleRooms;
    }

    /**
     * Finds all the players visible from the owner player, excluding the owner.
     * @param match the current match.
     * @param owner the player from where the visibility property is calculated.
     * @return the list of all visible players from owner perspective.
     */
    public ArrayList<Player> getVisiblePlayers(Match match, Player owner){
        ArrayList<Player> visiblePlayers = new ArrayList<>();
        for(Square object: visibleRooms){
            for(Player player: match.getAllPlayers()){
                if(player.getPosition() == object.getPosition()){
                    visiblePlayers.add(player);
                }
            }
        }
        visiblePlayers.remove(owner);
        return visiblePlayers;
    }

    //return the list of players you cannot see

    /**
     * Finds all the non visible players from the owner player.
     * The method simply subtract the visiblePlayer list from the list
     * of all players, also removing the owner.
     * @param match the current match.
     * @param owner the player from where the visibility property is calculated.
     * @return the list of all the non visible players.
     */
    public ArrayList<Player> getNonVisiblePlayers(Match match, Player owner){
        ArrayList<Player> nonVisiblePlayers = new ArrayList<>();
        for(Player player : match.getAllPlayers())
            if(player.getPosition()>=0){
                nonVisiblePlayers.add(player);
            }
        nonVisiblePlayers.removeAll(getVisiblePlayers(match,owner));

        nonVisiblePlayers.remove(owner);
        return nonVisiblePlayers;
    }

}
