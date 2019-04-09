package it.polimi.se2019.model.map;

import it.polimi.se2019.model.cards.Ammo;
import it.polimi.se2019.model.cards.Weapon;

import java.util.ArrayList;

public class AmmoSquare extends Square {

    //fills up the square if the ammo placed on it has been grabbed during the previous turn
    public void restock(ArrayList<Ammo> deck){
        if (this.full == false) {
            this.setFull();
        }

    }

    public AmmoSquare(Square square){
        square.setFull();
    }
}