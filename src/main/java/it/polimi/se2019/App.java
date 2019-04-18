package it.polimi.se2019;
import it.polimi.se2019.model.game.Match;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

Match match = new Match(4,1);
match.initGameField();
match.initPlayers();
    }
}