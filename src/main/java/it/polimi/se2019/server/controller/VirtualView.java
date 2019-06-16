package it.polimi.se2019.server.controller;

import it.polimi.se2019.client.view.GUIControllerInterface;
import it.polimi.se2019.server.model.cards.PowerUp;
import it.polimi.se2019.server.model.cards.weapons.Weapon;
import it.polimi.se2019.server.model.game.Cubes;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.Player;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class VirtualView implements Serializable {

    GUIControllerInterface clientReference;

    //
    private String username;
    private String character;
    private int position;
    private int damage;
    private int numberOfAction;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private Cubes cubes;
    private WeaponSlot cabinetRed;
    private WeaponSlot cabinetYellow;
    private WeaponSlot cabinetBlue;
    private InfoShot infoShot;

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

    public String getCharacter() {
        return this.character;
    }

    public int getPosition() {
        return this.position;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getNumberOfAction() {
        return this.numberOfAction;
    }

    public ArrayList<Weapon> getWeapons(){
        return this.weapons;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return this.powerUps;
    }

    public Cubes getCubes(){
        return this.cubes;
    }

    public WeaponSlot getCabinetRed() {
        return this.cabinetRed;
    }

    public WeaponSlot getCabinetYellow() {
        return this.cabinetYellow;
    }

    public WeaponSlot getCabinetBlue() {
        return this.cabinetBlue;
    }

    public InfoShot getInfoShot() { return
            this.infoShot;
    }


    //SETTER

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setNumberOfAction(int numberOfAction) {
        this.numberOfAction = numberOfAction;
    }

    public void setWeapons(ArrayList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public void setCubes(Cubes cubes) {
        this.cubes = cubes;
    }

    public void setCabinetRed(WeaponSlot cabinetRed) {
        this.cabinetRed = cabinetRed;
    }

    public void setCabinetYellow(WeaponSlot cabinetYellow) {
        this.cabinetYellow = cabinetYellow;
    }

    public void setCabinetBlue(WeaponSlot cabinetBlue) {
        this.cabinetBlue = cabinetBlue;
    }

    public void setInfoShot(InfoShot infoShot) {
        this.infoShot = infoShot;
    }

    //ALTRO

    public void initializeVirtualView(Player player, Match match) {
        this.setUsername(player.getClientName());
        this.setPosition(player.getPosition());
        this.setCharacter(player.getCharacter());
        this.setNumberOfAction(player.getNumberOfAction());
        this.setWeapons(player.getHand().getWeapons());
        this.setPowerUps(player.getHand().getPowerUps());
        this.setCubes(player.getPlayerBoard().getAmmoCubes());
        this.setCabinetRed(match.getArsenal().get(0));
        this.setCabinetYellow(match.getArsenal().get(1));
        this.setCabinetBlue(match.getArsenal().get(2));

    }

    /*
    public void updateClient(VirtualView virtualView) throws RemoteException {
        clientReference.updateRemoteView(virtualView);
    }

     */
}
