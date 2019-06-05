package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.view.View;
import it.polimi.se2019.server.model.cards.Ammo;
import it.polimi.se2019.server.model.map.Square;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//ritorno
public interface RMIServerInterface extends Remote {


    boolean register(String username) throws RemoteException;

    String getRegisteredPlayers() throws RemoteException;

    ArrayList<Square> reacheableSquare(int position) throws RemoteException;

    void pickUpAmmo(String username, int position) throws RemoteException;

    void setNewPosition(String username, int newPosition) throws RemoteException;

}