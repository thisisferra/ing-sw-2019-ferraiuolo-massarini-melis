package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.map.Map;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

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

    //this method fills accessibleRooms array with all the squares whose color is found
    //in roomColor.
    //e.g. if roomColor = {red,blue}, accessibleRooms will contain all red and blue squares.
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
    public ArrayList<Square> getAccessibleRooms(){
        return this.visibleRooms;
    }

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
