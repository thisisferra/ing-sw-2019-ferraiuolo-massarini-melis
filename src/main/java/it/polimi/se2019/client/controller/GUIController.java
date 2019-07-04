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

//ritorno
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

    //GETTER

    public RMIServerInterface getRmiStub() {
        return this.rmiStub;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<RemoteView> getAllViews() {
        return  this.allViews;
    }

    public RemoteView getMyRemoteView() {
        for (RemoteView remoteView : allViews) {
            if (remoteView.getUsername().equals(username)) {
                return remoteView;
            }
        }
        return null;
    }

    //SETTER

    public void setUsername(String username) {
        this.username = username;
    }

    //ALTRO


    //Create remote view if it hasn't already created
    //Call to "update" that update all remotes view
    public void initRemoteView(ArrayList<VirtualView> allVirtualView) throws RemoteException {
        boolean alreadyCreated = false;
        //out.println("Remote view locali: ");
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

    public void restoreGUI() {
        Platform.runLater(()->{
            guiObject.getWindow().close();
            guiObject.setGameScene();
            guiObject.getWindow().setScene(guiObject.getScene());
            guiObject.getWindow().show();
        });
    }

    public void recallLoginScene() {
        Platform.runLater(()->{
            guiObject.getWindow().close();
            guiObject.getWindow().setScene(guiObject.setLoginScene());
            guiObject.getWindow().show();
        });
    }

    public void restoreRemoteView(VirtualView virtualViewToRestore) {
        allViews.add(new RemoteView(virtualViewToRestore.getUsername()));
        getMyRemoteView().updateRemoteView(virtualViewToRestore);
    }

    //Find the corrispondence between VirtualView and RemoteView and update its data
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
        this.notifyClient();
    }

    public void notifyClient(){
        /*
        out.flush();
        out.println("Informazioni di gioco:");
        for (RemoteView remoteView : allViews) {
            out.println("Username: " + remoteView.getUsername() + " (" + remoteView.getCharacter() + ")");
            out.println("Position: " + remoteView.getPosition());
            if (remoteView.getUsername().equals(this.username)) {
                out.println("# action available: " + remoteView.getNumberOfActions());
                out.println("Weapons: " + remoteView.getWeapons());
                out.println("Power-up: " + remoteView.getPowerUps());
                out.println("Points " + remoteView.getPoints());
            }
            out.println("Cubes: " + remoteView.getCubes());
            out.print("Cabinet red: [");
            for (int i = 0; i < 3; i++) {
                if (remoteView.getCabinetRed().getSlot()[i] != null) {
                    out.print(remoteView.getCabinetRed().getSlot()[i] + "  ");
                }
            }
            out.println("]");
            out.print("Cabinet yellow: [");
            for (int i = 0; i < 3; i++) {
                if (remoteView.getCabinetYellow().getSlot()[i] != null) {
                    out.print(remoteView.getCabinetYellow().getSlot()[i] + "  ");
                }
            }
            out.println("]");
            out.print("Cabinet blue: [");
            for (int i = 0; i < 3; i++) {
                if (remoteView.getCabinetBlue().getSlot()[i] != null) {
                    out.print(remoteView.getCabinetBlue().getSlot()[i] + "  ");
                }
            }
            out.println("]");
            out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            out.println();
        }
         */
    }

    public void applyEffect(WeaponShot weaponShot) throws RemoteException{
        this.getRmiStub().applyEffectWeapon(weaponShot);
    }

    @Override
    public String ping() {
        System.out.println("Client " + this.username + " still connected!");
        return this.username;
    }

    @Override
    public void showMessage(String message) throws RemoteException {
        TextArea textArea = guiObject.getTextArea();
        textArea.setText(message + "\n" + textArea.getText());

    }

    public void respawnDialog() {
        Platform.runLater(() -> this.guiObject.startingDraw("Choose one power ups to discard.\nIt determines your spawn location, based on its color."));
    }

    public int pingClient() {
        return 13;
        //System.out.println("Client " +this.username + " connesso");
    }

    public void waitPlayers() throws RemoteException {
        Platform.runLater(() -> this.guiObject.setWaitScene());
    }

    public void startingMatch() throws RemoteException {
        Platform.runLater(() -> this.guiObject.startMatch());
    }

    public void showEndGameWindow(ArrayList<Player> allPlayers){
        Platform.runLater(() -> {
            this.guiObject.setEndScene(allPlayers);
        });
    }

    public void closeGUI() throws RemoteException{
        Platform.runLater(() -> this.guiObject.closeWindow());
    }
}