package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.controller.network.RMI.RMIClient;
import it.polimi.se2019.client.controller.network.RMI.RMIClientInterface;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Player;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer implements RMIServerInterface {

    private final String registryRemObjName = "Server";
    private final int port = 1099;
    private Match match;

    //Not a remote method
    public RMIServer() {
        //TODO implement better
        try {

            RMIServerInterface stub = (RMIServerInterface) UnicastRemoteObject.exportObject(this, port);
            System.out.println(InetAddress.getLocalHost());
            Registry registry = LocateRegistry.createRegistry(port);
            registry.rebind(registryRemObjName, stub);
            System.out.println("Remote object registration completed");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Not a remote method
    public void setMatch(Match match) {
        this.match = match;
    }

    public boolean login(String clientName, RMIClientInterface client) {
        //TODO implement
        return true;
    }

    public boolean register(String clientName, String color, RMIClientInterface client) {

        try {
            match.getAllPlayers().add(new Player(clientName, color, match));
            System.out.println(client.getInput());

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        //TODO implement
        return true;
    }

    //Its only existence purpose is to test the first functionality
    public static void main (String args[]) {
        //TODO probably: implement security manager

            RMIServerInterface remObj = new RMIServer();
            ((RMIServer) remObj).setMatch(new Match(1,5));

    }
}