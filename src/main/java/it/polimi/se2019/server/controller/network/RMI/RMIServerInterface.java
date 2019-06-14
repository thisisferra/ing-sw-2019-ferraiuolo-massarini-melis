package it.polimi.se2019.server.controller.network.RMI;

import it.polimi.se2019.client.view.GUIController;
import it.polimi.se2019.client.view.GUIControllerInterface;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.Square;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//ritorno
public interface RMIServerInterface extends Remote {


    boolean checkUsername(String username) throws Exception;

    void register(String username, GUIControllerInterface guiController) throws RemoteException;

    ArrayList<Square> reachableSquares(int position, int steps) throws RemoteException;

    void pickUpAmmo(String username) throws RemoteException;

    void setNewPosition(String username, int newPosition) throws RemoteException;

    boolean isSpawnPoint(int position) throws RemoteException;

    void pickUpWeapon(String username, int indexToPickUp)throws RemoteException;

    void pickUpWeapon(String username, int indexToPickUp, int indexToDrop)throws RemoteException;

    void restoreMap() throws RemoteException;

    Match getMatch() throws RemoteException;

    boolean checkNumberAction(String username) throws RemoteException;

    void useAction(String username) throws RemoteException;

    void setActivePlayer(String usernameLastPlayer) throws RemoteException;

    String getActivePlayer() throws RemoteException;

    void resetActionNumber(String username) throws RemoteException;

}