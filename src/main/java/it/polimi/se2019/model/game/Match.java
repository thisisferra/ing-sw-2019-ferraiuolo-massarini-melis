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
    private ArrayList<PowerUp> powerUpStack;
    private ArrayList<Ammo> ammoStack;
    private ArrayList<Weapon> weaponStack;
    private ArrayList<PowerUp> discardedPowerUps;
    private ArrayList<Ammo> discardedAmmos;
    private int chosenMap;
    private Map map;
    private ArrayList<Integer> killShotTrack;
    private WeaponSlot[] arsenal;

    //generate instances of players
    public void initPlayers(){

    }
    //generate the gamefield
    public void initGameField(int index){
        Ammo[] aS;
        Weapon[]wS;
        PowerUp[]pUS;

        map = new Map();
        map.setAllSquare(index);
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
        for(Ammo ammo : ammoStack)
            System.out.println(ammo.toString());
        for(Weapon weapon : weaponStack)
            System.out.println(weapon.toString());
        for(PowerUp powerup : powerUpStack)
            System.out.println(powerup.toString());
    }
    public void round(Player player){

    }

    public Ammo pickUpAmmoStack() {
        int size = ammoStack.size();
        return ammoStack.remove(size-1);
    }

    public PowerUp pickUpPowerUp() {
        int size = powerUpStack.size();
        return powerUpStack.remove(size - 1);
    }

    public void discardAmmo(Ammo currentAmmo) {
        //currentAmmo is the last ammo I've picked up
        discardedAmmos.add(currentAmmo);

    }

}