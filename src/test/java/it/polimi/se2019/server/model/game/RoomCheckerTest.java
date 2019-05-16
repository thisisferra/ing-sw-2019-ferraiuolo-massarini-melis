package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.map.Square;
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
}
