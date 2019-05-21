package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.map.Map;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

public class RoomChecker {

    private ArrayList<String> roomsColor = new ArrayList<>();
    private ArrayList<Square> visibleRooms = new ArrayList<>();
    Map map;

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

    public ArrayList<Player> getOtherRoomsVisiblePlayers(Match match,Player owner) {
        ArrayList<Square> visibleSquares = this.getAccessibleRooms();
        ArrayList<Player> visiblePlayer = new ArrayList<>();
        String squareColor = match.getMap().getSpecificSquare(owner.getPosition()).getColor();
        visibleSquares = visibleSquares.stream().filter(x-> !x.getColor().equals(squareColor)).collect(Collectors.toCollection(ArrayList::new));
        for (Square square : visibleSquares) {
            for (Player player : match.getAllPlayers()) {
                if (player.getPosition() == square.getPosition()) visiblePlayer.add(player);
            }
        }
        return  visiblePlayer;
    }

    //return the list of players you cannot see
    public ArrayList<Player> getNonVisiblePlayers(Match match, Player owner){
        ArrayList<Player> nonVisiblePlayers = getVisiblePlayers(match,owner);
        nonVisiblePlayers.removeAll(match.getAllPlayers());
        return nonVisiblePlayers;
    }


    // return the list of players you can see, ignoring all players sitting in a square near you based on
    // the distance parameter.
    public ArrayList<Player> getFarAwayPlayers(Match match,Player owner,int distance){
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Square> legitSquares = new ArrayList<>();
        ArrayList<Square> resultSquares = new ArrayList<>();

        MovementChecker ignoredSquares = new MovementChecker(match.getMap().getAllSquare(),distance,owner.getPosition());


        System.out.println("ignored: " + ignoredSquares.getReachableSquares());
        legitSquares.addAll(this.visibleRooms);
        resultSquares.addAll(this.visibleRooms);
        for(Square square1: legitSquares){
            for(Square square2: ignoredSquares.getReachableSquares()){
                if(square1.getPosition() == square2.getPosition())
                    resultSquares.remove(square1);
            }
        }
        System.out.println("legit: "+ legitSquares);
        System.out.println("result: " + resultSquares);

        for(Square object: resultSquares){
            for(Player player: match.getAllPlayers()){
                if(player.getPosition() == object.getPosition()){
                    playerList.add(player);
                }
            }
        }
        playerList.remove(owner);
        return playerList;
    }

}
