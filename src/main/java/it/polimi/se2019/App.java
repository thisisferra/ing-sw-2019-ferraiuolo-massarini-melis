package it.polimi.se2019;

import it.polimi.se2019.model.game.MovementChecker;
import it.polimi.se2019.model.map.Map;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

            Map map = new Map();
            map.setAllSquare();
            MovementChecker mCheck = new MovementChecker(map.getAllSquare(),3,6);
            mCheck.check();
            System.out.println(mCheck.getReachableSquares());

    }
}