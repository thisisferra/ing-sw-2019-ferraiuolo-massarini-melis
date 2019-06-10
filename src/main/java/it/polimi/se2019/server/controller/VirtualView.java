package it.polimi.se2019.server.controller;

import it.polimi.se2019.client.view.GUIControllerInterface;
import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class VirtualView implements Serializable {

    GUIControllerInterface clientReference;

    //
    private String username;
    private int position;
    private int damage;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    //COSTRUTTORE
    public VirtualView(GUIControllerInterface clientReference) {
        this.clientReference = clientReference;
    }

    //GETTER

    public GUIControllerInterface getClientReference() {
        return this.clientReference;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPosition() {
        return this.position;
    }

    public int getDamage() {
        return this.damage;
    }

    public ArrayList<Weapon> getWeapons(){
        return this.weapons;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return this.powerUps;
    }


    //SETTER

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    //ALTRO

    /*
    public void updateClient(VirtualView virtualView) throws RemoteException {
        clientReference.updateRemoteView(virtualView);
    }

     */
}
