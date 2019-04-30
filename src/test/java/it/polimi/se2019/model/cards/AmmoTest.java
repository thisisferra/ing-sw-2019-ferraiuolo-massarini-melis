package it.polimi.se2019.model.cards;

import it.polimi.se2019.model.cards.Ammo;
import it.polimi.se2019.model.game.Cubes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AmmoTest {

    Ammo ammoPowerUp;
    Ammo ammo;
    Cubes cube = new Cubes(1, 1, 1);

    @Before
    public void initialize() {
        ammo = new Ammo(cube, false);
        ammoPowerUp = new Ammo(cube, true);
    }

    @Test
    public void testGetAmmoCubes() {
        Assert.assertEquals(cube.getReds(), 1);
        Assert.assertEquals(cube.getYellows(), 1);
        Assert.assertEquals(cube.getBlues(), 1);
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
        String expected = "Reds: 1 Blues: 1 Yellows: 1 PUC: false";
        Assert.assertEquals(expected, ammo.toString());
        expected = "Reds: 1 Blues: 1 Yellows: 1 PUC: true";
        Assert.assertEquals(expected, ammoPowerUp.toString());


    }
}
