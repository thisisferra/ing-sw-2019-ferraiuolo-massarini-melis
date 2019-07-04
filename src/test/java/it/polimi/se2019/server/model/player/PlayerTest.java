package it.polimi.se2019.server.model.player;

import it.polimi.se2019.server.model.cards.powerUp.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerTest {

    private Match m1;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;


    @Before
    public void initialize() {

        m1 = new Match(4);
        m1.initializeMatch();
        p1 = new Player("Marco", m1);
        p2 = new Player("Mattia", m1);
        p3 = new Player("Ferra", m1);
        p4 = new Player("Matteo", m1);
        p1.setColor("blue");
        p2.setColor("green");
        p3.setColor("grey");
        p4.setColor("yellow");

    }

    @Test
    public void testGetClientName() {
        p1.setClientName("thisisferra");
        Assert.assertEquals("thisisferra", p1.getClientName());
    }

    @Test
    public void testSetClientName() {
        p1.setClientName("thisisferra");
        Assert.assertEquals("thisisferra", p1.getClientName());
        Assert.assertNotEquals("merklind", p1.getClientName());
    }

    @Test
    public void testGetColor() {
        Assert.assertEquals("blue",p1.getColor());
        Assert.assertEquals("green",p2.getColor());
        Assert.assertEquals("grey",p3.getColor());
        Assert.assertEquals("yellow",p4.getColor());
    }

    @Test
    public void testGetPosition() {
        Assert.assertEquals(-1, p1.getPosition());
        Assert.assertEquals(-1, p2.getPosition());
        Assert.assertEquals(-1, p3.getPosition());
        p1.setPosition(5);
        Assert.assertEquals(5, p1.getPosition());
    }

    @Test
    public void testGetHand() {
        Assert.assertEquals(0, p1.getHand().getWeapons().size());
        Assert.assertEquals(0, p1.getHand().getPowerUps().size());
        PowerUp pu1 = m1.getPowerUpStack().get(0);
        p1.getHand().addPowerUp(pu1);
        String namePowerUp = pu1.getType();
        String colorPowerUp = pu1.getColor();
        Assert.assertEquals(namePowerUp, p1.getHand().getPowerUps().get(0).getType());
        Assert.assertEquals(colorPowerUp, p1.getHand().getPowerUps().get(0).getColor());
        Assert.assertEquals(1, p1.getHand().getPowerUps().size());

    }

    @Test
    public void testCanMove() {
        Assert.assertFalse(p1.getCanMove());
        Assert.assertFalse(p2.getCanMove());
        Assert.assertFalse(p3.getCanMove());
        Assert.assertFalse(p4.getCanMove());
        p1.setCanMove(true);
        Assert.assertTrue(p1.getCanMove());
        p1.setCanMove(false);
        Assert.assertFalse(p1.getCanMove());
    }

    @Test
    public void testFinalFrenzy(){
        Assert.assertEquals(0,p1.getFinalFrenzy());
        Assert.assertEquals(0,p2.getFinalFrenzy());
        Assert.assertEquals(0,p3.getFinalFrenzy());
        Assert.assertEquals(0,p4.getFinalFrenzy());

        p1.setFinalFrenzy(1);
        p2.setFinalFrenzy(1);
        p3.setFinalFrenzy(1);
        p4.setFinalFrenzy(1);

        Assert.assertEquals(1,p1.getFinalFrenzy());
        Assert.assertEquals(1,p2.getFinalFrenzy());
        Assert.assertEquals(1,p3.getFinalFrenzy());
        Assert.assertEquals(1,p4.getFinalFrenzy());

        p1.setFinalFrenzy(2);
        p2.setFinalFrenzy(2);
        p3.setFinalFrenzy(2);
        p4.setFinalFrenzy(2);

        Assert.assertEquals(2,p1.getFinalFrenzy());
        Assert.assertEquals(2,p2.getFinalFrenzy());
        Assert.assertEquals(2,p3.getFinalFrenzy());
        Assert.assertEquals(2,p4.getFinalFrenzy());

    }

    @Test
    public void testPhaseAction(){
        Assert.assertEquals(0,p1.getPhaseAction());
        Assert.assertEquals(0,p2.getPhaseAction());
        Assert.assertEquals(0,p3.getPhaseAction());
        Assert.assertEquals(0,p4.getPhaseAction());
    }

    @Test
    public void testFirstPlayer(){
        p1.setFirstPlayer(true);
        Assert.assertTrue(p1.isFirstPlayer());
        Assert.assertFalse(p2.isFirstPlayer());
        Assert.assertFalse(p3.isFirstPlayer());
        Assert.assertFalse(p4.isFirstPlayer());
    }

    @Test
    public void testCharacter(){
        ArrayList<String> remainingCharacter = new ArrayList<>();
        remainingCharacter.add("dozer");
        remainingCharacter.add("violet");
        remainingCharacter.add("banshee");
        remainingCharacter.add("distructor");
        remainingCharacter.add("sprog");
        Assert.assertTrue(remainingCharacter.contains(p1.getCharacter()));
        remainingCharacter.remove(p1.getCharacter());
        System.out.println(remainingCharacter);
        Assert.assertTrue(remainingCharacter.contains(p2.getCharacter()));
        remainingCharacter.remove(p2.getCharacter());
        System.out.println(remainingCharacter);
        Assert.assertTrue(remainingCharacter.contains(p3.getCharacter()));
        remainingCharacter.remove(p3.getCharacter());
        System.out.println(remainingCharacter);
        Assert.assertTrue(remainingCharacter.contains(p4.getCharacter()));
        remainingCharacter.remove(p4.getCharacter());
        System.out.println(remainingCharacter);
    }

    @Test
    public void testNumberOfAction(){
        Assert.assertEquals(2,p1.getNumberOfAction());
        Assert.assertEquals(2,p2.getNumberOfAction());
        Assert.assertEquals(2,p3.getNumberOfAction());
        Assert.assertEquals(2,p4.getNumberOfAction());
        p1.decreaseNumberOfAction();
        Assert.assertEquals(1,p1.getNumberOfAction());
        p1.resetNumberOfAction();
        Assert.assertEquals(2,p1.getNumberOfAction());
        p1.setFinalFrenzy(1);
        p1.resetNumberOfAction();
        Assert.assertEquals(1,p1.getNumberOfAction());
        p1.setFinalFrenzy(2);
        p1.resetNumberOfAction();
        Assert.assertEquals(2,p1.getNumberOfAction());
    }

    @Test
    public void testPowerUp(){
        p1.pickUpPowerUp();
        p1.pickUpPowerUp();
        p1.pickUpPowerUp();
        Assert.assertEquals(3,p1.getHand().getPowerUps().size());
        p1.pickUpPowerUpToRespawn();
        Assert.assertEquals(4,p1.getHand().getPowerUps().size());
        String color = p1.getHand().getPowerUps().get(0).getColor();
        p1.tradeCube(0);
        if(color.equals("red")){
            Assert.assertEquals(2,p1.getPlayerBoard().getAmmoCubes().getReds());
            Assert.assertEquals(1,p1.getPlayerBoard().getAmmoCubes().getYellows());
            Assert.assertEquals(1,p1.getPlayerBoard().getAmmoCubes().getBlues());
        }

        if(color.equals("blue")){
            Assert.assertEquals(1,p1.getPlayerBoard().getAmmoCubes().getReds());
            Assert.assertEquals(1,p1.getPlayerBoard().getAmmoCubes().getYellows());
            Assert.assertEquals(2,p1.getPlayerBoard().getAmmoCubes().getBlues());
        }

        if(color.equals("yellow")){
            Assert.assertEquals(1,p1.getPlayerBoard().getAmmoCubes().getReds());
            Assert.assertEquals(2,p1.getPlayerBoard().getAmmoCubes().getYellows());
            Assert.assertEquals(1,p1.getPlayerBoard().getAmmoCubes().getBlues());
        }
        p2.pickUpAmmo(m1.getMap().getSpecificSquare(5),m1);
        Assert.assertTrue(p2.getPlayerBoard().getAmmoCubes().getReds()>1 || p2.getPlayerBoard().getAmmoCubes().getYellows()>1 || p2.getPlayerBoard().getAmmoCubes().getBlues()>1);
        if(p2.getPlayerBoard().getAmmoCubes().getReds() + p2.getPlayerBoard().getAmmoCubes().getYellows() + p2.getPlayerBoard().getAmmoCubes().getBlues() <6)
            Assert.assertEquals(1,p2.getHand().getPowerUps().size());
        else Assert.assertEquals(0,p2.getHand().getPowerUps().size());
    }


    @Test
    public void testGetReloadableWeapons(){
        p1.getHand().getWeapons().add(m1.getWeaponStack().remove(0));
        p1.getHand().getWeapons().get(0).setLoad(false);
        p1.getPlayerBoard().setAmmoCubes(new Cubes(2,2,2));
        ArrayList<Weapon> reloadableWeapons = p1.getReloadableWeapons();
        System.out.println(reloadableWeapons);
        Assert.assertEquals(reloadableWeapons.get(0).getType(),p1.getHand().getWeapons().get(0).getType());
    }

    @Test
    public void testPickUpWeapon(){
        p1.setPosition(4);
        p1.pickUpWeapon(0);
        System.out.println(p1.getHand().getWeapons());
        Assert.assertNotNull(p1.getHand().getWeapons().get(0));
    }
    @Test
    public void testResumePlayer(){
        JSONObject p1ToResume = p1.toJSON();

        Player p1Restored = Player.resumePlayer(p1ToResume,m1);

        Assert.assertEquals(p1Restored.getClientName(),p1.getClientName());
        Assert.assertEquals(p1Restored.getNumberOfAction(),p1.getNumberOfAction());
        Assert.assertEquals(p1Restored.getCharacter(),p1.getCharacter());
        Assert.assertEquals(p1Restored.getPosition(),p1.getPosition());
        Assert.assertEquals(p1Restored.getFinalFrenzy(),p1.getFinalFrenzy());
        Assert.assertEquals(p1Restored.getCanMove(),p1.getCanMove());
        Assert.assertEquals(p1Restored.getScore(),p1.getScore());
        Assert.assertEquals(p1Restored.getPhaseAction(),p1.getPhaseAction());
        Assert.assertEquals(p1Restored.getSuspended(),p1.getSuspended());
        Assert.assertEquals(p1Restored.getFirstSpawn(),p1.getFirstSpawn());
        Assert.assertEquals(p1Restored.getFinalFrenzy(),p1.getFinalFrenzy());
        Assert.assertEquals(p1Restored.getTypePlayerBoard(),p1.getTypePlayerBoard());
        Assert.assertEquals(p1Restored.getClientName(),p1.getClientName());
        Assert.assertEquals(p1Restored.getPlayerDead(),p1.getPlayerDead());
    }

}
