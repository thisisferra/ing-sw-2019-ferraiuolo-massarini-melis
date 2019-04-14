package it.polimi.se2019.model.game;

import com.google.gson.Gson;
import it.polimi.se2019.model.cards.Ammo;
import it.polimi.se2019.model.cards.PowerUp;
import it.polimi.se2019.model.cards.Weapon;
import it.polimi.se2019.model.map.WeaponSlot;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Match {
    private ArrayList<Player> players;
    private Player turn;
    private ArrayList<PowerUp> powerUpStack;
    private ArrayList<Ammo> ammoStack;
    private ArrayList<Weapon> weaponStack;
    private ArrayList<PowerUp> discardedPowerUps;
    private ArrayList<Ammo> discardedAmmos;
    private int chosenMap;
    private ArrayList<Integer> killShotTrack;
    private WeaponSlot[] arsenal;

    //generate instances of players
    public void initPlayers(){

    }
    //generate the gamefield
    public void initGameField(){
        Gson gson = new Gson();
        try {
            ammoStack = gson.fromJson(new FileReader("/src/main/java/it/polimi/se2019/ammo.json"), ArrayList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(Ammo ammo : ammoStack)
            System.out.println(ammoStack.toString());
    }
    public void round(Player player){

    }

}