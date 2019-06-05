package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.network.Server;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.map.Square;
import it.polimi.se2019.server.model.player.Player;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//ritorno
public class RMIServer extends Server implements RMIServerInterface {

    private Match match;

    private final int port;

    private final String remObjName;

    private Registry rmiRegistry;

    private String registeredPlayers = "";

    public RMIServer(OneAboveAll oneAboveAll, int port, String remObjName) {
        super(oneAboveAll);
        this.port = port;
        this.remObjName = remObjName;
    }

    public void startServer() {
        try {
            System.out.println("Server is up");
            rmiRegistry = LocateRegistry.createRegistry(port);
            rmiRegistry.rebind(remObjName, this);
            UnicastRemoteObject.exportObject(this, port);
            System.out.println("Registry created and object exported");
            match = new Match(1);
            match.initializeMatch();
            System.out.println("Match created");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClientIP() throws ServerNotActiveException {
        return RemoteServer.getClientHost();
    }

    public synchronized boolean register(String username) throws RemoteException{
        for(Player player: match.getAllPlayers())
            if(player.getClientName().equals(username)) {
                System.out.println("Lo username inserito è gia presente");
                return false;
            }
        match.getAllPlayers().add(new Player(username, "color", match));
        System.out.println("Login effettuato con successo: " + username);
        return true;
    }

    public String getRegisteredPlayers() {
        return registeredPlayers;
    }

    public ArrayList<Square> reacheableSquare(int position) throws RemoteException{
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
        System.out.println(match.searchPlayerByClientName(username).getPosition());
        currentPlayer.setPosition(newPosition);
        match.setChanged();
        match.notifyObservers(match.searchPlayerByClientName(username).getPosition());
    }
}