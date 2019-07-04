package it.polimi.se2019.server;

import it.polimi.se2019.server.controller.network.RMI.RMIServer;

//ritorno
public class OneAboveAll {

    private int rmiPort = 1099;

    private String remObjName = "remServer";

    private RMIServer rmiServer;

    private OneAboveAll(String args[]) {
        rmiServer = new RMIServer(this, rmiPort, remObjName, args[0]);
        rmiServer.startServer();
        rmiServer.checkSuspendedMatch();
    }

    public static void main(String args[]) {

        new OneAboveAll(args);

    }
}