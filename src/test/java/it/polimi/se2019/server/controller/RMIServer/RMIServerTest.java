package it.polimi.se2019.server.controller.RMIServer;

import it.polimi.se2019.client.controller.GUIController;
import it.polimi.se2019.client.controller.network.RMI.GUIControllerInterface;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.RMI.RMIServer;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;
import junit.framework.AssertionFailedError;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIServerTest {

    static RMIServer rmiServer;
    Match match1;
    Player p1;
    Player p2;
    Player p3;
    Player p4;

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

    @Test
    public void usePowerUpTest() {

    }





}