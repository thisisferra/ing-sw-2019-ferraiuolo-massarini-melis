package it.polimi.se2019.client.controller.network.RMI;

import it.polimi.se2019.client.controller.Client;
import it.polimi.se2019.client.view.GUI;
import it.polimi.se2019.client.view.RemoteView;
import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

//ritorno
public class RMIClient extends Client{

    private ArrayList<RemoteView> remoteViews = new ArrayList<>();

    private String host;

    private int port;

    private String remObjName;


    public static void main(String[] args) throws Exception{
        RMIClient client = new RMIClient();
        client.run(args);
    }

    public void run(String args[]) throws Exception{
        Stage primaryStage = new Stage();
        GUI gui = new GUI();
        gui.start(primaryStage);
    }
}