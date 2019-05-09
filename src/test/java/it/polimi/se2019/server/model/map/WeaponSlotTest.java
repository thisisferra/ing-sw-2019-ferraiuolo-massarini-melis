package it.polimi.se2019.model.map;

import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.WeaponSlot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeaponSlotTest {
    Match m;

    @Before
    public void initWeaponSlotTest(){
        m = new Match(1,3);
        m.initGameField();
        m.initPlayers();
        m.initCabinets();
        m.getArsenal().get(0).initSlot();
        m.getArsenal().get(1).initSlot();
        m.getArsenal().get(2).initSlot();

    }

    @Test
    public void testCabinetSizeAndColor(){
        WeaponSlot a0 = m.getArsenal().get(0);
        WeaponSlot a1 = m.getArsenal().get(1);
        WeaponSlot a2 = m.getArsenal().get(2);
        Assert.assertEquals("red",a0.getCabinetColor());
        Assert.assertEquals("yellow",a1.getCabinetColor());
        Assert.assertEquals("blue",a2.getCabinetColor());

        for(int i =0;i<3;i++){
            Assert.assertTrue(m.getArsenal().get(0).getSlot()[i] != null);
            Assert.assertTrue(m.getArsenal().get(1).getSlot()[i] != null);
            Assert.assertTrue(m.getArsenal().get(2).getSlot()[i] != null);
        }

    }
}
