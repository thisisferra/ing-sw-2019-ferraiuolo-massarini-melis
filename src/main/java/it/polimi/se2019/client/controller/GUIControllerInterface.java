package it.polimi.se2019.client.controller;

import it.polimi.se2019.server.controller.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GUIControllerInterface extends Remote {

    void initRemoteView(ArrayList<VirtualView> allVirtualView) throws RemoteException;

    void update(ArrayList<VirtualView> allVirtualView) throws RemoteException;

    String ping() throws RemoteException;

    void showMessageMovement(String message) throws RemoteException;

    void respawnDialog() throws RemoteException;

    void restoreGUI() throws RemoteException;

    void recallLoginScene() throws RemoteException;

    void restoreRemoteView(VirtualView virtualViewToRestore) throws RemoteException;
}
