package it.polimi.se2019.server.controller.network.RMI;


///////Qua dentro ci mettiamo tut quilll che è nuostro

import it.polimi.se2019.client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {

    void fatticazzituoi() throws RemoteException;

    void fatticazzimiei(Client client) throws RemoteException;
}
