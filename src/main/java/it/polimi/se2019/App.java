package it.polimi.se2019;
import it.polimi.se2019.model.game.Match;
import it.polimi.se2019.model.game.MovementChecker;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.player.Player;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

Match match = new Match(4,3);
match.initGameField();
Player p1 = new Player("Mattia","blue",match);
Player p2 = new Player("Marco","yellow",match);
Player p3 = new Player("Alessandro","red",match);
    }
}