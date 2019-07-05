package it.polimi.se2019.client.controller;

import it.polimi.se2019.client.controller.network.RMI.GUIControllerInterface;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.client.view.RemoteView;
import it.polimi.se2019.server.controller.WeaponShot;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import it.polimi.se2019.server.model.player.Player;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * This class contains the stub of the server, so through this stub we can call server method.
 * It contains also the remoteView of all players used to show the right information on the GUI.
 * @author merklind, mattiamassarini, thisisferra
 */

public class GUIController implements GUIControllerInterface {

    private GUI guiObject;

    PrintStream out;

    private String username;

    private RMIServerInterface rmiStub;

    private ArrayList<RemoteView> allViews = new ArrayList<>();

    public GUIController() {
        //for testing only
    }

    public GUIController(String IPAddress, GUI guiObject) {
        try {
            //Registry registry = LocateRegistry.getRegistry("192.168.",0);
            Registry registry = LocateRegistry.getRegistry(IPAddress,0);
            rmiStub = (RMIServerInterface) registry.lookup("remServer");
            UnicastRemoteObject.exportObject(this, 0);
            this.guiObject = guiObject;
        }
        catch (Exception e) {
            System.out.println("Errore nella creazione di GUIController");
        }
        out = new PrintStream(System.out);
    }

    /**
     * Get the stub object
     * @return the stub object
     */
    public RMIServerInterface getRmiStub() {
        return this.rmiStub;
    }

    /**
     * Get the username
     * @return the username attribute
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the allViews ArrayList
     * @return allViews ArrayList
     */
    public ArrayList<RemoteView> getAllViews() {
        return  this.allViews;
    }

    /**
     * Scan allViews ArrayList and search the one that has the username equals to username attribute
     * @return The remote view with my username
     */
    public RemoteView getMyRemoteView() {
        for (RemoteView remoteView : allViews) {
            if (remoteView.getUsername().equals(username)) {
                return remoteView;
            }
        }
        return null;
    }

    /**
     * Set the username
     * @param username: my username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Scan the ArrayList of virtualView passed as parameter, retrieve the username of each virtualView
     * then scan the ArrayList allView, if exists a remoteView with the same username of the virtualView, nothing happens,
     * create and add to allViews ArrayList otherwise
     * @param allVirtualView: ArrayList of virtualView
     * @throws RemoteException
     */
    public void initRemoteView(ArrayList<VirtualView> allVirtualView) throws RemoteException {
        boolean alreadyCreated = false;
        for (VirtualView virtualView : allVirtualView) {
            String username = virtualView.getUsername();
            for (RemoteView remoteView : allViews) {
                if (remoteView.getUsername().equals(username)) {
                    alreadyCreated = true;
                }
            }
            if (!alreadyCreated) {
                allViews.add(new RemoteView(username));
            }
            alreadyCreated = false;
        }
        //for (RemoteView remoteView : allViews) {
        //    out.println(remoteView.getUsername());
        //}
        update(allVirtualView);

    }

    /**
     * Restore the GUI
     */
    public void restoreGUI() {
        Platform.runLater(()->{
            guiObject.getWindow().close();
            guiObject.setGameScene();
            guiObject.setFigures();
            guiObject.getWindow().setScene(guiObject.getScene());
            guiObject.getWindow().show();
        });
    }

    /**
     * Show the login window
     */
    public void recallLoginScene() {
        Platform.runLater(()->{
            guiObject.getWindow().close();
            guiObject.getWindow().setScene(guiObject.setLoginScene());
            guiObject.getWindow().show();
        });
    }

    /**
     *Restore the remote view when a match is resumed
     * @param virtualViewToRestore: virtual view to restore
     */
    public void restoreRemoteView(VirtualView virtualViewToRestore) {
        allViews.add(new RemoteView(virtualViewToRestore.getUsername()));
        getMyRemoteView().updateRemoteView(virtualViewToRestore);
    }

    /**
     * Update all the data of all remotes view with the data contains in virtualView.
     * Then update the GUI
     * @param allVirtualView: ArrayList of virtualView containing the data to save in remoteView
     * @throws RemoteException
     */
    @Override
    public void update(ArrayList<VirtualView> allVirtualView) throws RemoteException {
        for (VirtualView virtualView : allVirtualView) {
            String virtualUsername = virtualView.getUsername();
            for (RemoteView remoteView : allViews) {
                if (remoteView.getUsername().equals(virtualUsername)) {
                    remoteView.updateRemoteView(virtualView);
                }
            }
        }
        if (guiObject.getScene() != null) {
            Platform.runLater(()->{
                    guiObject.updateAllGUI();
            });
        }
    }

    /**
     * Do a remote call to the server to apply the effect of a weapon
     * @param weaponShot: object that contains the info of a shoot
     * @throws RemoteException if the client can't call the server
     */
    public void applyEffect(WeaponShot weaponShot) throws RemoteException{
        this.getRmiStub().applyEffectWeapon(weaponShot);
    }

    /**
     * Show a message in each client GUI
     * @param message: the message to show
     * @throws RemoteException
     */
    @Override
    public void showMessage(String message) throws RemoteException {
        TextArea textArea = guiObject.getTextArea();
        textArea.setText(message + "\n" + textArea.getText());

    }

    /**
     * Show a windows in the GUI to choose a powerUp for the respawn
     */
    public void respawnDialog() {
        Platform.runLater(() -> this.guiObject.startingDraw("Choose one power ups to discard.\nIt determines your spawn location, based on its color."));
    }

    /**
     * Method called by the server to verify if the client is still alive
     * @return
     */
    public int pingClient() {
        return 13;
        //System.out.println("Client " +this.username + " connesso");
    }

    /**
     * Call a GUI method to show the waiting window
     * @throws RemoteException
     */
    public void waitPlayers() throws RemoteException {
        Platform.runLater(() -> this.guiObject.setWaitScene());
    }

    /**
     * Call a GUI method to start the GUI
     * @throws RemoteException
     */
    public void startingMatch() throws RemoteException {
        Platform.runLater(() -> this.guiObject.startMatch());
    }

    /**
     * Call a GUI method to show the end window
     * @param allPlayers
     */
    public void showEndGameWindow(ArrayList<Player> allPlayers){
        Platform.runLater(() -> {
            this.guiObject.setEndScene(allPlayers);
        });
    }

    /**
     * Call a GUI method to close the GUI
     * @throws RemoteException
     */
    public void closeGUI() throws RemoteException{
        Platform.runLater(() -> this.guiObject.closeWindow());
    }
}