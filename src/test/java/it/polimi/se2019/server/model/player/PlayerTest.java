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

        m1 = new Match(4);
        m1.initializeMatch();
        p1 = new Player("Marco", m1);
        p2 = new Player("Mattia", m1);
        p3 = new Player("Ferra", m1);
        p4 = new Player("Matteo", m1);


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

    /*
    @Test
    public void testGetColor() {
        Assert.assertTrue(p1.getColor().equals("red"));
        Assert.assertFalse(p1.getColor() .equals("red"));
        Assert.assertTrue(p2.getColor() .equals("yellow"));
        Assert.assertFalse(p2.getColor() .equals("yellow"));
        Assert.assertTrue(p3.getColor() .equals("blue"));
        Assert.assertFalse(p3.getColor() .equals("blue"));
        }
     */

    @Test
    public void testGetPosition() {
        Assert.assertEquals(-1, p1.getPosition());
        Assert.assertEquals(-1, p2.getPosition());
        Assert.assertEquals(-1, p3.getPosition());
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
}
