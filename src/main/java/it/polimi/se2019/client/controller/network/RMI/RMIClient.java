package it.polimi.se2019.client.controller.network.RMI;

import it.polimi.se2019.server.controller.network.RMI.RMIServer;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
/*import it.polimi.se2019.server.model.cards.Weapon;*/
import it.polimi.se2019.server.model.player.Player;
/*import it.polimi.se2019.server.model.game.Match;*/

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {

    private final String registryRemObjName = "Server";
    private final int port = 1099;

    public RMIClient(String clientName, String color, String host) throws RemoteException {
        super();

        try {

            Registry registry = LocateRegistry.getRegistry(host, port);
            RMIServerInterface remObj = (RMIServerInterface) registry.lookup(registryRemObjName);
            remObj.register(clientName, color, this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getInput() {

        return "Connected";
    }

    /*Weapon chooseWeaponToUse() {
        return weapon
    }*/

    /*public Player chooseTarget() {
        return player
    }*/

    /*public Square chooseSquare() {
        return square
    }*/

    //Its only existence purpose is to test the first functionality
    public static void main (String args[]) {

        try {
            RMIClient client = new RMIClient("Marco", "grey", args[0]);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
