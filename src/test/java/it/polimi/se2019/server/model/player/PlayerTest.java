package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.game.Match;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    private Match m1;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;


    @Before
    public void initialize() {

        m1 = new Match(4);
        m1.initializeMatch();
        p1 = new Player("Marco", m1);
        p2 = new Player("Mattia", m1);
        p3 = new Player("Ferra", m1);
        p4 = new Player("Matteo", m1);
        p1.setColor("blue");
        p2.setColor("green");
        p3.setColor("grey");
        p4.setColor("yellow");

    }


    @Test
    public void testGetClientName() {
        p1.setClientName("thisisferra");
        Assert.assertEquals("thisisferra", p1.getClientName());
    }

    @Test
    public void testSetClientName() {
        p1.setClientName("thisisferra");
        Assert.assertEquals("thisisferra", p1.getClientName());
        Assert.assertNotEquals("merklind", p1.getClientName());
    }

    @Test
    public void testGetColor() {
        Assert.assertEquals("blue",p1.getColor());
        Assert.assertEquals("green",p2.getColor());
        Assert.assertEquals("grey",p3.getColor());
        Assert.assertEquals("yellow",p4.getColor());
    }

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

    @Test
    public void testCanMove() {
        Assert.assertEquals(false,p1.getCanMove());
        Assert.assertEquals(false,p2.getCanMove());
        Assert.assertEquals(false,p3.getCanMove());
        Assert.assertEquals(false,p4.getCanMove());
        p1.setCanMove(true);
        Assert.assertEquals(true,p1.getCanMove());
        p1.setCanMove(false);
        Assert.assertEquals(false,p1.getCanMove());

    }

    @Test
    public void testFinalFrenzy(){
        Assert.assertEquals(0,p1.getFinalFrenzy());
        Assert.assertEquals(0,p2.getFinalFrenzy());
        Assert.assertEquals(0,p3.getFinalFrenzy());
        Assert.assertEquals(0,p4.getFinalFrenzy());

        p1.setFinalFrenzy(1);
        p2.setFinalFrenzy(1);
        p3.setFinalFrenzy(1);
        p4.setFinalFrenzy(1);

        Assert.assertEquals(1,p1.getFinalFrenzy());
        Assert.assertEquals(1,p2.getFinalFrenzy());
        Assert.assertEquals(1,p3.getFinalFrenzy());
        Assert.assertEquals(1,p4.getFinalFrenzy());

        p1.setFinalFrenzy(2);
        p2.setFinalFrenzy(2);
        p3.setFinalFrenzy(2);
        p4.setFinalFrenzy(2);

        Assert.assertEquals(2,p1.getFinalFrenzy());
        Assert.assertEquals(2,p2.getFinalFrenzy());
        Assert.assertEquals(2,p3.getFinalFrenzy());
        Assert.assertEquals(2,p4.getFinalFrenzy());

    }
}
