package it.polimi.se2019.model.player;

import it.polimi.se2019.model.cards.Weapon;
import it.polimi.se2019.model.cards.PowerUp;

public class Hand {
    private Weapon[] weapons;
    private PowerUp[] powerUps;

    /*
      discard one card from the player hand,
      the card is lost if it's a weapons card,
      otherwise is added on the corresponding
      discarded list
    */

    public void discard(Weapon card){

    }
    //same as below, this method has been overloaded
    public void discard(PowerUp card){

    }
}