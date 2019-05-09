package it.polimi.se2019.server.model.map;

import it.polimi.se2019.server.model.map.Map;
import it.polimi.se2019.server.model.map.Square;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class MapTest {

    private Map map1 = new Map(1);
    private Map map2 = new Map(2);
    private Map map3 = new Map(3);
    private Map map4 = new Map(4);
    private Map map5 = new Map(5);
    private Map mapNegative = new Map(-1);

    @Before
    public void setTestingMaps(){
        map1.setAllSquare();
        map2.setAllSquare();
        map3.setAllSquare();
        map4.setAllSquare();
        map5.setAllSquare();
        mapNegative.setAllSquare();
        map1.setRoomSquare();
        map2.setRoomSquare();
        map3.setRoomSquare();
        map4.setRoomSquare();
    }

    //testing if the mapID saved when creating the Map instance is the same as the map file identifier
    @Test
    public void testGetMapID(){

        Assert.assertEquals(1,map1.getMapID());
        Assert.assertEquals(2,map2.getMapID());
        Assert.assertEquals(3,map3.getMapID());
        Assert.assertEquals(4,map4.getMapID());
    }

    @Test
    public void testSetMapID(){
        map1.setMapID(5);
        Assert.assertEquals(5,map1.getMapID());
        try{
            map1.setMapID(-10);
        } catch (ArithmeticException e){
            e.printStackTrace();
        }

    }
    //testing if the reference allSquare contains an array not empty
    @Test
    public void testGetAllSquaresNotEmpty(){

        Assert.assertNotNull(map1.getAllSquare());
        Assert.assertNotNull(map2.getAllSquare());
        Assert.assertNotNull(map3.getAllSquare());
        Assert.assertNotNull(map4.getAllSquare());

    }

    //testing if allSquare contains all the squares defined in the json file (including "non-walkable" squares)
    @Test
    public void testSetAllSquaresLength(){
        Assert.assertEquals(12, map1.getAllSquare().length);
        Assert.assertEquals(12, map2.getAllSquare().length);
        Assert.assertEquals(12, map3.getAllSquare().length);
        Assert.assertEquals(12, map4.getAllSquare().length);
    }

    //testing if colorCount() method returns the correct number of colors
    @Test
    public void testColorCount(){
        Assert.assertEquals(5,map1.colorCount());
        Assert.assertEquals(4,map2.colorCount());
        Assert.assertEquals(5,map3.colorCount());
        Assert.assertEquals(6,map4.colorCount());
    }

    //testing if roomSquare outer list contains all colors available on the map
    // e.g. map1 contains 5 colours (red,blue,yellow,white,purple), roomSquare outer list is made
    // of 5 ArrayList<Square> containing squares of the same color
    @Test
    public void testSetRoomSquareColors(){

        ArrayList<ArrayList<Square>> list =map1.getRoomSquare();
        Assert.assertEquals(list.size(),map1.colorCount());
        list =map2.getRoomSquare();
        Assert.assertEquals(list.size(),map2.colorCount());
        list =map3.getRoomSquare();
        Assert.assertEquals(list.size(),map3.colorCount());
        list =map4.getRoomSquare();
        Assert.assertEquals(list.size(),map4.colorCount());

    }

    //testing if the number of rooms in roomSquare is the same as the number of squares characters can walk on.
    @Test public void testSetRoomSquareNumber(){
        int count =0;
        for(ArrayList<Square> innerList: map1.getRoomSquare())
            for(Square object : innerList)
                count++;
        Assert.assertEquals(11,count);
        count =0;
        for(ArrayList<Square> innerList: map2.getRoomSquare())
            for(Square object : innerList)
                count++;
        Assert.assertEquals(10,count);
        count =0;
        for(ArrayList<Square> innerList: map3.getRoomSquare())
            for(Square object : innerList)
                count++;
        Assert.assertEquals(11,count);
        count =0;
        for(ArrayList<Square> innerList: map4.getRoomSquare())
            for(Square object : innerList)
                count++;
        Assert.assertEquals(12,count);
    }

    //testing if each array cell is not empty and if getSpecificSquare return the correct square object
    @Test
    public void testGetSpecificSquareNotEmpty(){
        for(Square object: map1.getAllSquare())
            Assert.assertTrue(object != null);
        for(Square object: map2.getAllSquare())
            Assert.assertTrue(object != null);
        for(Square object: map3.getAllSquare())
            Assert.assertTrue(object != null);
        for(Square object: map4.getAllSquare())
            Assert.assertTrue(object != null);

        for(int i=0;i<map1.getAllSquare().length;i++)
            Assert.assertEquals(map1.getAllSquare()[i],map1.getSpecificSquare(i));
        for(int i=0;i<map2.getAllSquare().length;i++)
            Assert.assertEquals(map2.getAllSquare()[i],map2.getSpecificSquare(i));
        for(int i=0;i<map3.getAllSquare().length;i++)
            Assert.assertEquals(map3.getAllSquare()[i],map3.getSpecificSquare(i));
        for(int i=0;i<map4.getAllSquare().length;i++)
            Assert.assertEquals(map4.getAllSquare()[i],map4.getSpecificSquare(i));
    }
}
