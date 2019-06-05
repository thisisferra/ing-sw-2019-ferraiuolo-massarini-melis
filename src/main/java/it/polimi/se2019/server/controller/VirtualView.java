package it.polimi.se2019.server.controller;

import it.polimi.se2019.utils.Observable;
import it.polimi.se2019.utils.Observer;

public class VirtualView implements Observer {

    private int position = 0;

    public void update(Observable observed, Object obj) {

        //How can I manage different arguments?
        position = (int) obj;
        getPosition();
    }

    public void getPosition() {
        System.out.println(position);
    }

}