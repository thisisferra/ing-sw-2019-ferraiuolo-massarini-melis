package it.polimi.se2019.client.controller.network.RMI;

import it.polimi.se2019.client.controller.Client;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.client.view.View;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//ritorno
public class RMIClient extends Client {

    private String host;

    private int port;

    private String remObjName;

    private Thread threadClient;


    private RMIServerInterface rmiStub;

    public RMIClient(int port, String host, String remObjName, Thread threadClient) {
        this.port = port;
        this.host = host;
        this.remObjName = remObjName;
        this.threadClient = threadClient;
    }
}