package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

    Match m1;
    Player p1;
    Player p2;
    Player p3;
    Player p4;


    @Before
    public void initialize() {

        m1 = new Match(4, 4);
        m1.initGameField();
        p1 = new Player("Marco", "red", m1);
        p2 = new Player("Mattia", "yellow", m1);
        p3 = new Player("Ferra", "blue", m1);
        p4 = new Player("Matteo", "green", m1);


    }

    @Test
    public void testGetEnemyDamages() {
        Assert.assertEquals(0, p1.getEnemyDamages().size());
        EnemyDamage newEnemyDamage = new EnemyDamage();
        newEnemyDamage.setAggressorPlayer(p2);
        newEnemyDamage.setDamage(5);
        p1.getEnemyDamages().add(0, newEnemyDamage);
        Assert.assertEquals(newEnemyDamage, p1.getEnemyDamages().get(0));
        Assert.assertEquals(p2, p1.getEnemyDamages().get(0).getAggressorPlayer());
        Assert.assertEquals(5, p1.getEnemyDamages().get(0).getDamage());

    }

    @Test
    public void getClientName() {
        p1.setClientName("thisisferra");
        Assert.assertEquals("thisisferra", p1.getClientName());
    }

    @Test
    public void setClientName() {
        p1.setClientName("thisisferra");
        Assert.assertEquals("thisisferra", p1.getClientName());
        Assert.assertNotEquals("merklind", p1.getClientName());
    }

    @Test
    public void testGetColor() {
        Assert.assertTrue(p1.getColor() == "red");
        Assert.assertFalse(p1.getColor() != "red");
        Assert.assertTrue(p2.getColor() == "yellow");
        Assert.assertFalse(p2.getColor() != "yellow");
        Assert.assertTrue(p3.getColor() == "blue");
        Assert.assertFalse(p3.getColor() != "blue");


    }

    @Test
    public void testGetPosition() {
        Assert.assertEquals(0, p1.getPosition());
        Assert.assertEquals(0, p2.getPosition());
        Assert.assertEquals(0, p3.getPosition());
        p1.setPosition(5);
        Assert.assertEquals(5, p1.getPosition());
    }

    @Test
    public void testGetHand() {
        Assert.assertEquals(0, p1.getHand().getWeapons().size());
        Assert.assertEquals(0, p1.getHand().getPowerUps().size());
        PowerUp pu1 = m1.getPowerUpStack().get(0);
        System.out.println(pu1);
        p1.getHand().addPowerUp(pu1);
        String namePowerUp = pu1.getType();
        String colorPowerUp = pu1.getColor();
        Assert.assertEquals(namePowerUp, p1.getHand().getPowerUps().get(0).getType());
        Assert.assertEquals(colorPowerUp, p1.getHand().getPowerUps().get(0).getColor());
        Assert.assertEquals(1, p1.getHand().getPowerUps().size());


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
        p1.getEnemyDamages().add(0, en1);
        p1.getEnemyDamages().add(1, en2);
        p1.getEnemyDamages().add(2, en3);
        p1.sortAggressor();
        Assert.assertEquals(p3, p1.getEnemyDamages().get(0).getAggressorPlayer());
        Assert.assertEquals(6, p1.getEnemyDamages().get(0).getDamage());
    }

}
