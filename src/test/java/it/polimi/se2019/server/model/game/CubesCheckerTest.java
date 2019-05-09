package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.CubesChecker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CubesCheckerTest {

    private CubesChecker cC;

    @Test
    public void testCubesCheck(){
        cC = new CubesChecker(new Cubes(3,3,3),new Cubes(3,3,3));
        Assert.assertTrue(cC.check());

        cC = new CubesChecker(new Cubes(3,3,0),new Cubes(3,3,3));
        Assert.assertFalse(cC.check());

        cC = new CubesChecker(new Cubes(3,0,3),new Cubes(3,3,3));
        Assert.assertFalse(cC.check());

        cC = new CubesChecker(new Cubes(0,3,3),new Cubes(3,3,3));
        Assert.assertFalse(cC.check());
    }
}
