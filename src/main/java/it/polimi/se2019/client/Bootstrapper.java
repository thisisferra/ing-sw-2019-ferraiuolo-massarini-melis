
package it.polimi.se2019.client;

import it.polimi.se2019.client.controller.Client;
import it.polimi.se2019.client.view.GUI;
import javafx.application.Application;

//ritorno
public class Bootstrapper{

    private int rmiPort = 1099;

    private String host;

    private String remObjName = "remServer";

    private Client client;

    /*
    private Bootstrapper() {

        run();

    }

     */

    public static void main (String args[]) {

        new Bootstrapper();
        Application.launch(GUI.class, args);

    }
/*
    public void run() {
        Thread thread = Thread.currentThread();
        client = new RMIClient(rmiPort, host, remObjName, thread);

    }

 */
}
