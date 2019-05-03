package it.polimi.se2019;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.game.MovementChecker;
import it.polimi.se2019.model.game.RoomChecker;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.Square;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        Match match = new Match(1, 1);
        match.initGameField();
        match.initPlayers();
        Map map = match.getMap();

        MovementChecker movement = new MovementChecker(map.getAllSquare(), 1, 9);
        movement.check();
        System.out.println(movement.getReachableSquares());

        System.out.println( movement.getWalkableUpwardsSquares());
        System.out.println(movement.getWalkableRightSquares());
        System.out.println(movement.getWalkableDownwardsSquares());
        System.out.println(movement.getWalkableLeftSquares());

    }
}