package it.polimi.se2019;

import it.polimi.se2019.server.model.cards.*;
import it.polimi.se2019.server.model.game.Match;
import java.util.ArrayList;


public class App {
    public static void main( String[] args ) {
        Match m1  = new Match(1,5);
        m1.initializeMatch();
        ArrayList<Weapon> weapons = new ArrayList<>();
        weapons.addAll(m1.getWeaponStack());
        ArrayList<Weapon> copiedWeapons = new ArrayList<>();

        for(Weapon weapon: weapons){

            System.out.println(weapon.getType());
            copiedWeapons.add(weapon.weaponFactory(weapon));

        }
        System.out.println(weapons);
        System.out.println(copiedWeapons);


    }
}