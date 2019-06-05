package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

//ritorno
public class GUIController {

    private RMIServerInterface rmiStub;

    public GUIController(RMIServerInterface rmiStub) {
        this.rmiStub = rmiStub;
    }

    public RMIServerInterface getStub() {
        return rmiStub;
    }

}