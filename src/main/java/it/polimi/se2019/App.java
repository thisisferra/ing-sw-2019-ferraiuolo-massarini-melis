package it.polimi.se2019;


import it.polimi.se2019.server.controller.Controller;
import it.polimi.se2019.server.controller.ShotController;
import it.polimi.se2019.server.model.cards.Weapon;
import it.polimi.se2019.server.model.game.Match;
import it.polimi.se2019.server.model.game.MovementChecker;
import it.polimi.se2019.server.model.game.RoomChecker;
import it.polimi.se2019.server.model.player.Player;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        ShotController shotController = new ShotController();
        Match m1 = new Match(1, 4);
        m1.initGameField();
        m1.initPlayers();
        Weapon currentWeapon = m1.getWeaponStack().get(0);
        Player player = m1.getAllPlayers().get(0);
        player.getHand().getWeapons().add(currentWeapon);
        ArrayList<Weapon> weap = shotController.checkAll(player);
        System.out.println(weap.get(0).getType());
        int length = weap.get(0).getEffect().length;
        for(int i = 0;i< length;i++){
            System.out.println(weap.get(0).getEffect()[i]);
        }
    }
}