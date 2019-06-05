package it.polimi.se2019.model.player;

import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Hand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HandTest {

    Hand hand = new Hand();
    Match m1;
    @Before
    public void setClass() {
        m1 = new Match(1);
        m1.initializeMatch();
        hand.getPowerUps().add(m1.pickUpPowerUp());
        hand.getPowerUps().add(m1.pickUpPowerUp());
        hand.getPowerUps().add(m1.pickUpPowerUp());

    }

    @Test
    public void testTrueCheckPowerUps(){
        Assert.assertTrue(hand.checkPowerUps());
    }

    @Test
    public void testFalseCheckPowerUps(){
        PowerUp p1 = new PowerUp();
        hand.getPowerUps().add(p1);
        Assert.assertFalse(hand.checkPowerUps());
    }

    @Test
    public void testChooseToDiscardIndexLessThan3() {
        PowerUp powerUpToDiscard = hand.getPowerUps().get(0);
        Assert.assertEquals(powerUpToDiscard, hand.chooseToDiscard(0));
    }

    @Test
    public void testChooseToDiscardIndexGreaterThan3() {
        Assert.assertNull(hand.chooseToDiscard(5));
    }

    @Test
    public void testGetPowerUps(){
        PowerUp p1 = new PowerUp();
        PowerUp p2 = new PowerUp();
        Assert.assertEquals(hand.getPowerUps().size(), 3);
        hand.addPowerUp(p1);
        Assert.assertEquals(hand.getPowerUps().size(), 4);
        hand.addPowerUp(p2);
        Assert.assertEquals(hand.getPowerUps().size(), 4);
    }


}
