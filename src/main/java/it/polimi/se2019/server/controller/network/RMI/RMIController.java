package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.Client;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

import java.rmi.RemoteException;

public class RMIController implements RMIServerInterface {

    public void fatticazzituoi() {
        System.out.println("************");
    }

    public void fatticazzimiei(Client client) {
        client.setContainer(5);
    }
}
