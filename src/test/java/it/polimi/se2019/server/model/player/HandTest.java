package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Hand;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class HandTest {

    private Hand hand = new Hand();
    private Match m1;
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
    public void testChooseToDiscardIndexLessThan3() {
        PowerUp powerUpToDiscard = hand.getPowerUps().get(0);
        Assert.assertEquals(powerUpToDiscard, hand.chooseToDiscard(0));
    }

    @Test
    public void testChooseToDiscardIndexGreaterThan3() {
        Assert.assertNull(hand.chooseToDiscard(5));
    }

    @Test
    public void testResumeHand(){
        JSONObject handToRestore = hand.toJSON();
        Hand handRestored = Hand.resumeHand(handToRestore);

        Assert.assertEquals(handRestored.getPowerUps().get(0).getType(),hand.getPowerUps().get(0).getType());
        Assert.assertEquals(handRestored.getPowerUps().get(1).getType(),hand.getPowerUps().get(1).getType());
        Assert.assertEquals(handRestored.getPowerUps().get(2).getType(),hand.getPowerUps().get(2).getType());

        Assert.assertEquals(handRestored.getPowerUps().get(0).getColor(),hand.getPowerUps().get(0).getColor());
        Assert.assertEquals(handRestored.getPowerUps().get(1).getColor(),hand.getPowerUps().get(1).getColor());
        Assert.assertEquals(handRestored.getPowerUps().get(2).getColor(),hand.getPowerUps().get(2).getColor());

    }


}
