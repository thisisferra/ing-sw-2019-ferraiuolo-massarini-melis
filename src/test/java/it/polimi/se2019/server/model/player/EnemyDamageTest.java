package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.EnemyDamage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnemyDamageTest {

    EnemyDamage en1 = new EnemyDamage();
    Match m1 = new Match(2, 4);

    @Test
    public void testGetDamageEquals0(){
        Assert.assertEquals(0, en1.getDamage());
    }

    @Test
    public void testSetDamage(){
        Assert.assertEquals(en1.getDamage(), 0);
        en1.setDamage(5);
        Assert.assertEquals(en1.getDamage(), 5);
        en1.setDamage(3);
        Assert.assertEquals(en1.getDamage(), 8);
        en1.setDamage(5);
        Assert.assertEquals(en1.getDamage(), 11);
    }

    @Test
    public void testSetAggressorPlayer(){
        m1.initPlayers();
        en1.setAggressorPlayer(m1.getAllPlayers().get(0));
        Assert.assertEquals(m1.getAllPlayers().get(0), en1.getAggressorPlayer());

    }

}
