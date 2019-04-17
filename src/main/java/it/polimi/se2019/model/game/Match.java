package it.polimi.se2019.model.game;

import com.google.gson.Gson;
import it.polimi.se2019.model.cards.Ammo;
import it.polimi.se2019.model.cards.PowerUp;
import it.polimi.se2019.model.cards.Weapon;
import it.polimi.se2019.model.map.Map;
import it.polimi.se2019.model.map.WeaponSlot;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

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
    private ArrayList<Integer> killShotTrack;
    private ArrayList<WeaponSlot> arsenal;

    public Match(int chosenMap,int numberOfPlayers){
        this.chosenMap=chosenMap;
        this.numberOfPlayers = numberOfPlayers;
        this.arsenal = new ArrayList<WeaponSlot>();
        this.arsenal.add(new WeaponSlot("red"));
        this.arsenal.add(new WeaponSlot("blue"));
        this.arsenal.add(new WeaponSlot("yellow"));
        this.discardedAmmos = new ArrayList<Ammo>();
        this.discardedPowerUps = new ArrayList<PowerUp>();

    }

    //generate instances of players
    public void initPlayers(){

    }

    /*Generate the game field:
    * -the map is generated starting from mapID (1,2,3 or 4)
    * -ammo tiles, weapon and powerup cards are created by reading the json files located in the
    *  Resources folder.*/
    public void initGameField(){
        /*aS, wS and pUS are required in order to parse the json file.
        * Each json file define an array containing Ammo, Weapon and PowerUp instances respectively.
        * After parsing each file into three arrays, they are converted into ArrayLists*/
        Ammo[] aS;
        Weapon[]wS;
        PowerUp[]pUS;
        //create the map fills it with squares from the json file, based on the idMap
        map = new Map(this.chosenMap);
        map.setAllSquare();
        Gson gson = new Gson();

        try {
            aS = gson.fromJson(new FileReader("./src/main/resources/ammo.json"), Ammo[].class);
            ammoStack = new ArrayList<Ammo>(Arrays.asList(aS));
            wS = gson.fromJson(new FileReader("./src/main/resources/weapons_list.json"),Weapon[].class);
            weaponStack = new ArrayList<Weapon>(Arrays.asList(wS));
            pUS = gson.fromJson(new FileReader("./src/main/resources/powerups.json"),PowerUp[].class);
            powerUpStack = new ArrayList<PowerUp>(Arrays.asList(pUS));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //prints all decks
        for(Ammo ammo : ammoStack)
            System.out.println(ammo.toString());
        for(Weapon weapon : weaponStack)
            System.out.println(weapon.toString());
        for(PowerUp powerup : powerUpStack)
            System.out.println(powerup.toString());
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
        int size = powerUpStack.size();
        return powerUpStack.remove(size - 1);
    }

    // currentAmmo is discarded and saved in discardedAmmos
    public void discardAmmo(Ammo currentAmmo) {
        //currentAmmo is the last ammo I've picked up
        discardedAmmos.add(currentAmmo);

    }
    public ArrayList<PowerUp> getDiscardedPowerUps(){
        return this.discardedPowerUps;
    }

}