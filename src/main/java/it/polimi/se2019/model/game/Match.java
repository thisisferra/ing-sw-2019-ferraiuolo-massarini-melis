package it.polimi.se2019.model.game;

import it.polimi.se2019.model.cards.Ammo;
import it.polimi.se2019.model.cards.PowerUp;
import it.polimi.se2019.model.cards.Weapon;
import it.polimi.se2019.model.player.Player;
import java.util.ArrayList;

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

    //generate instances of players
    public void initPlayers(){

    }
    //generate the gamefield
    public void initGameField(){

    }
    public void round(Player player){

    }

}