package it.polimi.se2019.server;

import it.polimi.se2019.server.controller.network.RMI.RMIController;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server{

    public static void main(String[] args) {
        try {
            RMIController rmiController = new RMIController();
            RMIServerInterface stub = (RMIServerInterface) UnicastRemoteObject.exportObject(rmiController, 1234);
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.bind("controller", stub);
            System.out.println("I've exported the object");

        }
        catch(Exception e) {
            System.err.print("Server ****");
        }
    }
}
