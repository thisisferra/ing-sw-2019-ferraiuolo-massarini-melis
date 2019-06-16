package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.InfoShot;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import it.polimi.se2019.server.model.cards.weapons.Weapon;

import java.io.PrintStream;
import java.rmi.Naming;
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

    public GUIController(String IPAddress) {
        try {
            //Registry registry = LocateRegistry.getRegistry("192.168.",0);
            Registry registry = LocateRegistry.getRegistry(IPAddress,0);
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
        this.notifyClient();
    }

    public void notifyClient(){
        out.flush();
        out.println("Informazioni di gioco:");
        for (RemoteView remoteView : allViews) {
            out.println("Username: " + remoteView.getUsername() + " (" + remoteView.getCharacter() + ")");
            out.println("Position: " + remoteView.getPosition());
            if (remoteView.getUsername().equals(this.username)) {
                out.println("# action available: " + remoteView.getNumberOfActions());
                out.println("Weapons: " + remoteView.getWeapons());
                out.println("Power-up: " + remoteView.getPowerUps());
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
    }

    //TODO check this
    public void verifyWeapons() throws RemoteException{
        RemoteView myRemoteView = this.getMyRemoteView();
        myRemoteView.setAvailableWeapon(this.getRmiStub().verifyWeapons(this.username));
    }

    public void applyEffect(InfoShot infoShot) throws RemoteException{
        this.getRmiStub().applyEffect(infoShot);
    }
}