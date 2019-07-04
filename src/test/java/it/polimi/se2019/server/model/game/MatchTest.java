package it.polimi.se2019.server.model.game;

import com.google.gson.JsonObject;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Player;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MatchTest {

    private Match m1;
    private Match m2;
    private Match m3;
    private Match m4;
    private Match m5;

    @Before
    public void testSetMatch(){
        m1 = new Match(1);
        m2 = new Match(2);
        m3 = new Match(3);
        m4 = new Match(4);
        m5 = new Match(1);
        m1.initializeMatch();
        m2.initializeMatch();
        m3.initializeMatch();
        m4.initializeMatch();
        m5.initializeMatch();
        m1.initializeCharacterAvailable();
        m2.initializeCharacterAvailable();
        m3.initializeCharacterAvailable();
        m4.initializeCharacterAvailable();
        m5.initializeCharacterAvailable();
    }


    @Test
    public void testNumberOfPlayers(){

        m1.getAllPlayers().add(new Player("1",m1));
        m1.getAllPlayers().add(new Player("2",m1));
        m1.getAllPlayers().add(new Player("3",m1));
        Assert.assertEquals(3,m1.getAllPlayers().size());

        m2.getAllPlayers().add(new Player("1",m2));
        m2.getAllPlayers().add(new Player("2",m2));
        m2.getAllPlayers().add(new Player("3",m2));
        m2.getAllPlayers().add(new Player("4",m2));
        m2.getAllPlayers().add(new Player("5",m2));
        Assert.assertEquals(5,m2.getAllPlayers().size());
        Assert.assertEquals(0,m3.getAllPlayers().size());

        m4.getAllPlayers().add(new Player("1",m4));
        m4.getAllPlayers().add(new Player("2",m4));
        Assert.assertEquals(2,m4.getAllPlayers().size());

        m5.getAllPlayers().add(new Player("1",m5));
        m5.getAllPlayers().add(new Player("2",m5));
        m5.getAllPlayers().add(new Player("3",m5));
        m5.getAllPlayers().add(new Player("4",m5));
        Assert.assertEquals(4,m5.getAllPlayers().size());
        }



    @Test
    public void testDeckSize(){

        Assert.assertEquals(36,m1.getAmmoStack().size());
        Assert.assertEquals(36,m2.getAmmoStack().size());
        Assert.assertEquals(36,m3.getAmmoStack().size());
        Assert.assertEquals(36,m4.getAmmoStack().size());


        Assert.assertEquals(12,m1.getWeaponStack().size());
        Assert.assertEquals(12,m2.getWeaponStack().size());
        Assert.assertEquals(12,m3.getWeaponStack().size());
        Assert.assertEquals(12,m4.getWeaponStack().size());

        Assert.assertEquals(3,m1.getArsenal().get(0).getSlot().length);
        Assert.assertEquals(3,m1.getArsenal().get(1).getSlot().length);
        Assert.assertEquals(3,m1.getArsenal().get(2).getSlot().length);
        Assert.assertEquals(3,m2.getArsenal().size());
        Assert.assertEquals(3,m3.getArsenal().size());
        Assert.assertEquals(3,m4.getArsenal().size());

        Assert.assertEquals(24,m1.getPowerUpStack().size());
        Assert.assertEquals(24,m2.getPowerUpStack().size());
        Assert.assertEquals(24,m3.getPowerUpStack().size());
        Assert.assertEquals(24,m4.getPowerUpStack().size());

    }

    @Test
    public void testDiscardCard(){
        m1.initializeMatch();
        m1.getDiscardedPowerUps().add(m1.pickUpPowerUp());
        Assert.assertEquals(1,m1.getDiscardedPowerUps().size());
        Assert.assertEquals(23,m1.getPowerUpStack().size());

        m1.getDiscardedAmmos().add(m1.pickUpAmmoStack());
        Assert.assertEquals(1,m1.getDiscardedAmmos().size());
        Assert.assertEquals(35,m1.getAmmoStack().size());

        m1.discardAmmo(m1.getAmmoStack().remove(m1.getAmmoStack().size()-1));
        Assert.assertEquals(2,m1.getDiscardedAmmos().size());
        Assert.assertEquals(34,m1.getAmmoStack().size());
    }

    @Test
    public void testArsenalSize(){
        Assert.assertEquals(3,m1.getArsenal().size());
    }

    @Test
    public void testGetMap(){
        Assert.assertEquals(1,m1.getMap().getMapID());
        Assert.assertEquals(2,m2.getMap().getMapID());
        Assert.assertEquals(3,m3.getMap().getMapID());
        Assert.assertEquals(4,m4.getMap().getMapID());
        Assert.assertEquals(1,m5.getMap().getMapID());

    }

    @Test
    public void restoreMatch(){
        JSONObject m1ToRestore = m1.toJSON();
        JSONObject m2ToRestore = m2.toJSON();
        JSONObject m3ToRestore = m3.toJSON();
        JSONObject m4ToRestore = m4.toJSON();
        JSONObject m5ToRestore = m5.toJSON();

        Match m1Restored = Match.resumeMatch(m1ToRestore,m1.getMap().getMapID());
        Match m2Restored = Match.resumeMatch(m2ToRestore,m2.getMap().getMapID());
        Match m3Restored = Match.resumeMatch(m3ToRestore,m3.getMap().getMapID());
        Match m4Restored = Match.resumeMatch(m4ToRestore,m4.getMap().getMapID());
        Match m5Restored = Match.resumeMatch(m5ToRestore,m5.getMap().getMapID());

        Assert.assertEquals(m1Restored.getMap().getMapID(),m1.getMap().getMapID());
        Assert.assertEquals(m2Restored.getMap().getMapID(),m2.getMap().getMapID());
        Assert.assertEquals(m3Restored.getMap().getMapID(),m3.getMap().getMapID());
        Assert.assertEquals(m4Restored.getMap().getMapID(),m4.getMap().getMapID());
        Assert.assertEquals(m5Restored.getMap().getMapID(),m5.getMap().getMapID());


    }
}
