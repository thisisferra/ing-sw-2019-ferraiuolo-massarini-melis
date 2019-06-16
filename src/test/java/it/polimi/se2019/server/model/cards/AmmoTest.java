package it.polimi.se2019.server.model.cards;

import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.game.Cubes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AmmoTest {

    private Ammo ammoPowerUp;
    private Ammo ammo;
    private Cubes cube = new Cubes(1, 1, 1);

    @Before
    public void initialize() {
        ammo = new Ammo(cube, false);
        ammoPowerUp = new Ammo(cube, true);
    }

    @Test
    public void testGetAmmoCubes() {
        Assert.assertEquals(1,cube.getReds());
        Assert.assertEquals(1,cube.getYellows());
        Assert.assertEquals(1,cube.getBlues());
        Assert.assertEquals(cube, ammo.getAmmoCubes());
    }

    @Test
    public void testTrueGetPowerUpCard() {
        Assert.assertTrue(ammoPowerUp.getPowerUpCard());
    }

    @Test
    public void testFalseGetPowerUpCard() {
        Assert.assertFalse(ammo.getPowerUpCard());
    }

    @Test
    public void testToString() {
        String expected = "Reds: 1 Blues: 1 Yellows: 1\nPower Up : false";
        Assert.assertEquals(expected, ammo.toString());
        expected = "Reds: 1 Blues: 1 Yellows: 1\nPower Up : true";
        Assert.assertEquals(expected, ammoPowerUp.toString());


    }
}
