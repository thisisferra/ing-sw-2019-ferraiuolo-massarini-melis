package it.polimi.se2019.client;

import it.polimi.se2019.client.controller.Client;
import it.polimi.se2019.client.controller.network.RMI.RMIClient;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.client.view.GUIController;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import javafx.application.Application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//ritorno
public class Bootstrapper {

    private int rmiPort = 1099;

    private String host = "127.0.0.1";

    private String remObjName = "remServer";

    private Client client;

    private RMIServerInterface rmiStub;

    private String registeredPlayers = "";

    private String[] ciccio;

    private Bootstrapper() {

        client = null;
        client = new RMIClient(rmiPort, host, remObjName);
        rmiStub = client.getStub();

        String username = "";
        BufferedReader in = new BufferedReader( new InputStreamReader(System.in) );
        System.out.println("Digita il tuo username:\n");

        do {

            try {

                username = in.readLine();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } while(!validChoice(username));

        try {
            //guiController.getStub().register(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //System.out.println(guiController.getStub().getRegisteredPlayers());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main (String args[]) {

        new Bootstrapper();
        Application.launch(GUI.class, args);


    }

    public boolean validChoice(String text) {
        if (!text.equals("")) {
            return true;
        } else {
            System.out.println("Not a valid choice, try again.");
            return false;
        }
    }

    public String[] getCiccio() {
        return ciccio;
    }
}