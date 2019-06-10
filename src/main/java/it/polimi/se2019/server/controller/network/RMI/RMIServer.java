package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.view.GUIController;
import it.polimi.se2019.client.view.GUIControllerInterface;
import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.controller.network.Server;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.io.PrintStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

//ritorno
public class RMIServer extends Server implements RMIServerInterface {

    //Attributi

    PrintStream out = new PrintStream(System.out);

    private Match match;

    private final int port;

    private final String remObjName;

    private Registry rmiRegistry;

    private ArrayList<VirtualView> allVirtualViews = new ArrayList<>();


    //Metodi

    public RMIServer(OneAboveAll oneAboveAll, int port, String remObjName) {
        super(oneAboveAll);
        this.port = port;
        this.remObjName = remObjName;
    }

    public void startServer() {
        try {
            System.out.println("Server is up");
            rmiRegistry = LocateRegistry.createRegistry(port);
            UnicastRemoteObject.exportObject(this, port);
            rmiRegistry.bind(remObjName, this);
            System.out.println("Registry created and object exported");
            match = new Match(1);
            match.initializeMatch();
            System.out.println("Match created");
            System.out.println("Client connessi: ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClientIP() throws ServerNotActiveException {
        return RemoteServer.getClientHost();
    }

    public synchronized void register(String username, GUIControllerInterface guiController) throws RemoteException{
        //Creo una virtual View
        VirtualView virtualView = new VirtualView((guiController));
        allVirtualViews.add(virtualView);
        //Aggiungo riferimento al client appena connesso
        //registeredPlayers.put(username, guiController);
        //Aggiungo un nuovo giocatore al model
        Player player = new Player(username, "color", match);
        match.getAllPlayers().add(player);
        //Imposto lo username, preso dal model, nella virtualView appena creata
        virtualView.setUsername(player.getClientName());
        virtualView.setWeapons(player.getHand().getWeapons());
        virtualView.setPowerUps(player.getHand().getPowerUps());
        this.printClientConnected();
        this.initAllClient(allVirtualViews);

        /*
        for (VirtualView virtualView : allVirtualViews) {
            virtualView.getClientReference().notifyNewClient(username);
        }
        try {
            this.updateAllClient();
        }
        catch (Exception e) {
            System.err.println("Errore");
        }
         */
    }

    public synchronized boolean checkUsername(String username) throws Exception {
        if (match.getAllPlayers().size() < 5) {
            for (Player player : match.getAllPlayers()) {
                if (player.getClientName().equals(username)) {
                    return false;
                }
            }
        }
        else{
            throw new Exception("Already 5 players");
        }
        return true;
    }

    public ArrayList<Square> reacheableSquare(int position){
        MovementChecker movementChecker = new MovementChecker(match.getMap().getAllSquare(), 3, position);
        return movementChecker.getReachableSquares();
    }

    public void pickUpAmmo(String username, int position) {
        Square square = match.getMap().searchSquare(position);
        Player player = match.searchPlayerByClientName(username);
        player.pickUpAmmo(square, match);
    }

    public void setNewPosition(String username, int newPosition){
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.setPosition(newPosition);
        try {
            getMyVirtualView(username).setPosition(currentPlayer.getPosition());
            updateAllClient(allVirtualViews);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printClientConnected() {
        int size = allVirtualViews.size();
        out.println(allVirtualViews.get(size - 1).getUsername() + "---" + allVirtualViews.get(allVirtualViews.size() - 1).getClientReference());
    }

    //Create all remotes view
    public void initAllClient(ArrayList<VirtualView> allVirtualViews) throws RemoteException{
        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            clientRef.initRemoteView(allVirtualViews);
        }

    }

    public VirtualView getMyVirtualView(String username) {
        for(VirtualView virtualView : allVirtualViews) {
            if (virtualView.getUsername().equals(username)) {
                return  virtualView;
            }
        }
        return null;
    }

    public void updateAllClient(ArrayList<VirtualView> allVirtualView) throws RemoteException{
        for (VirtualView virtualView : allVirtualViews) {
            GUIControllerInterface clientRef = virtualView.getClientReference();
            clientRef.update(allVirtualViews);
        }
    }

    public boolean isSpawnPoint(int position) {
        if (match.getMap().searchSquare(position).isSpawnPoint()) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void pickUpWeapon(String username, int indexToPickUp) throws RemoteException {
        Player currentPlayer = match.searchPlayerByClientName(username);
        currentPlayer.pickUpWeapon(indexToPickUp);
        getMyVirtualView(username).setWeapons(currentPlayer.getHand().getWeapons());
        updateAllClient(allVirtualViews);


    }
}