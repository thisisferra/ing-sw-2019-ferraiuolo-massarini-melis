package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.view.GUIController;
import it.polimi.se2019.client.view.GUIControllerInterface;
import it.polimi.se2019.server.model.map.Square;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//ritorno
public interface RMIServerInterface extends Remote {


    boolean checkUsername(String username) throws Exception;

    void register(String username, GUIControllerInterface guiControllerInterface) throws RemoteException;

    //Map<String, RemoteView> getRegisteredPlayers() throws RemoteException;

    ArrayList<Square> reacheableSquare(int position) throws RemoteException;

    void pickUpAmmo(String username, int position) throws RemoteException;

    void setNewPosition(String username, int newPosition) throws RemoteException;

    boolean isSpawnPoint(int position) throws RemoteException;

    void pickUpWeapon(String username, int indexToPickUp)throws RemoteException;

}