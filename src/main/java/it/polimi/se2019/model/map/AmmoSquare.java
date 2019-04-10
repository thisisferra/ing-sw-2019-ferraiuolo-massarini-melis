package it.polimi.se2019.model.map;

import it.polimi.se2019.model.cards.Ammo;
import java.util.ArrayList;

public class AmmoSquare extends Square {

    /*
    * fills up the square if the ammo placed on it has been grabbed during the previous turn
    * when an ammo tile is taken, the player simply draw an ammo tile from the ammo stock and
    * the full attribute is set to 0. the restock method will set all ammo squares back to 1
    * at the end of the turn.
    */
    public void restock(ArrayList<Ammo> deck){
        if (!this.full) {
            this.setFull();
        }

    }

    public AmmoSquare(Square square){
        square.setFull();
    }
}