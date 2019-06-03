package it.polimi.se2019.client.controller.network.RMI;

import it.polimi.se2019.client.controller.Client;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//ritorno
public class RMIClient extends Client {

    private String host;

    private int port;

    private String remObjName;

    private Registry rmiRegistry;

    private RMIServerInterface rmiStub;

    public RMIClient(int port, String host, String remObjName) {
        this.port = port;
        this.host = host;
        this.remObjName = remObjName;
        connectRMIRegistry();
    }

    public void connectRMIRegistry() {
        try {

            rmiRegistry = LocateRegistry.getRegistry(host, port);
            rmiStub = (RMIServerInterface) rmiRegistry.lookup(remObjName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RMIServerInterface getStub() {
        return rmiStub;
    }

    public void register() {

    }

    public void login() {

    }
}