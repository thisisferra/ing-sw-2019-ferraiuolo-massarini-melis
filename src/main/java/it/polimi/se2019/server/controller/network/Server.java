package it.polimi.se2019.server.controller.network;

import it.polimi.se2019.server.OneAboveAll;

//ritorno
public abstract class Server {

    private OneAboveAll oneAboveAll;

    protected Server(OneAboveAll oneAboveAll) {
        this.oneAboveAll = oneAboveAll;
    }
}