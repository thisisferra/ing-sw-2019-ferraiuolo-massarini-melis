package it.polimi.se2019.server.model.game;

import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MovementCheckerTest {
    Match m1;
    Match m2;
    Match m3;
    Match m4;

    @Before
    public void setMovementCheckerTest() {
        m1 = new Match(1, 3);
        m2 = new Match(2, 3);
        m3 = new Match(3, 3);
        m4 = new Match(4, 3);
        m1.initGameField();
        m2.initGameField();
        m3.initGameField();
        m4.initGameField();
    }

    @Test
    public void testCheck(){

        MovementChecker mC1;
        MovementChecker mC2;
        MovementChecker mC3;
        MovementChecker mC4;

        for(int i=0;i<m1.getMap().getAllSquare().length;i++){
            if(i!=3){
                mC1 = new MovementChecker(m1.getMap().getAllSquare(),1,i);
                mC2 = new MovementChecker(m1.getMap().getAllSquare(),2,i);
                mC3 = new MovementChecker(m1.getMap().getAllSquare(),3,i);
                mC4 = new MovementChecker(m1.getMap().getAllSquare(),4,i);

                mC1.check();
                mC2.check();
                mC3.check();
                mC4.check();

                Assert.assertTrue(mC1.getReachableSquares().size()<= mC2.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC2.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC2.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC3.getReachableSquares().size()<= mC4.getReachableSquares().size());


                Assert.assertTrue(mC1.getAllUpwardsSquares().size()>= mC1.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC1.getAllRightSquares().size()>= mC1.getWalkableRightSquares().size());
                Assert.assertTrue(mC1.getAllDownwardsSquares().size()>= mC1.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC1.getAllLeftSquares().size()>= mC1.getWalkableLeftSquares().size());

                Assert.assertTrue(mC2.getAllUpwardsSquares().size()>= mC2.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC2.getAllRightSquares().size()>= mC2.getWalkableRightSquares().size());
                Assert.assertTrue(mC2.getAllDownwardsSquares().size()>= mC2.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC2.getAllLeftSquares().size()>= mC2.getWalkableLeftSquares().size());

                Assert.assertTrue(mC3.getAllUpwardsSquares().size()>= mC3.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC3.getAllRightSquares().size()>= mC3.getWalkableRightSquares().size());
                Assert.assertTrue(mC3.getAllDownwardsSquares().size()>= mC3.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC3.getAllLeftSquares().size()>= mC3.getWalkableLeftSquares().size());

                Assert.assertTrue(mC4.getAllUpwardsSquares().size()>= mC4.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC4.getAllRightSquares().size()>= mC4.getWalkableRightSquares().size());
                Assert.assertTrue(mC4.getAllDownwardsSquares().size()>= mC4.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC4.getAllLeftSquares().size()>= mC4.getWalkableLeftSquares().size());

            }
        }

        for(int i=0;i<m2.getMap().getAllSquare().length;i++){

            if(i!=3 && i!=8){
                mC1 = new MovementChecker(m2.getMap().getAllSquare(),1,i);
                mC2 = new MovementChecker(m2.getMap().getAllSquare(),2,i);
                mC3 = new MovementChecker(m2.getMap().getAllSquare(),3,i);
                mC4 = new MovementChecker(m2.getMap().getAllSquare(),4,i);

                mC1.check();
                mC2.check();
                mC3.check();
                mC4.check();

                Assert.assertTrue(mC1.getReachableSquares().size()<= mC2.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC2.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC2.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC3.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC1.getAllUpwardsSquares().size()>= mC1.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC1.getAllRightSquares().size()>= mC1.getWalkableRightSquares().size());
                Assert.assertTrue(mC1.getAllDownwardsSquares().size()>= mC1.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC1.getAllLeftSquares().size()>= mC1.getWalkableLeftSquares().size());

                Assert.assertTrue(mC2.getAllUpwardsSquares().size()>= mC2.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC2.getAllRightSquares().size()>= mC2.getWalkableRightSquares().size());
                Assert.assertTrue(mC2.getAllDownwardsSquares().size()>= mC2.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC2.getAllLeftSquares().size()>= mC2.getWalkableLeftSquares().size());

                Assert.assertTrue(mC3.getAllUpwardsSquares().size()>= mC3.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC3.getAllRightSquares().size()>= mC3.getWalkableRightSquares().size());
                Assert.assertTrue(mC3.getAllDownwardsSquares().size()>= mC3.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC3.getAllLeftSquares().size()>= mC3.getWalkableLeftSquares().size());

                Assert.assertTrue(mC4.getAllUpwardsSquares().size()>= mC4.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC4.getAllRightSquares().size()>= mC4.getWalkableRightSquares().size());
                Assert.assertTrue(mC4.getAllDownwardsSquares().size()>= mC4.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC4.getAllLeftSquares().size()>= mC4.getWalkableLeftSquares().size());
            }
        }

        for(int i=0;i<m3.getMap().getAllSquare().length;i++){
            if(i!=8){

                mC1 = new MovementChecker(m3.getMap().getAllSquare(),1,i);
                mC2 = new MovementChecker(m3.getMap().getAllSquare(),2,i);
                mC3 = new MovementChecker(m3.getMap().getAllSquare(),3,i);
                mC4 = new MovementChecker(m3.getMap().getAllSquare(),4,i);

                mC1.check();
                mC2.check();
                mC3.check();
                mC4.check();

                Assert.assertTrue(mC1.getReachableSquares().size()<= mC2.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC2.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC2.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC3.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC1.getAllUpwardsSquares().size()>= mC1.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC1.getAllRightSquares().size()>= mC1.getWalkableRightSquares().size());
                Assert.assertTrue(mC1.getAllDownwardsSquares().size()>= mC1.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC1.getAllLeftSquares().size()>= mC1.getWalkableLeftSquares().size());

                Assert.assertTrue(mC2.getAllUpwardsSquares().size()>= mC2.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC2.getAllRightSquares().size()>= mC2.getWalkableRightSquares().size());
                Assert.assertTrue(mC2.getAllDownwardsSquares().size()>= mC2.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC2.getAllLeftSquares().size()>= mC2.getWalkableLeftSquares().size());

                Assert.assertTrue(mC3.getAllUpwardsSquares().size()>= mC3.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC3.getAllRightSquares().size()>= mC3.getWalkableRightSquares().size());
                Assert.assertTrue(mC3.getAllDownwardsSquares().size()>= mC3.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC3.getAllLeftSquares().size()>= mC3.getWalkableLeftSquares().size());

                Assert.assertTrue(mC4.getAllUpwardsSquares().size()>= mC4.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC4.getAllRightSquares().size()>= mC4.getWalkableRightSquares().size());
                Assert.assertTrue(mC4.getAllDownwardsSquares().size()>= mC4.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC4.getAllLeftSquares().size()>= mC4.getWalkableLeftSquares().size());
            }
        }

        for(int i=0;i<m4.getMap().getAllSquare().length;i++){
            if(i!=3){

                mC1 = new MovementChecker(m4.getMap().getAllSquare(),1,i);
                mC2 = new MovementChecker(m4.getMap().getAllSquare(),2,i);
                mC3 = new MovementChecker(m4.getMap().getAllSquare(),3,i);
                mC4 = new MovementChecker(m4.getMap().getAllSquare(),4,i);

                mC1.check();
                mC2.check();
                mC3.check();
                mC4.check();

                Assert.assertTrue(mC1.getReachableSquares().size()<= mC2.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC1.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC2.getReachableSquares().size()<= mC3.getReachableSquares().size());
                Assert.assertTrue(mC2.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC3.getReachableSquares().size()<= mC4.getReachableSquares().size());

                Assert.assertTrue(mC1.getAllUpwardsSquares().size()>= mC1.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC1.getAllRightSquares().size()>= mC1.getWalkableRightSquares().size());
                Assert.assertTrue(mC1.getAllDownwardsSquares().size()>= mC1.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC1.getAllLeftSquares().size()>= mC1.getWalkableLeftSquares().size());

                Assert.assertTrue(mC2.getAllUpwardsSquares().size()>= mC2.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC2.getAllRightSquares().size()>= mC2.getWalkableRightSquares().size());
                Assert.assertTrue(mC2.getAllDownwardsSquares().size()>= mC2.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC2.getAllLeftSquares().size()>= mC2.getWalkableLeftSquares().size());

                Assert.assertTrue(mC3.getAllUpwardsSquares().size()>= mC3.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC3.getAllRightSquares().size()>= mC3.getWalkableRightSquares().size());
                Assert.assertTrue(mC3.getAllDownwardsSquares().size()>= mC3.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC3.getAllLeftSquares().size()>= mC3.getWalkableLeftSquares().size());

                Assert.assertTrue(mC4.getAllUpwardsSquares().size()>= mC4.getWalkableUpwardsSquares().size());
                Assert.assertTrue(mC4.getAllRightSquares().size()>= mC4.getWalkableRightSquares().size());
                Assert.assertTrue(mC4.getAllDownwardsSquares().size()>= mC4.getWalkableDownwardsSquares().size());
                Assert.assertTrue(mC4.getAllLeftSquares().size()>= mC4.getWalkableLeftSquares().size());
            }
        }
    }
}
