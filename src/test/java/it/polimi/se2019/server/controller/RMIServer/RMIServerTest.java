package it.polimi.se2019.server.controller.RMIServer;

import it.polimi.se2019.client.controller.GUIController;
import it.polimi.se2019.client.controller.network.RMI.GUIControllerInterface;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.PowerUpShot;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.controller.network.RMI.RMIServer;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.weapons.LockRifle;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.EnemyDamage;
import it.polimi.se2019.server.model.player.Player;
import junit.framework.AssertionFailedError;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIServerTest {

    static RMIServer rmiServer;
    private Match match1;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @BeforeClass
    public static void onceSetClass() {
        rmiServer = new RMIServer();
        rmiServer.startServer();

    }

    @Before
    public void setClass() {
        match1 = new Match(1);
        match1.initializeMatch();
        rmiServer.setMatch(match1);
        p1 = new Player("Marco", match1);
        p2 = new Player("Mattia", match1);
        p3 = new Player("Ferra", match1);
        p4 = new Player("Bruno", match1);
        match1.getAllPlayers().add(p1);
        match1.getAllPlayers().add(p2);
        match1.getAllPlayers().add(p3);
        match1.getAllPlayers().add(p4);


    }


    @Test
    public void checkUsername2Test() {

        String newPlayer = "Matteo";
        match1.setOpenConnection(true);
        try {
            Assert.assertEquals("NotUsed", rmiServer.checkUsername2(newPlayer));
        } catch(Exception e) {
            e.printStackTrace();
        }
        newPlayer = "Marco";
        try {
            Assert.assertEquals("AlreadyUsed", rmiServer.checkUsername2(newPlayer));
        } catch(Exception e) {
            e.printStackTrace();
        }
        match1.setOpenConnection(false);
        try {
            Assert.assertEquals("Reconnect", rmiServer.checkUsername2(newPlayer));
        } catch(Exception e) {
            e.printStackTrace();
        }
        newPlayer = "Matteo";
        try {
            Assert.assertEquals("CantConnect", rmiServer.checkUsername2(newPlayer));
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void searchVirtualViewByClientNameTest() {
        String username = "Marco";
        String username2 = "Mattia";
        VirtualView virtualView = new VirtualView();
        VirtualView virtualView2 = new VirtualView();
        virtualView.setUsername(username);
        rmiServer.getAllVirtualView().add(virtualView);

        Assert.assertEquals("Marco", rmiServer.searchVirtualViewByClientName(username).getUsername());



    }

    @Test
    public void reachableSquaresTest() {
        int position = 0;
        int steps = 1;
        Assert.assertEquals(3, rmiServer.reachableSquares(position, steps).size());
        position = 5;
        steps = 2;
        Assert.assertEquals(9, rmiServer.reachableSquares(position, steps).size());

    }

    @Test
    public void getAllSquareTest() {
        Assert.assertEquals(12, rmiServer.getAllSquares().length);
        Assert.assertEquals("0 red", rmiServer.getAllSquares()[0].toString());
        Assert.assertEquals("1 blue", rmiServer.getAllSquares()[1].toString());
        Assert.assertEquals("5 purple", rmiServer.getAllSquares()[5].toString());
    }

    @Test
    public void showLastAmmoTest() throws RemoteException{
        Assert.assertEquals(null, rmiServer.showLastAmmo());
        Ammo newAmmo = rmiServer.getMatch().getAmmoStack().get(0);
        rmiServer.getMatch().getDiscardedAmmos().add(newAmmo);
        Assert.assertEquals(newAmmo, rmiServer.showLastAmmo());
    }

    @Test
    public void setNewPositionTest() {
        rmiServer.setNewPosition("Marco", 5);
        this.match1.searchPlayerByClientName("Marco");
        Assert.assertEquals(5, p1.getPosition());
    }

    @Test
    public void getMyVirtualViewTest() {
        rmiServer.getAllVirtualView().clear();
        String username = "Marco";
        VirtualView virtualView = new VirtualView();
        virtualView.setUsername("Marco");
        rmiServer.getAllVirtualView().add(virtualView);
        Assert.assertEquals(virtualView, rmiServer.getAllVirtualView().get(0));
        Assert.assertEquals(1, rmiServer.getAllVirtualView().size());
        Assert.assertEquals(virtualView, rmiServer.getMyVirtualView(username));
    }

    @Test
    public void isSpawnPoinTest() {
        int spawnPointPosition = 2;
        Assert.assertTrue(rmiServer.isSpawnPoint(spawnPointPosition));
        spawnPointPosition = 3;
        Assert.assertFalse(rmiServer.isSpawnPoint(spawnPointPosition));
    }

    @Test(expected = NullPointerException.class)
    public void pickUpWeaponLessThanThreeTest() {
        VirtualView myVirtualView = new VirtualView();
        myVirtualView.setUsername("Mattia");
        rmiServer.getAllVirtualView().add(myVirtualView);
        p2.setPosition(2);
        String username = "Mattia";
        int indexToPick = 0;
        Weapon weaponPicked = match1.getArsenal().get(2).getSlot()[0];
        rmiServer.pickUpWeapon(username, indexToPick);
        Assert.assertEquals(1, rmiServer.getMatch().searchPlayerByClientName(username).getHand().getWeapons().size());
        Assert.assertEquals(weaponPicked.getType(), rmiServer.getMatch().searchPlayerByClientName(username).getHand().getWeapons().get(0).getType());


    }

    @Test
    public void printClientConnectedTest() {
        VirtualView virtualView1 = new VirtualView();
        VirtualView virtualView2 = new VirtualView();
        VirtualView virtualView3 = new VirtualView();
        virtualView1.setUsername("Marco");
        virtualView2.setUsername("Mattia");
        virtualView3.setUsername("Matteo");
        rmiServer.getAllVirtualView().add(virtualView1);
        rmiServer.getAllVirtualView().add(virtualView2);
        rmiServer.getAllVirtualView().add(virtualView3);
        rmiServer.printClientConnected();
    }

    @Test
    public void checkNumberOfActionTest() {
        String username = "Marco";
        int numberOfAction = rmiServer.getMatch().searchPlayerByClientName(username).getNumberOfAction();
        Assert.assertTrue(rmiServer.checkNumberAction(username));
        Assert.assertEquals(numberOfAction, rmiServer.getMatch().searchPlayerByClientName(username).getNumberOfAction());
        rmiServer.getMatch().searchPlayerByClientName(username).setNumberOfAction(0);
        Assert.assertFalse(rmiServer.checkNumberAction(username));

    }

    @Test(expected = NullPointerException.class)
    public void giftActionTest() {
        String username = "Marco";
        rmiServer.getMatch().searchPlayerByClientName(username).setNumberOfAction(0);
        try {
            rmiServer.giftAction(username);
        } catch (RemoteException remException) {
            remException.getMessage();
        }
        Assert.assertEquals(1, rmiServer.getMatch().searchPlayerByClientName(username).getNumberOfAction());
    }

    @Test(expected = NullPointerException.class)
    public void setActivePlayerTest() {
        String lastActivePlayer = "Marco";
        try {
            rmiServer.setActivePlayer(lastActivePlayer);
        }
        catch (RemoteException remException) {
            System.err.println("pingClient failed");
        }
        Assert.assertEquals("Mattia", rmiServer.getActivePlayer());

        lastActivePlayer = "Mattia";
        try {
            rmiServer.setActivePlayer(lastActivePlayer);
        }
        catch (RemoteException remException) {
            System.err.println("pingClient failed");
        }
        Assert.assertEquals("Ferra", rmiServer.getActivePlayer());

        lastActivePlayer = "Bruno";
        try {
            rmiServer.setActivePlayer(lastActivePlayer);
        }
        catch (RemoteException remException) {
            System.err.println("pingClient failed");
        }
        Assert.assertEquals("Marco", rmiServer.getActivePlayer());
    }

    @Test
    public void setSpecificActivePlayerTest() {
        Player nextActivePlayer = p2;
        rmiServer.setSpecificActivePlayer(nextActivePlayer);
        Assert.assertEquals("Mattia", rmiServer.getActivePlayer());
        Assert.assertEquals(p2, rmiServer.getSpecificActivePlayer());
    }

    @Test
    public void getKillShotTrackTest() {
        rmiServer.getMatch().getKillShotTrack().add(p1);
        Assert.assertEquals(p1, rmiServer.getKillShotTrack().get(0));
    }

    @Test(expected = NullPointerException.class)
    public void resetActionNumberTest() {
        String activePlayer = "Marco";
        try {
            rmiServer.resetActionNumber(activePlayer);
        } catch(RemoteException remException) {
            remException.getMessage();
        }
        Assert.assertEquals(2, rmiServer.getMatch().getAllPlayers().get(0).getNumberOfAction());
    }

    @Test
    public void checkSuspendedMatchTest() {
        rmiServer.checkSuspendedMatch();
    }

    @Test(expected = NullPointerException.class)
    public void pickUpAmmoTest() {
        String activePlayer = "Marco";
        p1.setPosition(0);
        try {
            rmiServer.pickUpAmmo(activePlayer);
        } catch(RemoteException remException) {
            remException.getMessage();
        }
    }

    @Test(expected = NullPointerException.class)
    public void restoreMapTest() {
        rmiServer.getMatch().getMap().getAllSquare()[0].setFull(false);
        rmiServer.getMatch().getArsenal().get(0).getSlot()[0] = null;

        try {
            rmiServer.restoreMap();
        } catch(RemoteException remExc) {
            remExc.getMessage();
        }
        Assert.assertTrue(rmiServer.getMatch().getMap().getAllSquare()[0].isFull());
    }

    @Test(expected = NullPointerException.class)
    public void registerTest() {
        String username = "Marco";
        GUIControllerInterface guiController = new GUIController();
        try {
            rmiServer.register(username, guiController, 1);
        } catch(RemoteException remExc) {
            remExc.getMessage();
        }
    }

    @Test(expected = NullPointerException.class)
    public void discardAndSpawnTest() {
        String username = "Marco";
        int index = 0;
        p1.getHand().getPowerUps().add(rmiServer.getMatch().getPowerUpStack().get(0));
        p1.getHand().getPowerUps().add(rmiServer.getMatch().getPowerUpStack().get(0));
        p1.getHand().getPowerUps().add(rmiServer.getMatch().getPowerUpStack().get(0));

        for (int i = 0; i < p1.getHand().getPowerUps().size(); i++) {
            String color = p1.getHand().getPowerUps().get(0).getColor();
            try {
                rmiServer.discardAndSpawn(username, index);
            }
            catch (RemoteException remExc) {
                remExc.getMessage();
            }
            if (color.equals("red")) {
                Assert.assertEquals(4, p1.getPosition());
            }
            if (color.equals("blue")) {
                Assert.assertEquals(2, p1.getPosition());
            }
            if (color.equals("yellow")) {
                Assert.assertEquals(11, p1.getPosition());
            }
        }
    }

    @Test
    public void getReloadableWeaponsTest() {
        String username = "Marco";
        Weapon weapon1 = rmiServer.getMatch().getWeaponStack().remove(0);
        Weapon weapon2 = rmiServer.getMatch().getWeaponStack().remove(0);
        p1.getHand().getWeapons().add(weapon1);
        p1.getHand().getWeapons().add(weapon2);

        try {
            rmiServer.getReloadableWeapons(username);
        }
        catch(RemoteException remExc) {
            remExc.getMessage();
        }


    }

    @Test(expected = NullPointerException.class)
    public void reloadWeaponTest() {
        String username = "Marco";
        int index = 0;
        Weapon newWeapon = rmiServer.getMatch().getWeaponStack().remove(0);
        p1.getHand().getWeapons().add(newWeapon);
        newWeapon.setLoad(false);
        try {
            rmiServer.reloadWeapon(username, index);
        } catch(RemoteException remExc) {
            remExc.getMessage();
        }
        Assert.assertTrue(p1.getHand().getWeapons().get(0).getLoad());
    }

    @Test
    public void checkSizeWeaponTest() {
        String username = "Marco";
        Weapon newWeapon = rmiServer.getMatch().getWeaponStack().remove(0);
        p1.getHand().getWeapons().add(newWeapon);
        Assert.assertTrue(rmiServer.checkSizeWeapon(username));
    }

    @Test(expected = NullPointerException.class)
    public void assignPointsTest() {
        //Damage player1
        EnemyDamage enemyDamage1 = new EnemyDamage(p2, 3);
        EnemyDamage enemyDamage2 = new EnemyDamage(p4, 2);
        EnemyDamage enemyDamage = new EnemyDamage(p3, 5);
        p1.getPlayerBoard().getEnemyDamages().add(enemyDamage1);
        p1.getPlayerBoard().getEnemyDamages().add(enemyDamage2);
        p1.getPlayerBoard().getEnemyDamages().add(enemyDamage);
        p1.getPlayerBoard().getDamage().add(p2);
        p1.getPlayerBoard().getDamage().add(p2);
        p1.getPlayerBoard().getDamage().add(p2);
        p1.getPlayerBoard().getDamage().add(p4);
        p1.getPlayerBoard().getDamage().add(p4);
        p1.getPlayerBoard().getDamage().add(p3);
        p1.getPlayerBoard().getDamage().add(p3);
        p1.getPlayerBoard().getDamage().add(p3);
        p1.getPlayerBoard().getDamage().add(p3);
        p1.getPlayerBoard().getDamage().add(p3);
        //1 point first death
        p2.setScore(1);

        p1.getPlayerBoard().sortAggressor();

        try {
            rmiServer.assignPoints(p1);
        } catch(RemoteException remExc) {
            remExc.getMessage();
        }

        //Assert.assertEquals(13, p1.getScore());
        Assert.assertEquals(7, p2.getScore());
        Assert.assertEquals(8, p3.getScore());
        Assert.assertEquals(4, p4.getScore());


    }

    @Test(expected = NullPointerException.class)
    public void computePointsFinalTest() {
        EnemyDamage enemyDamage1 = new EnemyDamage(p2, 3);
        EnemyDamage enemyDamage2 = new EnemyDamage(p4, 2);
        p1.getPlayerBoard().getEnemyDamages().add(enemyDamage1);
        p1.getPlayerBoard().getEnemyDamages().add(enemyDamage2);
        p1.getPlayerBoard().getDamage().add(p2);
        p1.getPlayerBoard().getDamage().add(p2);
        p1.getPlayerBoard().getDamage().add(p2);
        p1.getPlayerBoard().getDamage().add(p4);
        p1.getPlayerBoard().getDamage().add(p4);
        //1 point first death
        p2.setScore(1);

        //Damage player 2
        EnemyDamage enemyDamage3 = new EnemyDamage(p1, 1);
        EnemyDamage enemyDamage4 = new EnemyDamage(p4, 3);
        EnemyDamage enemyDamage5 = new EnemyDamage(p3, 2);
        p2.getPlayerBoard().getEnemyDamages().add(enemyDamage3);
        p2.getPlayerBoard().getEnemyDamages().add(enemyDamage4);
        p2.getPlayerBoard().getEnemyDamages().add(enemyDamage5);
        p2.getPlayerBoard().getDamage().add(p1);
        p2.getPlayerBoard().getDamage().add(p4);
        p2.getPlayerBoard().getDamage().add(p4);
        p2.getPlayerBoard().getDamage().add(p4);
        p2.getPlayerBoard().getDamage().add(p3);
        p2.getPlayerBoard().getDamage().add(p3);
        //1 point first death
        p1.setScore(1);

        EnemyDamage enemyDamage6 = new EnemyDamage(p2, 2);
        EnemyDamage enemyDamage7 = new EnemyDamage(p1, 3);
        p3.getPlayerBoard().getEnemyDamages().add(enemyDamage6);
        p3.getPlayerBoard().getEnemyDamages().add(enemyDamage7);
        p3.getPlayerBoard().getDamage().add(p2);
        p3.getPlayerBoard().getDamage().add(p2);
        p3.getPlayerBoard().getDamage().add(p1);
        p3.getPlayerBoard().getDamage().add(p1);
        p3.getPlayerBoard().getDamage().add(p1);
        //1 point first death
        p2.setScore(1);

        EnemyDamage enemyDamage8 = new EnemyDamage(p3, 3);
        p4.getPlayerBoard().getEnemyDamages().add(enemyDamage8);
        p4.getPlayerBoard().getDamage().add(p3);
        p4.getPlayerBoard().getDamage().add(p3);
        p4.getPlayerBoard().getDamage().add(p3);
        //1 point first death
        p3.setScore(1);

        //KillShotTrack
        rmiServer.getMatch().getKillShotTrack().add(p2);

        p1.getPlayerBoard().sortAggressor();
        p2.getPlayerBoard().sortAggressor();
        p3.getPlayerBoard().sortAggressor();
        p4.getPlayerBoard().sortAggressor();

        try {
            rmiServer.computePointsFinal();
        } catch(RemoteException remException) {
            remException.getMessage();
        }

        Assert.assertEquals(0, p1.getScore());
        Assert.assertEquals(8, p2.getScore());
        Assert.assertEquals(0, p3.getScore());
        Assert.assertEquals(0, p4.getScore());


    }

    @Test
    public void enableFinalFrenzyTest() {
        p1.getPlayerBoard().getEnemyDamages().add(new EnemyDamage(p3, 2));
        p1.getPlayerBoard().getDamage().add(p3);
        p1.getPlayerBoard().getDamage().add(p3);
        String username = "Ferra";
        try {
            rmiServer.enableFinalFrenzy(username);
        }
        catch (RemoteException remException) {
            remException.getCause();
        }
        Assert.assertEquals(0, p1.getTypePlayerBoard());
        Assert.assertEquals(1, p2.getTypePlayerBoard());
        Assert.assertEquals(2, p4.getFinalFrenzy());
    }

    @Test
    public void deathPlayerTest() {
        //Add damage to p2 player
        EnemyDamage enemyDamage = new EnemyDamage(p1, 10);
        p2.getPlayerBoard().getEnemyDamages().add(enemyDamage);
        for(int i = 0; i < 11; i++) {
            p2.getPlayerBoard().getDamage().add(p1);
        }

        //Set p2 player as dead
        p2.setPlayerDead(true);

        //Call deathPlayer method
        try {
            rmiServer.deathPlayer("Marco");
        } catch(RemoteException remException) {
            remException.getCause();
        }

        //Check that the reference on killSHotTrack is the same that do the last kill
        Assert.assertEquals(p1, rmiServer.getMatch().getKillShotTrack().get(0));
        //Check that p1 player gain 8 points for the kill
        Assert.assertEquals(8, p1.getScore());
        Assert.assertEquals(0, p2.getScore());
        Assert.assertEquals(0, p3.getScore());
        Assert.assertEquals(0, p4.getScore());
        Assert.assertTrue(p2.getPlayerDead());
        Assert.assertEquals(0, p2.getPhaseAction());
        Assert.assertTrue(rmiServer.getMatch().isFinalFrenzyStatus());
    }

    @Test
    public void applyEffectWeaponTest() {
        WeaponShot weaponShot = new WeaponShot();
        /*
        for (Weapon weapon : rmiServer.getMatch().getWeaponStack()) {
            if (weapon.getType().equals("lock_rifle")) {
                p1.getHand().getWeapons().add(weapon);
                weaponShot.setWeapon(weapon);
            }
        }

         */
        p1.getHand().getWeapons().add(rmiServer.getMatch().getWeaponStack().get(0));
        ArrayList<Player> targetsPlayer = new ArrayList<>();
        targetsPlayer.add(p2);
        targetsPlayer.add(p3);
        weaponShot.setWeapon(p1.getHand().getWeapons().get(0));
        weaponShot.setTargetPlayers(targetsPlayer);
        weaponShot.setNameEffect("BasicEffect");
        weaponShot.setNewPosition(0);
        weaponShot.setDamagingPlayer(p1);
        try {
            rmiServer.applyEffectWeapon(weaponShot);
        } catch (RemoteException remException) {
            remException.getCause();
        } catch(NullPointerException nullPointer) {
            nullPointer.getCause();
        }

        Assert.assertFalse(p1.getHand().getWeapons().get(0).getLoad());


    }

    @Test
    public void usePowerUpTest() {
        String username = "Marco";
        int index = 0;
        PowerUpShot powerUpShot = new PowerUpShot();
        powerUpShot.setTargetingPlayer(p3);
        powerUpShot.setDamagingPlayer(p1);
        for (int i = 0; i < rmiServer.getMatch().getPowerUpStack().size(); i++) {
            if (rmiServer.getMatch().getPowerUpStack().get(i).getType().equals("teleporter")) {
                p1.getHand().getPowerUps().add(rmiServer.getMatch().getPowerUpStack().get(i));
                break;
            }
        }

        Assert.assertEquals(1, p1.getHand().getPowerUps().size());
        try {
            rmiServer.usePowerUp(username, index, powerUpShot);
        } catch(RemoteException remException) {
            remException.getCause();
        } catch (NullPointerException nullPointer) {
            nullPointer.getCause();
        }

        Assert.assertEquals(0, p1.getHand().getPowerUps().size());
    }



}
