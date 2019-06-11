package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.game.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnemyMarkTest {
    Player p1,p2,p3;
    Match match;
    @Before
    public void setEnemyMarkTest(){
        match = new Match(1);
        p1 = new Player("Mattia", match);
        p2 = new Player("Marco", match);
        p3 = new Player("Alessandro",match);
    }

    //checking marks mechanics
    @Test
    public void testMarkingPlayers(){

        p1.getPlayerBoard().dealMark(p2,4);
        Assert.assertEquals(3,p1.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        p2.getPlayerBoard().dealMark(p3,1);
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        p2.getPlayerBoard().dealMark(p3,1);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        p2.getPlayerBoard().dealMark(p1,1);
        Assert.assertEquals(2,p2.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        Assert.assertEquals(1,p2.getPlayerBoard().getEnemyMarks().get(1).getMarks());
        Assert.assertEquals(p2,p1.getPlayerBoard().getEnemyMarks().get(0).getAggressorPlayer());
    }
}
