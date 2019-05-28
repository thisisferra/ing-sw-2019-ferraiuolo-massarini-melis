package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.server.OneAboveAll;
import it.polimi.se2019.server.controller.network.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//ritorno
public class RMIServer extends Server implements RMIServerInterface {

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

            rmiRegistry = LocateRegistry.createRegistry(port);
            rmiRegistry.rebind(remObjName, this);
            UnicastRemoteObject.exportObject(this, port);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClientIP() throws ServerNotActiveException {
        return RemoteServer.getClientHost();
    }

    public String pickUpAmmo(String text) {
        try {

            System.out.println("Sto per stampare testo lato Client");
            //String returnText = text+"...la capra campa!";    //Return result using the passed parameter
            String returnText = "...la capra campa!";           //Return result without using the passed parameter
            System.out.println("Stampato testo");
            return returnText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error as above";
        }
    }

    public void register(String username) {
        registeredPlayers = username;
    }

    public String getRegisteredPlayers() {
        return registeredPlayers;
    }
}