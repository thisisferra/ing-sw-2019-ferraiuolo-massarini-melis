package it.polimi.se2019;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.game.MovementChecker;
import it.polimi.se2019.model.game.RoomChecker;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;
import it.polimi.se2019.model.player.Player;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        Match match = new Match(1, 5);
        match.initGameField();
        match.initPlayers();
        for(Player object: match.getAllPlayers()){
            object.setPosition(5);
        }
        RoomChecker roomchecker = new RoomChecker(match.getMap(),5);
        roomchecker.setAccessibleRooms();
        System.out.println(roomchecker.getVisiblePlayers(match,match.getAllPlayers().get(0)));
        MovementChecker movement = new MovementChecker(match.getMap().getAllSquare(),3,6);
        movement.check();
        System.out.println("UP: " + movement.getWalkableUpwardsSquares());
        System.out.println("UP: " + movement.getAllUpwardsSquares());
        System.out.println("RIGHT: " + movement.getWalkableRightSquares());
        System.out.println("RIGHT: "+ movement.getAllRightSquares());
        System.out.println("DOWN: " + movement.getWalkableDownwardsSquares());
        System.out.println("DOWN: "+ movement.getAllDownwardsSquares());
        System.out.println("LEFT: " + movement.getWalkableLeftSquares());
        System.out.println("LEFT: " + movement.getAllLeftSquares());

    }
}