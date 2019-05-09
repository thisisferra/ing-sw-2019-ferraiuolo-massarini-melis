package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.game.Cubes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CubesTest {
    Cubes c1,c2,c3,c4;

    @Before
    public void initCubesTest(){
        c1 = new Cubes(0,0,0);
        c2 = new Cubes(1,0,0);
        c3 = new Cubes(3,3,3);
        c4 = new Cubes(0,3,0);
    }

    @Test
    public void testSetCubes(){
        c1.setCubes(new Cubes(3,3,3));
        c2.setCubes(new Cubes(3,0,4));
        c3.setCubes(new Cubes(0,0,0));
        c4.setCubes(new Cubes(3,1,3));
        Assert.assertEquals(3,c1.getReds());
        Assert.assertEquals(3,c1.getYellows());
        Assert.assertEquals(3,c1.getBlues());

        Assert.assertEquals(3,c2.getReds());
        Assert.assertEquals(0,c2.getYellows());
        Assert.assertEquals(3,c2.getBlues());

        Assert.assertEquals(3,c3.getReds());
        Assert.assertEquals(3,c3.getYellows());
        Assert.assertEquals(3,c3.getBlues());

        Assert.assertEquals(3,c4.getReds());
        Assert.assertEquals(3,c4.getYellows());
        Assert.assertEquals(3,c4.getBlues());



    }
}
