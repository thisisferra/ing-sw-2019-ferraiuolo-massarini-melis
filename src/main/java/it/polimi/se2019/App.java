package it.polimi.se2019;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.game.RoomChecker;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

    Match match = new Match(1,1);
    match.initGameField();
    match.initPlayers();
    Map map = match.getMap();
    map.setRoomSquare();
        System.out.println(map.getRoomSquare());
    Square[] squares = map.getAllSquare();
    RoomChecker rooms = new RoomChecker(map,11);
    rooms.setAccessibleRooms();
        System.out.println(rooms.getRoomsColor());
        System.out.println(rooms.getAccessibleRooms());
    }
}