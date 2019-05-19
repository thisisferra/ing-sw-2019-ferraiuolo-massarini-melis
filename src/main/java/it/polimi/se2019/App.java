package it.polimi.se2019;

import it.polimi.se2019.server.controller.ShotController;
import it.polimi.se2019.server.model.cards.*;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.player.Hand;
import it.polimi.se2019.server.model.player.Player;


public class App {
    public static void main( String[] args ) {
        Match m1  = new Match(1,5);
        m1.initializeMatch();
        Player p0 = m1.getAllPlayers().get(0);
        Player p1 = m1.getAllPlayers().get(1);
        Player p2 = m1.getAllPlayers().get(2);
        Player p3 = m1.getAllPlayers().get(3);
        Player p4 = m1.getAllPlayers().get(4);
        p0.setPosition(0); //mattia
        p1.setPosition(2); //marco
        p2.setPosition(1); //ferra
        p3.setPosition(7); // matteo
        p4.setPosition(11); //bruno
        Hand h0 = p0.getHand();
        Hand h1 = p1.getHand();
        Hand h2 = p2.getHand();
        Hand h3 = p3.getHand();
        Hand h4 = p4.getHand();

        ShotController shotController = new ShotController(m1);
        for(int i=0; i< 3; i++){
            h0.getWeapons().add(m1.pickUpWeapon());
            h1.getWeapons().add(m1.pickUpWeapon());
            h2.getWeapons().add(m1.pickUpWeapon());
            h3.getWeapons().add(m1.pickUpWeapon());
            h4.getWeapons().add(m1.pickUpWeapon());
        }
        System.out.println("mattia :" + h0.getWeapons());
        shotController.checkAll(p0);

        System.out.println("marco :" + h0.getWeapons());
        shotController.checkAll(p1);

        System.out.println("ferra :" + h0.getWeapons());
        shotController.checkAll(p2);

        System.out.println("matteo :" + h0.getWeapons());
        shotController.checkAll(p3);

        System.out.println("bruno :" + h0.getWeapons());
        shotController.checkAll(p4);

    }
}