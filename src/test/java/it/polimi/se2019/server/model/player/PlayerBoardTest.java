package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.player.PlayerBoard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerBoardTest {

    private PlayerBoard pl1;
    Cubes cube1 = new Cubes(1, 1, 1);

    @Before
    public void initialize() {
        pl1 = new PlayerBoard();
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
        Assert.assertEquals(8, pl1.getSpecificPointDeaths(0));
        Assert.assertEquals(6, pl1.getSpecificPointDeaths(1));
        Assert.assertEquals(4, pl1.getSpecificPointDeaths(2));
        Assert.assertEquals(2, pl1.getSpecificPointDeaths(3));
        Assert.assertEquals(1, pl1.getSpecificPointDeaths(4));
        Assert.assertEquals(1, pl1.getSpecificPointDeaths(5));
        pl1.getSpecificPointDeaths(10);
        pl1.getPointDeaths().remove("8");
        Assert.assertEquals(6, pl1.getSpecificPointDeaths(0));



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
        Assert.assertEquals(8, pl1.getSpecificPointDeaths(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(6, pl1.getSpecificPointDeaths(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(4, pl1.getSpecificPointDeaths(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(2, pl1.getSpecificPointDeaths(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(1, pl1.getSpecificPointDeaths(0));
        pl1.deleteFirstPointDeaths();
        Assert.assertEquals(1, pl1.getSpecificPointDeaths(0));
        pl1.deleteFirstPointDeaths();
        pl1.deleteFirstPointDeaths();

    }
}