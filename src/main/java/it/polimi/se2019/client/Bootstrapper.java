
package it.polimi.se2019.client;

import it.polimi.se2019.client.controller.Client;
import it.polimi.se2019.client.controller.network.RMI.RMIClient;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.client.view.GUIController;
import it.polimi.se2019.client.view.RemoteView;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import javafx.application.Application;
import sun.rmi.runtime.RuntimeUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//ritorno
public class Bootstrapper{

    private int rmiPort = 1099;

    private String host = "127.0.0.1";

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
