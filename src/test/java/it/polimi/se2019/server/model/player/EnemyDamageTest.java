package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.EnemyDamage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnemyDamageTest {

    EnemyDamage en1 = new EnemyDamage();
    Match m1 = new Match(2);

    @Test
    public void testGetDamageEquals0(){
        Assert.assertEquals(0, en1.getDamage());
    }

    @Test
    public void testSetDamage(){
        Assert.assertEquals(0,en1.getDamage());
        en1.setDamage(5);
        Assert.assertEquals(5,en1.getDamage());
        en1.setDamage(3);
        Assert.assertEquals(8,en1.getDamage());
        en1.setDamage(5);
        Assert.assertEquals(12,en1.getDamage());
    }

    @Test
    public void testSetAggressorPlayer(){
        m1.initializeMatch();
        en1.setAggressorPlayer(m1.getAllPlayers().get(0));
        Assert.assertEquals(m1.getAllPlayers().get(0), en1.getAggressorPlayer());

    }
}
