package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.EnemyDamage;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.player.PlayerBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerBoardTest {

    private PlayerBoard pl1;
    private Match m1;
    private Player p1,p2,p3,p4;
    Cubes cube1 = new Cubes(1, 1, 1);

    @Before
    public void initialize() {

        m1 = new Match(4, 4);
        m1.initializeMatch();
        p1 = new Player("Marco", "red", m1);
        p2 = new Player("Mattia", "yellow", m1);
        p3 = new Player("Ferra", "blue", m1);
        p4 = new Player("Matteo", "green", m1);
        pl1 = new PlayerBoard(p1);
    }

    @Test
    public void testSetPointDeaths() {
        Assert.assertEquals(6, pl1.getPointDeaths().size());
        int firstIndex = pl1.getPointDeaths().get(0);
        int lastIndex = pl1.getPointDeaths().get(5);
        Assert.assertEquals(8, firstIndex);
        Assert.assertEquals(1, lastIndex);
    }

    @Test
    public void testGetTags(){
        Assert.assertEquals(0, pl1.getTags().size());
        Match m1 = new Match(3, 2);
        Player p1 = new Player("Marco", "red", m1);
        Player p2 = new Player("Mattia", "yellow", m1);
        pl1.getTags().add(p1);
        pl1.getTags().add(p2);
        Assert.assertEquals(2, pl1.getTags().size());
        Assert.assertEquals(p1, pl1.getTags().get(0));
        Assert.assertEquals(p2, pl1.getTags().get(1));
    }


    @Test
    public void testDealDamage(){
        p1.getPlayerBoard().dealDamage(p2,3);
        Assert.assertEquals(3,p1.getPlayerBoard().getDamage().size());
        Assert.assertEquals(3,p1.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(p2,p1.getPlayerBoard().getEnemyDamages().get(0).getAggressorPlayer());
        p1.getPlayerBoard().dealDamage(p3,10);
        Assert.assertEquals(12,p1.getPlayerBoard().getDamage().size());
        Assert.assertEquals(3,p1.getPlayerBoard().getEnemyDamages().get(1).getDamage());
        Assert.assertEquals(10,p1.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        p3.getPlayerBoard().dealMark(p2,2);
        p3.getPlayerBoard().dealDamage(p1,2);
        Assert.assertEquals(3,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertEquals(2,p3.getPlayerBoard().getEnemyMarks().get(0).getMarks());
        p3.getPlayerBoard().dealDamage(p2,2);
        Assert.assertEquals(4,p3.getPlayerBoard().getEnemyDamages().get(0).getDamage());
        Assert.assertTrue(p1.checkDeath());
    }

    @Test
    public void testGetDamage() {
        Assert.assertEquals(0, pl1.getDamage().size());
        Match m1 = new Match(3, 2);
        Player p1 = new Player("Marco", "red", m1);
        Player p2 = new Player("Mattia", "yellow", m1);
        pl1.getDamage().add(p1);
        pl1.getDamage().add(p2);
        Assert.assertEquals(2, pl1.getDamage().size());
        Assert.assertEquals(p1, pl1.getDamage().get(0));
        Assert.assertEquals(p2, pl1.getDamage().get(1));

    }

    @Test(expected = IllegalStateException.class)
    public void testGetDeaths() {
        Assert.assertEquals(0, pl1.getDeaths());
        pl1.setDeaths();
        Assert.assertEquals(1, pl1.getDeaths());
        pl1.setDeaths();
        Assert.assertEquals(2, pl1.getDeaths());
        pl1.setDeaths();
        pl1.setDeaths();
        pl1.setDeaths();
        pl1.setDeaths();
        pl1.setDeaths();

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetSpecificPointDeaths() {
        Assert.assertEquals((Integer)8, pl1.getPointDeaths().get(0));
        Assert.assertEquals((Integer)6, pl1.getPointDeaths().get(1));
        Assert.assertEquals((Integer)4, pl1.getPointDeaths().get(2));
        Assert.assertEquals((Integer)2, pl1.getPointDeaths().get(3));
        Assert.assertEquals((Integer)1, pl1.getPointDeaths().get(4));
        Assert.assertEquals((Integer)1, pl1.getPointDeaths().get(5));
        pl1.getPointDeaths().get(10);
        pl1.getPointDeaths().remove(new Integer(8));
        Assert.assertEquals((Integer)6, pl1.getPointDeaths().get(0));



    }

    @Test
    public void testGetAmmoCubes() {
        Assert.assertTrue(pl1.getAmmoCubes().getReds() == 1);
        Assert.assertTrue(pl1.getAmmoCubes().getYellows() == 1);
        Assert.assertTrue(pl1.getAmmoCubes().getBlues() == 1);
    }

    @Test
    public void testSetAmmoCubes() {
        Assert.assertTrue(pl1.getAmmoCubes().getReds() == 1);
        Assert.assertTrue(pl1.getAmmoCubes().getYellows() == 1);
        Assert.assertTrue(pl1.getAmmoCubes().getBlues() == 1);
        Cubes cube1 = new Cubes(1, 1, 1);
        pl1.setAmmoCubes(cube1);
        Assert.assertTrue(pl1.getAmmoCubes().getReds() == 2);
        Assert.assertTrue(pl1.getAmmoCubes().getYellows() == 2);
        Assert.assertTrue(pl1.getAmmoCubes().getBlues() == 2);
        Cubes cube2 = new Cubes(1, 1, 1);
        pl1.setAmmoCubes(cube2);
        Assert.assertTrue(pl1.getAmmoCubes().getReds() == 3);
        Assert.assertTrue(pl1.getAmmoCubes().getYellows() == 3);
        Assert.assertTrue(pl1.getAmmoCubes().getBlues() == 3);
        Cubes cube3 = new Cubes(1, 1, 1);
        Assert.assertTrue(pl1.getAmmoCubes().getReds() == 3);
        Assert.assertTrue(pl1.getAmmoCubes().getYellows() == 3);
        Assert.assertTrue(pl1.getAmmoCubes().getBlues() == 3);
    }

    @Test
    public void testDeleteFirstPointDeaths(){
        Assert.assertEquals(new Integer(8), pl1.getPointDeaths().get(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(new Integer(6), pl1.getPointDeaths().get(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(new Integer(4), pl1.getPointDeaths().get(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(new Integer(2), pl1.getPointDeaths().get(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(new Integer(1), pl1.getPointDeaths().get(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(new Integer(1), pl1.getPointDeaths().get(0));
        pl1.deleteFirstPointDeaths();
        pl1.deleteFirstPointDeaths();

    }

    @Test
    public void testSortAggressor() {
        EnemyDamage en1 = new EnemyDamage();
        EnemyDamage en2 = new EnemyDamage();
        EnemyDamage en3 = new EnemyDamage();
        en1.setAggressorPlayer(p2);
        en2.setAggressorPlayer(p3);
        en3.setAggressorPlayer(p4);
        en1.setDamage(4);
        en2.setDamage(6);
        en3.setDamage(1);
        p1.getPlayerBoard().getEnemyDamages().add(0, en1);
        p1.getPlayerBoard().getEnemyDamages().add(1, en2);
        p1.getPlayerBoard().getEnemyDamages().add(2, en3);
        p1.getPlayerBoard().sortAggressor();
        Assert.assertEquals(p3, p1.getPlayerBoard().getEnemyDamages().get(0).getAggressorPlayer());
        Assert.assertEquals(6, p1.getPlayerBoard().getEnemyDamages().get(0).getDamage());
    }
}