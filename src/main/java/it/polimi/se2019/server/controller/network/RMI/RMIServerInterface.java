package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.controller.network.RMI.RMIClient;
import it.polimi.se2019.client.controller.network.RMI.RMIClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Comprise methods available to the remote client invocation
 */
public interface RMIServerInterface extends Remote {

    /**
     * Lets the client login if he was suspended or has exit the game
     * @return true if the user has successfully logged in, false otherwise
     */
    boolean login(String clientName, RMIClientInterface client) throws RemoteException;

    /**
     * Lets the client register himself in the player's game list
     * @return true if the user has been successfully registered, false otherwise
     */
    boolean register(String clientName, String color, RMIClientInterface client) throws RemoteException;
}