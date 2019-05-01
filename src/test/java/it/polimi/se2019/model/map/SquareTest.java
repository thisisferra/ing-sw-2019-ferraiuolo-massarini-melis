package it.polimi.se2019.model.map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SquareTest {

    private Map map1;
    private Map map2;
    private Map map3;
    private Map map4;

    @Before
    public void setMap(){
        this.map1 = new Map(1);
        map1.setAllSquare();
        this.map2 = new Map(2);
        map2.setAllSquare();
        this.map3 = new Map(3);
        map3.setAllSquare();
        this.map4 = new Map(4);
        map4.setAllSquare();

    }

    @Test
    public void testGetStep(){
        for(Square object: map1.getAllSquare()){
            Assert.assertTrue(object.getStep() >=0);
        }
        for(Square object: map2.getAllSquare()){
            Assert.assertTrue(object.getStep() >=0);
        }
        for(Square object: map3.getAllSquare()){
            Assert.assertTrue(object.getStep() >=0);
        }
        for(Square object: map4.getAllSquare()){
            Assert.assertTrue(object.getStep() >=0);
        }
    }

    //testing if squares are all full after initializing the gamefield
    @Test
    public void testIsFull(){
        for(Square object: map1.getAllSquare())
            Assert.assertTrue(object.isFull());
        for(Square object: map2.getAllSquare())
            Assert.assertTrue(object.isFull());
        for(Square object: map3.getAllSquare())
            Assert.assertTrue(object.isFull());
        for(Square object: map4.getAllSquare())
            Assert.assertTrue(object.isFull());
    }


    @Test
    public void testSetDirection(){
        Square thisSquare = new Square(map1.getSpecificSquare(0));
        thisSquare.setNorth(1);
        thisSquare.setEast(2);
        thisSquare.setSouth(3);
        thisSquare.setWest(4);
        thisSquare.setPosition(0);
        Assert.assertEquals(0,thisSquare.getPosition());
        Assert.assertEquals(1,thisSquare.getNorth());
        Assert.assertEquals(2,thisSquare.getEast());
        Assert.assertEquals(3,thisSquare.getSouth());
        Assert.assertEquals(4,thisSquare.getWest());


    }
    //testing if the spawnpoint are in the correct square
    @Test
    public void testSpawnPoint(){
        for(Square object: map1.getAllSquare()){
            if(object.getPosition() == 4 || object.getPosition() == 2 || object.getPosition() == 11)
                Assert.assertTrue(object.isSpawnPoint());
            else
                Assert.assertFalse(object.isSpawnPoint());
        }
        for(Square object: map2.getAllSquare()){
            if(object.getPosition() == 4 || object.getPosition() == 2 || object.getPosition() == 11)
                Assert.assertTrue(object.isSpawnPoint());
            else
                Assert.assertFalse(object.isSpawnPoint());
        }
        for(Square object: map3.getAllSquare()){
            if(object.getPosition() == 4 || object.getPosition() == 2 || object.getPosition() == 11)
                Assert.assertTrue(object.isSpawnPoint());
            else
                Assert.assertFalse(object.isSpawnPoint());
        }
        for(Square object: map4.getAllSquare()){
            if(object.getPosition() == 4 || object.getPosition() == 2 || object.getPosition() == 11)
                Assert.assertTrue(object.isSpawnPoint());
            else
                Assert.assertFalse(object.isSpawnPoint());
        }
    }


    @Test
    public void testRestock(){
        Square s1;
        Square s2;
        Square s3;
        Square s4;

        s1 = new Square(map1.getSpecificSquare(0));
        s2 = new Square(map2.getSpecificSquare(11));
        s3 = new Square(map3.getSpecificSquare(2));
        s4 = new Square (map4.getSpecificSquare(8));

        s1.setFull(false);
        s2.setFull(false);
        s3.setFull(true);
        s4.setFull(true);

        s1.restock();
        s2.restock();
        s3.restock();
        s4.restock();

        Assert.assertTrue(s1.isFull());
        Assert.assertFalse(s2.isFull());
        Assert.assertTrue(s3.isFull());
        Assert.assertTrue(s4.isFull());
    }
}

