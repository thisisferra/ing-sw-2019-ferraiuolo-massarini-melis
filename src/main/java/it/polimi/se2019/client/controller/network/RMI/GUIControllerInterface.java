package it.polimi.se2019.client.controller.network.RMI;

import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GUIControllerInterface extends Remote {

    void initRemoteView(ArrayList<VirtualView> allVirtualView) throws RemoteException;

    void update(ArrayList<VirtualView> allVirtualView) throws RemoteException;

    String ping() throws RemoteException;

    void respawnDialog() throws RemoteException;

    void restoreGUI() throws RemoteException;

    void recallLoginScene() throws RemoteException;

    void restoreRemoteView(VirtualView virtualViewToRestore) throws RemoteException;

    int pingClient() throws RemoteException;

    void waitPlayers() throws RemoteException;

    void startingMatch() throws RemoteException;

    void closeGUI() throws RemoteException;

    void showMessage(String message) throws RemoteException;

    void showEndGameWindow(ArrayList<Player> allPlayers) throws RemoteException;

}
