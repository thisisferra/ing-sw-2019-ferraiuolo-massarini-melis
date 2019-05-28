package it.polimi.se2019.client.view;

import it.polimi.se2019.server.controller.network.RMI.RMIServerInterface;

//ritorno
public class GUIController {

    private RMIServerInterface rmiStub;

    public GUIController(RMIServerInterface rmiStub) {
        this.rmiStub = rmiStub;
    }

    public String pickUpAmmo(String text) {
        try {
            return rmiStub.pickUpAmmo(text);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error as above";
        }
    }

    public RMIServerInterface getStub() {
        return rmiStub;
    }

}