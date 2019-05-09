package it.polimi.se2019;


import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.player.Player;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        Match match = new Match(1, 5);
        match.initGameField();
        match.initPlayers();
        match.getAllPlayers().get(0).setPosition(6);
        match.getAllPlayers().get(1).setPosition(11);
        match.getAllPlayers().get(2).setPosition(11);
        match.getAllPlayers().get(3).setPosition(11);
        match.getAllPlayers().get(4).setPosition(11);

        RoomChecker roomchecker = new RoomChecker(match.getMap(),6);
        roomchecker.setAccessibleRooms();
        System.out.println(roomchecker.getFarAwayPlayers(match,match.getAllPlayers().get(0),2));


    }
}