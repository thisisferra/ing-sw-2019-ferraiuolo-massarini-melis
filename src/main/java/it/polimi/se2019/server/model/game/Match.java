package it.polimi.se2019.server.model.game;

import com.google.gson.Gson;

import it.polimi.se2019.server.model.cards.*;
import it.polimi.se2019.server.model.cards.weapons.*;
import it.polimi.se2019.server.model.map.Map;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;


public class Match {
    private ArrayList<Player> players;
    private Player turn;
    private int numberOfPlayers;
    private ArrayList<PowerUp> powerUpStack;
    private ArrayList<Ammo> ammoStack;
    private ArrayList<Weapon> weaponStack;
    private ArrayList<PowerUp> discardedPowerUps;
    private ArrayList<Ammo> discardedAmmos;
    private int chosenMap;
    private Map map;
    private ArrayList<Player> killShotTrack;
    private ArrayList<WeaponSlot> arsenal;

    public Match(int chosenMap,int numberOfPlayers){
        this.chosenMap=chosenMap;
        this.numberOfPlayers = numberOfPlayers;
        this.arsenal = new ArrayList<>();
        this.discardedAmmos = new ArrayList<>();
        this.discardedPowerUps = new ArrayList<>();
        this.players = new ArrayList<>();

    }

    public void initializeMatch(){
        this.initGameField();
        this.initPlayers();
        this.initCabinets();
    }

    //generate instances of players
    //each player should type in his name and his figure(color figure)
    private void initPlayers(){
        switch(this.numberOfPlayers){
            case 0: {
                System.out.println("Not enough players.");
                break;
            }
            case 1: {
                System.out.println("Not enough players.");
                break;
            }
            case 2: {
                players.add(new Player("Mattia","blue",this));
                players.add(new Player("Marco","grey",this));
                break;
            }
            case 3: {
                players.add(new Player("Mattia","blue",this));
                players.add(new Player("Marco","grey",this));
                players.add(new Player("Ferra","yellow",this));
                break;
            }
            case 4: {
                players.add(new Player("Mattia","blue",this));
                players.add(new Player("Marco","grey",this));
                players.add(new Player("Ferra","yellow",this));
                players.add(new Player("Matteo","purple",this));
                break;
            }
            case 5: {
                players.add(new Player("Mattia","blue",this));
                players.add(new Player("Marco","grey",this));
                players.add(new Player("Ferra","yellow",this));
                players.add(new Player("Matteo","purple",this));
                players.add(new Player("Bruno", "green",this));
                break;
            }
            default:{
                System.out.println("Too many players!");
                break;
            }
        }
    }

    /*Generate the game field:
    * -the map is generated starting from mapID (1,2,3 or 4)
    * -ammo tiles, weapon and powerup cards are created by reading the json files located in the
    *  Resources folder.*/
    private void initGameField(){
        /*aS and pUS are required in order to parse the json file.
        * Each json file define an array containing Ammo, Weapon and PowerUp instances respectively.
        * After parsing each file into three arrays, they are converted into ArrayLists*/
        weaponStack = new ArrayList<>();
        Ammo[] aS;
        PowerUp[]pUS;
        //create the map filling it with squares from the json file, based on the idMap
        map = new Map(this.chosenMap);
        map.setAllSquare();
        map.setRoomSquare();
        Gson gson = new Gson();

        try {
            aS = gson.fromJson(new FileReader("./src/main/resources/ammo.json"), Ammo[].class);
            ammoStack = new ArrayList<>(Arrays.asList(aS));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/machine_gun.json"), MachineGun.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/plasma_gun.json"), PlasmaGun.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/thor.json"), Thor.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/electroscythe.json"), Electroscythe.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/tractor_beam.json"), TractorBeam.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/vortex_cannon.json"), VortexCannon.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/furnace.json"), Furnace.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/lock_rifle.json"), LockRifle.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/heatseeker.json"), Heatseeker.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/hellion.json"), Hellion.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/whisper.json"), Whisper.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/flamethrower.json"), Flamethrower.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/zx-2.json"), ZX2.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/grenade_launcher.json"), GrenadeLauncher.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/rocket_launcher.json"), RocketLauncher.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/shotgun.json"), Shotgun.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/power_glove.json"), PowerGlove.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/railgun.json"), Railgun.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/shockwave.json"), Shockwave.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/cyberblade.json"), Cyberblade.class));
            weaponStack.add(gson.fromJson(new FileReader("./src/main/resources/sledgehammer.json"), Sledgehammer.class));
            Collections.shuffle(weaponStack);
            pUS = gson.fromJson(new FileReader("./src/main/resources/powerups.json"),PowerUp[].class);
            powerUpStack = new ArrayList<>(Arrays.asList(pUS));
            Collections.shuffle(powerUpStack);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        //prints all decks
        /*
        if(!ammoStack.isEmpty()){
            for(Ammo ammo : ammoStack)
                System.out.println(ammo.toString());
        }
        if(!weaponStack.isEmpty()){
            for(Weapon weapon : weaponStack)
                System.out.println(weapon.toString());
        }
        if(!powerUpStack.isEmpty()){
            for(PowerUp powerup : powerUpStack)
                System.out.println(powerup.toString());
        }*/
    }

    private void initCabinets(){
        this.arsenal.add(new WeaponSlot("red",this));
        this.arsenal.add(new WeaponSlot("yellow",this));
        this.arsenal.add(new WeaponSlot("blue",this));
    }

    public ArrayList<Player> getAllPlayers() {
        return this.players;
    }

    public void round(Player player){

    }

    //if the stack isn't empty, it return the ammo card from the last position (arraylist size -1)
    public Ammo pickUpAmmoStack() {
        int size = ammoStack.size();
        return ammoStack.remove(size-1);
    }

    //if the stack isn't empty, it return the powerup card from the last position (arraylist size -1)
    public PowerUp pickUpPowerUp() {
        return powerUpStack.remove(powerUpStack.size() - 1);
    }

    public Weapon pickUpWeapon(){
        return weaponStack.remove(weaponStack.size()-1);
    }

    // currentAmmo is discarded and saved in discardedAmmos
    public void discardAmmo(Ammo currentAmmo) {
        //currentAmmo is the last ammo I've picked up
        discardedAmmos.add(currentAmmo);

    }

    public Map getMap(){
        return this.map;
    }

    public ArrayList<WeaponSlot> getArsenal(){
        return this.arsenal;
    }


    public void addPlayerKillShot(Player dead){
        killShotTrack.add(dead);
    }

    public ArrayList<PowerUp> getPowerUpStack(){
        return this.powerUpStack;
    }

    public ArrayList<Weapon> getWeaponStack(){
        return this.weaponStack;
    }
    public ArrayList<Ammo> getAmmoStack(){
        return this.ammoStack;
    }

    public ArrayList<Ammo> getDiscardedAmmos(){
        return this.discardedAmmos;
    }
    public ArrayList<PowerUp> getDiscardedPowerUps(){
        return this.discardedPowerUps;
    }
}