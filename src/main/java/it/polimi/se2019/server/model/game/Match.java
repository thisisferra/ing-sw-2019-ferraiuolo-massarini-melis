package it.polimi.se2019.server.model.game;

import com.google.gson.Gson;

import it.polimi.se2019.server.controller.VirtualView;
import it.polimi.se2019.server.model.cards.*;
import it.polimi.se2019.server.model.cards.powerUp.*;
import it.polimi.se2019.server.model.cards.weapons.*;
import it.polimi.se2019.server.model.map.Map;
import it.polimi.se2019.server.model.map.WeaponSlot;
import it.polimi.se2019.server.model.player.Player;
import it.polimi.se2019.server.model.player.PlayerBoard;
import it.polimi.se2019.utils.Observable;

import java.io.Serializable;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Match extends Observable implements Serializable {
    private ArrayList<Player> players;
    private Player turn;
    //private int numberOfPlayers;
    private ArrayList<PowerUp> powerUpStack;
    private ArrayList<Ammo> ammoStack;
    private ArrayList<Weapon> weaponStack;
    private ArrayList<PowerUp> discardedPowerUps;
    private ArrayList<Ammo> discardedAmmos;
    private int chosenMap;
    private Map map;
    private ArrayList<Player> killShotTrack = new ArrayList<>();
    private ArrayList<WeaponSlot> arsenal;
    private ArrayList<String> characterAvailable = new ArrayList<>();
    private ArrayList<Player> playersDead = new ArrayList<>();
    private boolean openConnection;

    private boolean finalFrenzyStatus;

    public Match(int chosenMap){
        this.chosenMap=chosenMap;
        //this.numberOfPlayers = numberOfPlayers;
        this.arsenal = new ArrayList<>();
        this.discardedAmmos = new ArrayList<>();
        this.discardedPowerUps = new ArrayList<>();
        this.players = new ArrayList<>();
        this.openConnection = true;
        this.finalFrenzyStatus = false;

    }

    public void initializeMatch(){
        this.initGameField();
        this.initCabinets();
        this.initializeCharacterAvailable();
    }

    public void initializeCharacterAvailable() {
        this.characterAvailable.add("distructor");
        this.characterAvailable.add("banshee");
        this.characterAvailable.add("dozer");
        this.characterAvailable.add("sprog");
        this.characterAvailable.add("violet");
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
        powerUpStack = new ArrayList<>();
        Ammo[] aS;
        //PowerUp[]pUS;
        //create the map filling it with squares from the json file, based on the idMap
        map = new Map(this.chosenMap);
        map.setAllSquare();
        map.setRoomSquare();
        Gson gson = new Gson();
        Logger logger = Logger.getAnonymousLogger();
        try {
            aS = gson.fromJson(new FileReader("./src/main/resources/ammo.json"), Ammo[].class);
            //pUS = gson.fromJson(new FileReader("./src/main/resources/powerups.json"),PowerUp[].class);

            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/plasma_gun.json"), PlasmaGun.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/electroscythe.json"), Electroscythe.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/lock_rifle.json"), LockRifle.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/whisper.json"), Whisper.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/zx-2.json"), ZX2.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/shockwave.json"), Shockwave.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/machine_gun.json"), MachineGun.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/flamethrower.json"), Flamethrower.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/furnace.json"), Furnace.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/hellion.json"), Hellion.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/shotgun.json"), Shotgun.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/power_glove.json"), PowerGlove.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/heatseeker.json"), Heatseeker.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/vortex_cannon.json"), VortexCannon.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/tractor_beam.json"), TractorBeam.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/railgun.json"), Railgun.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/sledgehammer.json"), Sledgehammer.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/cyberblade.json"), Cyberblade.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/rocket_launcher.json"), RocketLauncher.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/grenade_launcher.json"), GrenadeLauncher.class));
            weaponStack.add(gson.fromJson(new FileReader("src/main/resources/thor.json"), Thor.class));


            for (int i = 0; i < 2; i++) {
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/targeting_scopeRed.json"), TargetingScope.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/targeting_scopeYellow.json"), TargetingScope.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/targeting_scopeBlue.json"), TargetingScope.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/teleporterRed.json"), Teleporter.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/teleporterYellow.json"), Teleporter.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/teleporterBlue.json"), Teleporter.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/tagback_grenadeRed.json"), TagbackGrenade.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/tagback_grenadeYellow.json"), TagbackGrenade.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/tagback_grenadeBlue.json"), TagbackGrenade.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/newtonRed.json"), Newton.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/newtonYellow.json"), Newton.class));
                powerUpStack.add(gson.fromJson(new FileReader("./src/main/resources/newtonBlue.json"), Newton.class));
            }
            //powerUpStack = new ArrayList<>(Arrays.asList(pUS));
            ammoStack = new ArrayList<>(Arrays.asList(aS));

            //Collections.shuffle(weaponStack);
            Collections.shuffle(powerUpStack);
            Collections.shuffle(ammoStack);

        } catch (FileNotFoundException e) {
            logger.log(Level.INFO,"Error while reading json files",e);
        }
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
        PlayerBoard playerBoard = dead.getPlayerBoard();
        if (playerBoard.getDamage().size() == 11) {
            this.killShotTrack.add(playerBoard.getDamage().get(10));
        }
        else if(playerBoard.getDamage().size() == 12) {
            this.killShotTrack.add(playerBoard.getDamage().get(11));
        }
    }

    public ArrayList<Player> getKillShotTrack(){
        return this.killShotTrack;
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

    public Player searchPlayerByClientName(String clientName) {
        for (Player player : this.getAllPlayers()) {
            if (player.getClientName().equals(clientName)) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<String> getCharacterAvailable() {
        return this.characterAvailable;
    }

    public void setPlayerDead() {
        for (Player player : this.getAllPlayers()) {
            if (player.getPlayerDead()) {
                this.playersDead.add(player);
            }
        }
    }

    public ArrayList<Player> getPlayersDead() {
        return this.playersDead;
    }

    public void setOpenConnection(boolean openConnection) {
        this.openConnection = openConnection;
    }

    public boolean getOpenConnection() {
        return this.openConnection;
    }

    public void setFinalFrenzyStatus(boolean finalFrenzyStatus) {
        this.finalFrenzyStatus =finalFrenzyStatus;
    }

    public boolean isFinalFrenzyStatus() {
        return this.finalFrenzyStatus;
    }
}