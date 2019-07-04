package it.polimi.se2019.server.model.map;

import com.google.gson.JsonObject;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.WeaponSlot;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeaponSlotTest {
    Match m;

    @Before
    public void initWeaponSlotTest(){
        m = new Match(1);
        m.initializeMatch();
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
            Assert.assertNotNull(m.getArsenal().get(0).getSlot()[i]);
            Assert.assertNotNull(m.getArsenal().get(1).getSlot()[i]);
            Assert.assertNotNull(m.getArsenal().get(2).getSlot()[i]);
        }

    }

    @Test
    public void testResumeWeaponSlot(){
        WeaponSlot redWeaponSlot = m.getArsenal().get(0);
        WeaponSlot yellowWeaponSlot = m.getArsenal().get(1);
        WeaponSlot blueWeaponSlot = m.getArsenal().get(2);

        JSONObject redWeaponSlotToRestore = redWeaponSlot.toJSON();
        JSONObject yellowWeaponSlotToRestore = yellowWeaponSlot.toJSON();
        JSONObject blueWeaponSlotToRestore = blueWeaponSlot.toJSON();


        WeaponSlot redWeaponsSlotRestored = WeaponSlot.resumeWeaponSlot(redWeaponSlotToRestore,m);
        WeaponSlot yellowWeaponsSlotRestored = WeaponSlot.resumeWeaponSlot(yellowWeaponSlotToRestore,m);
        WeaponSlot blueWeaponsSlotRestored = WeaponSlot.resumeWeaponSlot(blueWeaponSlotToRestore,m);

        //red
        Assert.assertEquals(redWeaponSlot.getSlot()[0].getType(),redWeaponsSlotRestored.getSlot()[0].getType());
        Assert.assertEquals(redWeaponSlot.getSlot()[1].getType(),redWeaponsSlotRestored.getSlot()[1].getType());
        Assert.assertEquals(redWeaponSlot.getSlot()[2].getType(),redWeaponsSlotRestored.getSlot()[2].getType());

        Assert.assertEquals(yellowWeaponSlot.getSlot()[0].getType(),yellowWeaponsSlotRestored.getSlot()[0].getType());
        Assert.assertEquals(yellowWeaponSlot.getSlot()[1].getType(),yellowWeaponsSlotRestored.getSlot()[1].getType());
        Assert.assertEquals(yellowWeaponSlot.getSlot()[2].getType(),yellowWeaponsSlotRestored.getSlot()[2].getType());

        Assert.assertEquals(blueWeaponSlot.getSlot()[0].getType(),blueWeaponsSlotRestored.getSlot()[0].getType());
        Assert.assertEquals(blueWeaponSlot.getSlot()[1].getType(),blueWeaponsSlotRestored.getSlot()[1].getType());
        Assert.assertEquals(blueWeaponSlot.getSlot()[2].getType(),blueWeaponsSlotRestored.getSlot()[2].getType());

    }
}
