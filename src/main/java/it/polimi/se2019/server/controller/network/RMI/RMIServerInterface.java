package it.polimi.se2019.server.controller.network.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

//ritorno
public interface RMIServerInterface extends Remote {

    String pickUpAmmo(String text) throws RemoteException;

    void register(String username) throws RemoteException;

    String getRegisteredPlayers() throws RemoteException;

}