package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.cards.weapons.Weapon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WeaponTest {

    Match m1;
    Weapon createdWeapon;
    @Before
    public void setWeaponTest(){
        m1 = new Match(1,5);
        m1.initializeMatch();

    }

    @Test
    public void testFactoryWeapon(){
        for(Weapon weapon: m1.getWeaponStack()){
            Assert.assertTrue(weapon.getLoad());
            createdWeapon = weapon.weaponFactory(weapon);
            Assert.assertEquals(createdWeapon.getType(),weapon.getType());
            Assert.assertEquals(createdWeapon.getReloadCost().getReds(),weapon.getReloadCost().getReds());
            Assert.assertEquals(createdWeapon.getReloadCost().getYellows(),weapon.getReloadCost().getYellows());
            Assert.assertEquals(createdWeapon.getReloadCost().getBlues(),weapon.getReloadCost().getBlues());
            Assert.assertEquals(createdWeapon.getBuyingCost().getReds(),weapon.getBuyingCost().getReds());
            Assert.assertEquals(createdWeapon.getBuyingCost().getYellows(),weapon.getBuyingCost().getYellows());
            Assert.assertEquals(createdWeapon.getBuyingCost().getBlues(),weapon.getBuyingCost().getBlues());
            Assert.assertEquals(createdWeapon.getType(),weapon.getType());
            for(int i=0; i<weapon.getEffect().length;i++){
                Assert.assertEquals(createdWeapon.getEffect()[i].toString(),weapon.getEffect()[i].toString());
            }
        }
    }
}
