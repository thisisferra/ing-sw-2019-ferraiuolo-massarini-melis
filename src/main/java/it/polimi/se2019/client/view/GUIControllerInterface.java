package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GUIControllerInterface extends Remote {

    void initRemoteView(ArrayList<VirtualView> allVirtualView) throws RemoteException;

    void update(ArrayList<VirtualView> allVirtualView) throws RemoteException;
}
