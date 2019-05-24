package it.polimi.se2019.client;

import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;

public class Client implements Serializable {

    private Client(){}

    private static int container;

    public static void main(String[] args) {
        try {
            Client person = new Client();
            Registry registry = LocateRegistry.getRegistry(1234);
            RMIServerInterface stub = (RMIServerInterface) registry.lookup("controller");
            stub.fatticazzituoi();
            stub.fatticazzimiei(person);
            TimeUnit.SECONDS.sleep(5);
            System.out.println(container);
        }
        catch (Exception e) {
            System.err.print("Client *****");
            e.printStackTrace();
        }
    }

    public void setContainer(int number) {
        container = number;
    }
}