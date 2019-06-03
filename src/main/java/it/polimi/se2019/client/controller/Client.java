package it.polimi.se2019.client.controller;

import it.polimi.se2019.client.controller.network.RMI.RMIClient;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

//ritorno
public abstract class Client {

    private int rmiPort;

    private String host;

    private String remObjName;

    private RMIServerInterface rmiStub;

    public Client() {

    }

    public Client (RMIServerInterface rmiStub) {
        this.rmiStub = rmiStub;
    }

    public Client(int rmiPort, String host, String remObjName) {
        this.rmiPort = rmiPort;
        this.host = host;
        this.remObjName = remObjName;
        rmiStub = new RMIClient(this.rmiPort, this.host, this.remObjName).getStub();
    }

    public RMIServerInterface getStub() {
        return rmiStub;
    }

    public abstract void register();

    public abstract void login();
}