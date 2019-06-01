package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class RoomCheckerTest {

    private RoomChecker rC1;
    private RoomChecker rC2;
    private RoomChecker rC3;
    private RoomChecker rC4;
    private Match m1;
    private Match m2;
    private Match m3;
    private Match m4;


    @Before
    public void setRoomCheckerTest(){

        m1 = new Match(1,5);
        m2 = new Match(2,5);
        m3 = new Match(3,5);
        m4 = new Match(4,5);
        m1.initializeMatch();
        m2.initializeMatch();
        m3.initializeMatch();
        m4.initializeMatch();

    }


    @Test
    public void testRoomColors(){

        int i;
        ArrayList<String> colors = new ArrayList<>();
        Set<String> set;

        for(i =0;i<m1.getMap().getAllSquare().length;i++){
            if(i!=3){

                rC1 = new RoomChecker(m1.getMap(),i);
                set = new LinkedHashSet<>();
                for(Square object: rC1.getAccessibleRooms()){
                    set.add(object.getColor());
                }
                colors.clear();
                colors.addAll(set);
                    Assert.assertTrue(colors.containsAll(rC1.getRoomsColor()));

            }
        }

        for(i =0;i<m2.getMap().getAllSquare().length;i++){
            if(i!=3 && i!=8){

                rC2 = new RoomChecker(m2.getMap(),i);
                set = new LinkedHashSet<>();
                for(Square object: rC2.getAccessibleRooms()){
                    set.add(object.getColor());
                }
                colors.clear();
                colors.addAll(set);
                Assert.assertTrue(colors.containsAll(rC2.getRoomsColor()));
            }
        }

        for(i =0;i<m3.getMap().getAllSquare().length;i++){
            if(i!=8){

                rC3 = new RoomChecker(m3.getMap(),i);
                set = new LinkedHashSet<>();
                for(Square object: rC3.getAccessibleRooms()){
                    set.add(object.getColor());
                }
                colors.clear();
                colors.addAll(set);
                Assert.assertTrue(colors.containsAll(rC3.getRoomsColor()));

            }
        }

        for(i =0;i<m4.getMap().getAllSquare().length;i++){

            rC4 = new RoomChecker(m4.getMap(),i);
            set = new LinkedHashSet<>();
            for(Square object: rC4.getAccessibleRooms()){
                set.add(object.getColor());
            }
            colors.clear();
            colors.addAll(set);
            Assert.assertTrue(colors.containsAll(rC4.getRoomsColor()));
        }
    }

    @Test
    public void testPlayerVisibility(){
        Player p0 = m1.getAllPlayers().get(0);
        Player p1 = m1.getAllPlayers().get(1);
        Player p2 = m1.getAllPlayers().get(2);
        Player p3 = m1.getAllPlayers().get(3);
        Player p4 = m1.getAllPlayers().get(4);
        p0.setPosition(5);  //purple --> blue,white,purple        --> p1,p2
        p1.setPosition(1);  //blue   --> red, blue, purple --> p0
        p2.setPosition(8);  //white  --> white, red        --> none
        p3.setPosition(11);
        p4.setPosition(11);
        rC1 = new RoomChecker(m1.getMap(),p1.getPosition());
        Player visiblePlayer = rC1.getVisiblePlayers(m1,p1).get(0);
        Assert.assertEquals(p0,visiblePlayer);
        rC1 = new RoomChecker(m1.getMap(),p2.getPosition());
        Assert.assertTrue(rC1.getVisiblePlayers(m1,p2).isEmpty());
        Assert.assertTrue(rC1.getNonVisiblePlayers(m1,p2).contains(p0));
        Assert.assertTrue(rC1.getNonVisiblePlayers(m1,p2).contains(p1));
        Assert.assertTrue(rC1.getNonVisiblePlayers(m1,p2).contains(p3));
        Assert.assertTrue(rC1.getNonVisiblePlayers(m1,p2).contains(p4));
        Assert.assertFalse(rC1.getNonVisiblePlayers(m1,p2).contains(p2));
        rC1 = new RoomChecker(m1.getMap(),p1.getPosition());
        Assert.assertTrue(rC1.getFarAwayPlayers(m1,p1,1).isEmpty());
        Assert.assertEquals(p0,rC1.getOtherRoomsVisiblePlayers(m1,p1).get(0));
    }
}
