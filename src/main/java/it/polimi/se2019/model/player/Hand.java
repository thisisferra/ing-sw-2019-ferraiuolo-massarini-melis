package it.polimi.se2019.model.player;

import it.polimi.se2019.model.cards.Weapon;
import it.polimi.se2019.model.cards.PowerUp;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Weapon> weapons;
    private ArrayList<PowerUp> powerUps;

    /*
      discard one card from the player hand,
      the card is lost if it's a weapons card,
      otherwise it's added on the corresponding
      discarded list
    */

    public void discardWeapon(){

    }
    public void discardPower(){

    }

    public void addPowerUp(PowerUp currentPowerUp) {
        powerUps.add(powerUps.size() - 1, currentPowerUp);
    }
}