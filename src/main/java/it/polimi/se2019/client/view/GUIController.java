package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//ritorno
public class GUIController implements GUIControllerInterface {

    PrintStream out;

    private String username;

    private RMIServerInterface rmiStub;

    private ArrayList<RemoteView> allViews = new ArrayList<>();

    public GUIController() {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            rmiStub = (RMIServerInterface) registry.lookup("remServer");
            UnicastRemoteObject.exportObject(this, 0);
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
    @Override
    public void initRemoteView(ArrayList<VirtualView> allVirtualView) throws RemoteException {
        boolean alreadyCreated = false;
        out.println("Remote view locali: ");
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
        for (RemoteView remoteView : allViews) {
            out.println(remoteView.getUsername());
        }
        update(allVirtualView);

    }

    @Override
    public void update(ArrayList<VirtualView> allVirtualView) throws RemoteException {
        for (VirtualView virtualView : allVirtualView) {
            String virtualUsername = virtualView.getUsername();
            for (RemoteView remoteView : allViews) {
                if (remoteView.getUsername().equals(virtualUsername)) {
                    //Set the position
                    //remoteView.setPosition(virtualView.getPosition());
                    remoteView.updateRemoteView(virtualView);
                }
            }
        }
        this.notifyClient();
    }

    public void notifyClient(){
        out.flush();
        out.println("Informazioni di gioco:");
        for (RemoteView remoteView : allViews) {
            out.println(remoteView.getUsername() + ":");
            out.println("Position: " + remoteView.getPosition());
            if (remoteView.getUsername().equals(this.username)){
                out.println("Weapons: " + remoteView.getWeapons());
                out.println("Power-up: " + remoteView.getPowerUp());
            }
        }
    }
}